package projekti.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.Account;
import projekti.models.FriendRequest;
import projekti.models.ProfilePicture;
import projekti.repositories.AccountRepository;
import projekti.repositories.FriendRequestRepository;
import projekti.repositories.ProfilePictureRepository;

@Transactional
@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ProfilePictureRepository profilePicRepo;

  public String getLoggedInUser() {
    final Object principal = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
    final String uname;
    if (principal instanceof UserDetails) {
      uname = ((UserDetails) principal).getUsername();
    } else {
      uname = principal.toString();
    }
    return uname;
  }

  public List<Account> findAll() {
    return accountRepo.findAll();
  }

  public Account findById(long id) {
    Optional<Account> a = accountRepo.findById(id);
    return a.get();
  }

  public Account findByUsername(String uname) {
    return accountRepo.findByUsername(uname);
  }

  public Page<Account> findPaginated(final Pageable pageable) {
    final int pageSize = pageable.getPageSize();
    final int currentPage = pageable.getPageNumber();
    final int startItem = currentPage * pageSize;
    List<Account> list = accountRepo.findAll();

    final int count = list.size();
    if (count < startItem) {
      list = Collections.emptyList();
    } else {
      final int toIndex = Math.min(startItem + pageSize, count);
      list = list.subList(startItem, toIndex);
    }
    final Page<Account> accountPage =
        new PageImpl<>(list, PageRequest.of(currentPage, pageSize), count);
    return accountPage;
  }

  public Account addAccountToDB(Account a) {
    a.setPassword(passwordEncoder.encode(a.getPassword()));
    return accountRepo.save(a);
  }

  public void clearAccounts() {
    accountRepo.deleteAll();
  }

  public long addAccountToDB(String uname, String fname, String lname, String pw)
      throws Exception {
    Account acco = new Account();
    acco.setFirstName(fname);
    acco.setLastName(lname);
    acco.setPassword(passwordEncoder.encode(pw));
    acco.setUsername(uname);
    return accountRepo.save(acco).getId();
  }


  public long addProfilePictureToDB(String uname, byte[] data) {
    Account a = accountRepo.findByUsername(uname);
    ProfilePicture pic = new ProfilePicture(a, data);
    return profilePicRepo.save(pic).getId();
  }

}
