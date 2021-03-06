package projekti.services;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.Account;
import projekti.models.FriendRequest;
import projekti.repositories.AccountRepository;
import projekti.repositories.FriendRequestRepository;

@Service
@Transactional
public class FriendService {

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private FriendRequestRepository friendRequestRepository;

  @Autowired
  private AccountRepository accountRepository;


  public void clearFriends() {
    friendRequestRepository.deleteAll();
  }


  public long addFriendRequestToDB(final String from, final String to) {
    if (friendRequestRepository.findByEitherSenderOrReceiver(from, to).size() > 0) {
      throw new IllegalStateException();
    }
    FriendRequest f = new FriendRequest(false,
        accountRepo.findByUsername(from),
        accountRepo.findByUsername(to)
    );
    return friendRequestRepository.save(f).getId();
  }

  public void sendFriendInvitation(final String from, final String to) {
    final Account aFrom = accountRepository.findByUsername(from);
    final Account aTo = accountRepository.findByUsername(to);
    final FriendRequest req = new FriendRequest(false, aFrom, aTo);
    friendRequestRepository.save(req);
  }

  public List<FriendRequest> getAllFriendRequests() {
    return friendRequestRepository.findAll();
  }

  public List<FriendRequest> getUsersPendingRequests(final Long id) {
    final Optional<Account> res = accountRepo.findById(id);
    final String username = res.get().getUsername();
    return friendRequestRepository.findReceivedPendingQuery(username);
  }

  public Set<Account> getPendingSent(final String uname) {
    final Set<Account> sentTo = new LinkedHashSet<>();
    final List<FriendRequest> reqs = friendRequestRepository.findSentPendingQuery(uname);
    for (final FriendRequest req : reqs) {
      sentTo.add(req.getTargetFriend());
    }
    return sentTo;
  }

  public Set<Account> getPendingReceived(final String uname) {
    final Set<Account> receivedFrom = new LinkedHashSet<>();
    final List<FriendRequest> reqs = friendRequestRepository.findReceivedPendingQuery(uname);
    for (final FriendRequest req : reqs) {
      receivedFrom.add(req.getIssuer());
    }
    return receivedFrom;
  }

  public Set<Account> getFriends(final String uname) {
    final Set<Account> friends = new LinkedHashSet<>();
    final List<FriendRequest> reqs = friendRequestRepository.findFriends(uname);
    for (final FriendRequest req : reqs) {
      friends.add(req.getIssuer());
      friends.add(req.getTargetFriend());
    }
    return friends;
  }

  public long removeFriendRequestFromDB(final String from, final String to) {
    final List<FriendRequest> reqs = friendRequestRepository.findByEitherSenderOrReceiver(from, to);
    assert reqs.size() == 1;
    final long id = reqs.get(0).getId();
    friendRequestRepository.remove(id);
    return id;
  }

  public long acceptFriendship(final String from, final String to) {
    final List<FriendRequest> reqs = friendRequestRepository.findByEitherSenderOrReceiver(from, to);
    assert reqs.size() == 1;
    final FriendRequest req = reqs.get(0);
    assert req.getIssuer().getUsername().equals(from)
      && req.getTargetFriend().getUsername().equals(to);
    final long id = req.getId();
    friendRequestRepository.acceptFriendrequest(id);
    return id;
  }

}

