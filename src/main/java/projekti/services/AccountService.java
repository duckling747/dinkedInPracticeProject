package projekti.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.Account;
import projekti.repositories.AccountRepository;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  /**
   * Add a connection between users.
   * @param target a username
   * @param follower another username
   */
  @Transactional
  public void addConnection(final String target, final String follower) {
    final Account targetAccount = accountRepository.findByUsername(target);
    final Account followerAccount = accountRepository.findByUsername(follower);
    targetAccount.getConnections().add(followerAccount);
    followerAccount.getConnections().add(targetAccount);
  }

  /**
   * Remove a connection between users.
   * @param target a username
   * @param follower another username
   */
  @Transactional
  public void removeConnection(final String target, final String follower) {
    final Account targetAccount = accountRepository.findByUsername(target);
    final Account followerAccount = accountRepository.findByUsername(follower);
    targetAccount.getConnections().remove(followerAccount);
    followerAccount.getConnections().remove(targetAccount);
  }

}
