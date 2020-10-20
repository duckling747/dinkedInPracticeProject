package projekti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.Account;
import projekti.models.FriendRequest;
import projekti.repositories.AccountRepository;
import projekti.repositories.FriendRequestRepository;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountTest {
    
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private FriendRequestRepository friendRequestRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public static Random R = new Random();

  @Before
  public void resetDB() {
    accountRepository.deleteAll();
    friendRequestRepository.deleteAll();
  }

  public static String randomString() {
    byte[] array = new byte[20];
    R.nextBytes(array);
    return new String(array, Charset.forName("UTF-8"));
  }

  public void addSomeAccountsToDB(int count) {
    Account[] accounts = new Account[count];
    for (int i = 0; i < count; i++) {
      Account acco = new Account();
      acco.setFirstName(randomString());
      acco.setLastName(randomString());
      acco.setUsername(randomString());
      acco.setPassword(passwordEncoder.encode(randomString()));
      accounts[i] = acco;
    }
    accountRepository.saveAll(List.of(accounts));
  }

  public long addAccountToDB(String uname, String fname, String lname, String pw) {
    Account acco = new Account();
    acco.setFirstName(fname);
    acco.setLastName(lname);
    acco.setPassword(passwordEncoder.encode(pw));
    acco.setUsername(uname);
    return accountRepository.save(acco).getId();
  }

  @Test
  public void addAccountWorks() {
    String uname = "alfabeeta@theeta.fi";
    long id = addAccountToDB(uname, "Teppo", "Testi", "salasana");
    assertEquals(1L, accountRepository.count());
    assertEquals(id, accountRepository.findByUsername(uname).getId());
  }

  @Test
  public void realPasswordIsEncodedInDB() {
    String pw = "salasana";
    long id = addAccountToDB("asdf", "Teppo", "Testi", pw);
    String encodedActual = accountRepository.getOne(id).getPassword();
    assertNotEquals(pw, encodedActual);
  }

  @Test
  public void multipleAccountsCorrectCount() {
    addSomeAccountsToDB(5);
    assertEquals(5L, accountRepository.count());
    addSomeAccountsToDB(200);
    assertEquals(205L, accountRepository.count());
  }

  @Test(expected = DataIntegrityViolationException.class)
  public void cannotAddSameUsername() {
    String uname = "same";
    addAccountToDB(uname, "fname", "lname", "pw");
    addAccountToDB(uname, "fname", "lname", "pw");
    assertTrue(accountRepository.findAll().stream().filter(
        account -> account.getUsername() == uname
    ).count() == 1L);
  }

  public long addFriendRequestToDB(String uname1, String uname2) {
    long id1 = addAccountToDB(uname1, "fname", "lname", "pw");
    long id2 = addAccountToDB(uname2, "fname2", "lname2", "pw2");
    FriendRequest f = new FriendRequest(false,
        accountRepository.getOne(id1),
        accountRepository.getOne(id2)
    );
    return friendRequestRepository.save(f).getId();
  }

  @Test
  public void addFriendRequestWorks() {
    String user1 = "uname1";
    String user2 = "uname2";
    addFriendRequestToDB(user1, user2);
    assertEquals(1L, friendRequestRepository.count());
    assertTrue(friendRequestRepository.findAll()
        .stream()
        .anyMatch(friendreq -> friendreq.getIssuer().getUsername().equals(user1))
    );
    assertTrue(friendRequestRepository.findAll()
        .stream()
        .anyMatch(friendreq -> friendreq.getTargetFriend().getUsername().equals(user2))
    );
    
  }

}
