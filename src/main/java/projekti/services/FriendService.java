package projekti.services;

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
  private FriendRequestRepository friendRequestRepository;

  @Autowired
  private AccountRepository accountRepository;

  public void sendFriendInvitation(final String from, final String to) {
    final Account aFrom = accountRepository.findByUsername(from);
    final Account aTo = accountRepository.findByUsername(to);
    final FriendRequest req = new FriendRequest(false, aFrom, aTo);
    friendRequestRepository.save(req);
  }

  public void getPendingFriendRequests(final String uname) {
      // do stuff
      
  }
}
