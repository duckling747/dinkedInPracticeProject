package projekti.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import projekti.models.Account;
import projekti.repositories.AccountRepository;

@RestController
public class AccountController {
    
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
}
