package projekti.services;

import java.util.HashSet;
import java.util.List;
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


  public long addFriendRequestToDB(String uname1, String uname2) {
    FriendRequest f = new FriendRequest(false,
        accountRepo.findByUsername(uname1),
        accountRepo.findByUsername(uname2)
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

  public Set<Account> getPendingSent(final String uname) {
    final Set<Account> sentTo = new HashSet<>();
    final List<FriendRequest> reqs = friendRequestRepository.findSentPendingQuery(uname);
    for (final FriendRequest req : reqs) {
      sentTo.add(req.getTargetFriend());
    }
    return sentTo;
  }

  public Set<Account> getPendingReceived(final String uname) {
    final Set<Account> receivedFrom = new HashSet<>();
    final List<FriendRequest> reqs = friendRequestRepository.findReceivedPendingQuery(uname);
    for (final FriendRequest req : reqs) {
      receivedFrom.add(req.getIssuer());
    }
    return receivedFrom;
  }

  public Set<Account> getFriends(final String uname) {
    final Set<Account> friends = new HashSet<>();
    final List<FriendRequest> reqs = friendRequestRepository.findFriends(uname);
    for (final FriendRequest req : reqs) {
      friends.add(req.getIssuer());
      friends.add(req.getTargetFriend());
    }
    return friends;
  }

}

