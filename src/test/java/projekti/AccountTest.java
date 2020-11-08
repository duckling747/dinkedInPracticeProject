package projekti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
import projekti.models.Skill;
import projekti.repositories.AccountRepository;
import projekti.repositories.FriendRequestRepository;
import projekti.repositories.PostRepository;
import projekti.repositories.SkillRepository;

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

  @Autowired
  private SkillRepository skillRepository;

  public static Random R = new Random();

  @Before
  public void resetDB() {
    accountRepository.deleteAll();
    friendRequestRepository.deleteAll();
  }

  private static String randomString() {
    final byte[] array = new byte[20];
    R.nextBytes(array);
    return new String(array, Charset.forName("UTF-8"));
  }

  private void addSomeAccountsToDB(int count) {
    final Account[] accounts = new Account[count];
    for (int i = 0; i < count; i++) {
      final Account acco = new Account(randomString(),
          passwordEncoder.encode(randomString()),
          randomString(), randomString());
      accounts[i] = acco;
    }
    accountRepository.saveAll(List.of(accounts));
  }

  private long addAccountToDB(String uname, String fname, String lname, String pw) {
    final Account acco = new Account(uname, passwordEncoder.encode(pw), fname, lname);
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
    final String uname = "same";
    addAccountToDB(uname, "fname", "lname", "pw");
    addAccountToDB(uname, "fname", "lname", "pw");
    assertTrue(accountRepository.findAll().stream().filter(
        account -> account.getUsername() == uname
    ).count() == 1L);
  }

  private long addFriendRequestToDB(long id1, long id2) {
    final FriendRequest f = new FriendRequest(false,
        accountRepository.getOne(id1),
        accountRepository.getOne(id2)
    );
    return friendRequestRepository.save(f).getId();
  }

  @Test
  public void addFriendRequestWorks() {
    final String user1 = "uname1";
    final String user2 = "uname2";
    final long id1 = addAccountToDB(user1, "fname", "lname", "pw");
    final long id2 = addAccountToDB(user2, "fname2", "lname2", "pw2");
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
    final long a = addAccountToDB("AAA", "fname", "lname", "pw");
    final long b = addAccountToDB("BBB", "fname", "lname", "pw");
    final long c = addAccountToDB("CCC", "fname", "lname", "pw");
    final long d = addAccountToDB("DDD", "fname", "lname", "pw");
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
    final List<FriendRequest> sentPending = friendRequestRepository.findSentPendingQuery("AAA");
    for (final FriendRequest f : sentPending) {
      assertFalse(f.isAccepted());
      assertTrue(f.getIssuer().getUsername().equals("AAA"));
      assertFalse(f.getIssuer().getUsername().equals("BBB"));
      assertFalse(f.getIssuer().getUsername().equals("CCC"));
      assertFalse(f.getIssuer().getUsername().equals("DDD"));
      assertFalse(f.getTargetFriend().getUsername().equals("AAA"));
      assertFalse(f.getTargetFriend().getUsername().equals("AAA"));
      assertFalse(f.getTargetFriend().getUsername().equals("AAA"));
    }
    assertEquals(2, sentPending.size());
  }

  @Test
  public void friendRequestReceivedPendingQueryWorks() {
    setFriendsForTests();
    List<FriendRequest> receivedPending = friendRequestRepository.findReceivedPendingQuery("BBB");
    for (final FriendRequest f : receivedPending) {
      assertFalse(f.isAccepted());
      assertTrue(f.getTargetFriend().getUsername().equals("BBB"));
      assertFalse(f.getTargetFriend().getUsername().equals("AAA"));
      assertFalse(f.getTargetFriend().getUsername().equals("CCC"));
      assertFalse(f.getTargetFriend().getUsername().equals("DDD"));
      assertFalse(f.getIssuer().getUsername().equals("BBB"));
      assertFalse(f.getIssuer().getUsername().equals("BBB"));
      assertFalse(f.getIssuer().getUsername().equals("BBB"));
    }
    assertEquals(1, receivedPending.size());
  }

  @Test
  public void friendQueryWorks1() {
    setFriendsForTests();
    final List<FriendRequest> sentPending = friendRequestRepository.findSentPendingQuery("AAA");
    final FriendRequest fr = sentPending.get(0);
    final String tName = fr.getTargetFriend().getUsername();
    fr.setAccepted(true);
    friendRequestRepository.saveAndFlush(fr);
    final List<FriendRequest> friends = friendRequestRepository.findFriends("AAA");
    assertEquals(1, friends.size());
    assertTrue(friends.get(0).getIssuer().getUsername().equals("AAA")
        && friends.get(0).getTargetFriend().getUsername().equals(tName));
  }

  @Test
  public void friendQueryWorks2() {
    setFriendsForTests();
    final List<FriendRequest> sentPending
        = friendRequestRepository.findSentPendingQuery("AAA");
    for (final FriendRequest f : sentPending) {
      f.setAccepted(true);
    }
    friendRequestRepository.saveAll(sentPending);
    friendRequestRepository.flush();

    final List<FriendRequest> friends = friendRequestRepository.findFriends("AAA");
    assertEquals(sentPending.size(), friends.size());
    for (final FriendRequest f : friends) {
      assertTrue(f.getIssuer().getUsername().equals("AAA"));
      assertTrue(sentPending.contains(f));
    }

  }

  @Test
  public void eitherOrQueryWorks1() {
    setFriendsForTests();
    final List<FriendRequest> res
        = friendRequestRepository.findByEitherSenderOrReceiver("AAA", "BBB");
    assertEquals(1, res.size());
    FriendRequest f = res.get(0);
    assertTrue((f.getIssuer().getUsername().equals("AAA")
        && f.getTargetFriend().getUsername().equals("BBB"))
    );
  }

  @Test
  public void eitherOrQueryWorks2() {
    setFriendsForTests();
    final List<FriendRequest> res
        = friendRequestRepository.findByEitherSenderOrReceiver("BBB", "AAA");
    assertEquals(1, res.size());
    final FriendRequest f = res.get(0);
    assertTrue((f.getIssuer().getUsername().equals("AAA")
        && f.getTargetFriend().getUsername().equals("BBB"))
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
    final Account a = accountRepository.findByUsername("AAA");
    makePost(a, "apost");
    final Account b = accountRepository.findByUsername("BBB");
    makePost(b, "bpost");
    makePost(b, "bpost2");
    final Account c = accountRepository.findByUsername("CCC");
    makePost(c, "cpost");
    final Account d = accountRepository.findByUsername("DDD");
    makePost(d, "dpost");
  }

  @Test
  public void postQueryWorks1() {
    setFriendsForTests();
    makePosts();
    final Account a = accountRepository.findByUsername("AAA");
    var posts = postRepository.findAllUsersAndFriendsPosts(a.getId(), PageRequest.of(0, 25));
    assertEquals(1, posts.size());
    assertEquals("apost", posts.get(0).getPost());
  }

  @Test
  public void postQueryWorks2() {
    setFriendsForTests();
    makePosts();
    final Account b = accountRepository.findByUsername("BBB");
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
    final Account c = accountRepository.findByUsername("CCC");
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

  @Test
  public void likeSkillsWorks() {
    setFriendsForTests();
    final Account a = accountRepository.findByUsername("AAA");
    Skill s = new Skill(a, "skill", "description", new LinkedHashSet<>());
    s = skillRepository.save(s);
    a.getSkills().add(s);
    accountRepository.save(a);
    final Account b = accountRepository.findByUsername("BBB");
    final Account c = accountRepository.findByUsername("CCC");
    s.getLikes().add(b);
    s.getLikes().add(c);
    skillRepository.save(s);
    Set<Account> setlikes = s.getLikes();
    assertEquals(2, setlikes.size());
  }

  @Test
  public void skillQueryWorks() {
    setFriendsForTests();
    final Account a = accountRepository.findByUsername("AAA");
    final Account b = accountRepository.findByUsername("BBB");
    final Account c = accountRepository.findByUsername("CCC");
    
    final Skill s1 = new Skill(a, "skill1", "description", new LinkedHashSet<>());
    final Skill s2 = new Skill(a, "skill2", "description", new LinkedHashSet<>());
    final Skill s3 = new Skill(a, "skill3", "description", new LinkedHashSet<>());
    final Skill s4 = new Skill(a, "skill4", "description", new LinkedHashSet<>());
    s1.getLikes().add(b);
    s1.getLikes().add(c);
    s4.getLikes().add(c);

    a.getSkills().addAll(List.of(s1,s2,s3,s4));
    accountRepository.save(a);
    skillRepository.saveAll(List.of(s1,s2,s3,s4));

    final List<Skill> skillsA = skillRepository.findUsersSkills(a.getId(), PageRequest.of(0, 5));
    assertEquals("skill1", skillsA.get(0).getTitle());
    assertEquals("skill4", skillsA.get(1).getTitle());
    assertTrue(skillsA.get(2).getTitle().equals("skill3")
        || skillsA.get(2).getTitle().equals("skill2"));
  }

  @Test
  public void skillQueryWorks2() {
    setFriendsForTests();
    final Account a = accountRepository.findByUsername("AAA");
    final Account b = accountRepository.findByUsername("BBB");
    final Account c = accountRepository.findByUsername("CCC");
    
    final Skill s1 = new Skill(a, "skill1", "description", new LinkedHashSet<>());
    final Skill s2 = new Skill(a, "skill2", "description", new LinkedHashSet<>());
    final Skill s3 = new Skill(a, "skill3", "description", new LinkedHashSet<>());
    final Skill s4 = new Skill(a, "skill4", "description", new LinkedHashSet<>());
    s2.getLikes().add(b);
    s2.getLikes().add(c);
    s3.getLikes().add(c);

    a.getSkills().addAll(List.of(s1,s2,s3,s4));
    accountRepository.save(a);
    skillRepository.saveAll(List.of(s1,s2,s3,s4));

    final List<Skill> skillsA = skillRepository.findUsersSkills(a.getId(), PageRequest.of(0, 5));
    assertEquals("skill2", skillsA.get(0).getTitle());
    assertEquals("skill3", skillsA.get(1).getTitle());
    assertTrue(skillsA.get(2).getTitle().equals("skill1")
        || skillsA.get(2).getTitle().equals("skill4"));
  }

}
