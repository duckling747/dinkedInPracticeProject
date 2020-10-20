package projekti.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projekti.models.Account;
import projekti.models.FriendRequest;
import projekti.repositories.AccountRepository;
import projekti.repositories.FriendRequestRepository;

@RestController
@RequestMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
public class AccountController {

  @Autowired
  private FriendRequestRepository friendRequestRepository;
    
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/accounts")
  public List<Account> listAccounts() {
    return accountRepository.findAll();
  }

  @GetMapping("/accounts/{id}")
  public Account getAccount(@PathVariable Long id) {
    Optional<Account> o = accountRepository.findById(id);
    return o.get();
  }

  @PostMapping("/accounts")
  public Account addAccount(@RequestBody Account account) {

    if (accountRepository.findByUsername(account.getUsername()) != null) {
      throw new RuntimeException();
    }
    account.setPassword(passwordEncoder.encode(account.getPassword()));
    return accountRepository.save(account);
  }

  @GetMapping("/accounts/logged")
  public Map<String, String> getLogged() {
    final Object principal = SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal();
    String uname;
    if (principal instanceof UserDetails) {
      uname = ((UserDetails) principal).getUsername();
    } else {
      uname = principal.toString();
    }
    return Map.of("username", uname);
  }

  private long addAccountToDB(String uname, String fname, String lname, String pw) {
    Account acco = new Account();
    acco.setFirstName(fname);
    acco.setLastName(lname);
    acco.setPassword(passwordEncoder.encode(pw));
    acco.setUsername(uname);
    return accountRepository.save(acco).getId();
  }

  private long addFriendRequestToDB(String uname1, String uname2) {
    FriendRequest f = new FriendRequest(false,
        accountRepository.findByUsername(uname1),
        accountRepository.findByUsername(uname2)
    );
    return friendRequestRepository.save(f).getId();
  }



  @GetMapping("/accounts/manualtest")
  public Map<String,String> addManuallyDefaultStuff() {
    accountRepository.deleteAll();
    friendRequestRepository.deleteAll();
    addAccountToDB("Arnold", "b", "c", "pw");
    addAccountToDB("Bertrand", "b", "c", "pw");
    addAccountToDB("Bartholomew", "b", "c", "pw");
    addAccountToDB("Christine", "b", "c", "pw");
    addAccountToDB("Quentin", "b", "c", "pw");
    addAccountToDB("Ruth", "b", "c", "pw");
    addFriendRequestToDB("Arnold", "Bertrand");
    addFriendRequestToDB("Arnold", "Bartholomew");
    addFriendRequestToDB("Bartholomew", "Christine");
    return Map.of("result", "success");
  }

  @GetMapping("/accounts/friendrequests")
  public List<FriendRequest> getFReqs() {
    return friendRequestRepository.findByAcceptedFalse();
  }

}
