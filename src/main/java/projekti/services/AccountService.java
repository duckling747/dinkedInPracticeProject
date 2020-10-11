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
   * Add a follower to target.
   * @param target represents the one followed
   * @param follower represents the follower to-be
   */
  @Transactional
  public void addFollower(final String target, final String follower) {
    final Account targetAccount = accountRepository.findByUsername(target);
    final Account followerAccount = accountRepository.findByUsername(follower);
    targetAccount.getFollowers().add(followerAccount);
  }

  /**
   * Remove a follower from target.
   * @param target represents the one followed
   * @param follower represents the removed follower
   */
  @Transactional
  public void removeFollower(final String target, final String follower) {
    final Account targetAccount = accountRepository.findByUsername(target);
    final Account followerAccount = accountRepository.findByUsername(follower);
    targetAccount.getFollowers().remove(followerAccount);
  }

}
