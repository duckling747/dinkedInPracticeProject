package projekti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.Account;
import projekti.models.FriendRequest;
import projekti.models.Post;
import projekti.repositories.AccountRepository;
import projekti.repositories.FriendRequestRepository;
import projekti.repositories.PostRepository;

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

  @Autowired
  private PostRepository postRepository;

  public static Random R = new Random();

  @Before
  public void resetDB() {
    accountRepository.deleteAll();
    friendRequestRepository.deleteAll();
  }

  private static String randomString() {
    byte[] array = new byte[20];
    R.nextBytes(array);
    return new String(array, Charset.forName("UTF-8"));
  }

  private void addSomeAccountsToDB(int count) {
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

  private long addAccountToDB(String uname, String fname, String lname, String pw) {
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

  private long addFriendRequestToDB(long id1, long id2) {
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
    long id1 = addAccountToDB(user1, "fname", "lname", "pw");
    long id2 = addAccountToDB(user2, "fname2", "lname2", "pw2");
    addFriendRequestToDB(id1, id2);
    assertEquals(1L, friendRequestRepository.count());
    assertTrue(friendRequestRepository.findAll()
        .stream()
        .anyMatch(friendreq -> friendreq.getIssuer().getUsername().equals(user1)
          && friendreq.getTargetFriend().getUsername().equals(user2)
        )
    );
  }

  private void setFriendsForTests() {
    final long a = addAccountToDB("A", "fname", "lname", "pw");
    final long b = addAccountToDB("B", "fname", "lname", "pw");
    final long c = addAccountToDB("C", "fname", "lname", "pw");
    final long d = addAccountToDB("D", "fname", "lname", "pw");
    addFriendRequestToDB(a, b);
    addFriendRequestToDB(b, c);
    addFriendRequestToDB(a, c);
    final long fr = addFriendRequestToDB(c, d);
    final FriendRequest f = friendRequestRepository.getOne(fr);
    f.setAccepted(true);
    friendRequestRepository.save(f);
  }

  @Test
  public void friendRequestSentPendingQueryWorks() {
    setFriendsForTests();
    List<FriendRequest> sentPending = friendRequestRepository.findSentPendingQuery("A");
    for (final FriendRequest f : sentPending) {
      assertFalse(f.isAccepted());
      assertTrue(f.getIssuer().getUsername().equals("A"));
      assertFalse(f.getIssuer().getUsername().equals("B"));
      assertFalse(f.getIssuer().getUsername().equals("C"));
      assertFalse(f.getIssuer().getUsername().equals("D"));
      assertFalse(f.getTargetFriend().getUsername().equals("A"));
      assertFalse(f.getTargetFriend().getUsername().equals("A"));
      assertFalse(f.getTargetFriend().getUsername().equals("A"));
    }
    assertEquals(2, sentPending.size());
  }

  @Test
  public void friendRequestReceivedPendingQueryWorks() {
    setFriendsForTests();
    List<FriendRequest> receivedPending = friendRequestRepository.findReceivedPendingQuery("B");
    for (final FriendRequest f : receivedPending) {
      assertFalse(f.isAccepted());
      assertTrue(f.getTargetFriend().getUsername().equals("B"));
      assertFalse(f.getTargetFriend().getUsername().equals("A"));
      assertFalse(f.getTargetFriend().getUsername().equals("C"));
      assertFalse(f.getTargetFriend().getUsername().equals("D"));
      assertFalse(f.getIssuer().getUsername().equals("B"));
      assertFalse(f.getIssuer().getUsername().equals("B"));
      assertFalse(f.getIssuer().getUsername().equals("B"));
    }
    assertEquals(1, receivedPending.size());
  }

  @Test
  public void friendQueryWorks1() {
    setFriendsForTests();
    List<FriendRequest> sentPending = friendRequestRepository.findSentPendingQuery("A");
    FriendRequest fr = sentPending.get(0);
    final String tName = fr.getTargetFriend().getUsername();
    fr.setAccepted(true);
    friendRequestRepository.saveAndFlush(fr);
    List<FriendRequest> friends = friendRequestRepository.findFriends("A");
    assertEquals(1, friends.size());
    assertTrue(friends.get(0).getIssuer().getUsername().equals("A")
        && friends.get(0).getTargetFriend().getUsername().equals(tName));
  }

  @Test
  public void friendQueryWorks2() {
    setFriendsForTests();
    List<FriendRequest> sentPending = friendRequestRepository.findSentPendingQuery("A");
    for (final FriendRequest f : sentPending) {
      f.setAccepted(true);
    }
    friendRequestRepository.saveAll(sentPending);
    friendRequestRepository.flush();

    List<FriendRequest> friends = friendRequestRepository.findFriends("A");
    assertEquals(sentPending.size(), friends.size());
    for (final FriendRequest f : friends) {
      assertTrue(f.getIssuer().getUsername().equals("A"));
      assertTrue(sentPending.contains(f));
    }

  }

  @Test
  public void eitherOrQueryWorks1() {
    setFriendsForTests();
    List<FriendRequest> res = friendRequestRepository.findByEitherSenderOrReceiver("A", "B");
    assertEquals(1, res.size());
    FriendRequest f = res.get(0);
    assertTrue((f.getIssuer().getUsername().equals("A")
        && f.getTargetFriend().getUsername().equals("B"))
    );
  }

  @Test
  public void eitherOrQueryWorks2() {
    setFriendsForTests();
    List<FriendRequest> res = friendRequestRepository.findByEitherSenderOrReceiver("B", "A");
    assertEquals(1, res.size());
    FriendRequest f = res.get(0);
    assertTrue((f.getIssuer().getUsername().equals("A")
        && f.getTargetFriend().getUsername().equals("B"))
    );
  }

  @Test
  public void deleteFriendQueryWorks() {
    setFriendsForTests();
    List<FriendRequest> res = friendRequestRepository.findAll();
    assertEquals(4, res.size());
    final long removeMeId = res.get(R.nextInt(res.size())).getId();
    friendRequestRepository.remove(removeMeId);
    res = friendRequestRepository.findAll();
    assertEquals(3, res.size());
    assertFalse(
        res.stream().anyMatch(f -> f.getId() == removeMeId)
    );
  }

  private void makePost(Account a, String post) {
    final Post p = new Post();
    p.setUser(a);
    p.setTimestamp(LocalDateTime.now());
    p.setPost(post);
    postRepository.save(p);
  }

  private void makePosts() {
    final Account a = accountRepository.findByUsername("A");
    makePost(a, "apost");
    final Account b = accountRepository.findByUsername("B");
    makePost(b, "bpost");
    makePost(b, "bpost2");
    final Account c = accountRepository.findByUsername("C");
    makePost(c, "cpost");
    final Account d = accountRepository.findByUsername("D");
    makePost(d, "dpost");
  }

  @Test
  public void postQueryWorks1() {
    setFriendsForTests();
    makePosts();
    final Account a = accountRepository.findByUsername("A");
    var posts = postRepository.findAllUsersAndFriendsPosts(a.getId(), PageRequest.of(0, 25));
    assertEquals(1, posts.size());
    assertEquals("apost", posts.get(0).getPost());
  }

  @Test
  public void postQueryWorks2() {
    setFriendsForTests();
    makePosts();
    final Account b = accountRepository.findByUsername("B");
    var posts = postRepository.findAllUsersAndFriendsPosts(b.getId(), PageRequest.of(0, 25));
    assertEquals(2, posts.size());
    assertTrue(
        posts.stream()
        .anyMatch(p -> p.getPost().equals("bpost"))
    );
    assertTrue(
        posts.stream()
        .anyMatch(p -> p.getPost().equals("bpost2"))
    );
  }

  @Test
  public void postQueryWorks3() {
    setFriendsForTests();
    makePosts();
    final Account c = accountRepository.findByUsername("C");
    var posts = postRepository.findAllUsersAndFriendsPosts(c.getId(), PageRequest.of(0, 25));
    assertEquals(2, posts.size());
    assertTrue(
        posts.stream()
        .anyMatch(p -> p.getPost().equals("cpost"))
    );
    assertTrue(
        posts.stream()
        .anyMatch(p -> p.getPost().equals("dpost"))
    );
  }

}
