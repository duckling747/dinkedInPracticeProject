package projekti.services;

import java.util.List;

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

  public void getPendingFriendRequests(final String uname) {
      // do stuff
      
  }
}
