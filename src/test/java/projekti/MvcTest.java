package projekti;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import projekti.models.ProfilePicture;
import projekti.repositories.AccountRepository;
import projekti.repositories.FriendRequestRepository;
import projekti.repositories.ProfilePictureRepository;
import projekti.services.AccountService;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class MvcTest {
    
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private ProfilePictureRepository profilePictureRepository;

  @Autowired
  private AccountService accountService;

  @Autowired
  private FriendRequestRepository friendRequestRepository;

  @Before
  public void reset() {
    accountRepository.deleteAll();
    profilePictureRepository.deleteAll();
  }

  @Test
  public void indexGetOk() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());
  }

  private static String makeJson(String uname, String fname, String lname, String pw) {
    var json = new StringBuilder();
    json.append("{");
    json.append("\"username\": \"" + uname + "\",\n");
    json.append("\"password\": \"" + pw + "\",\n");
    json.append("\"firstName\": \"" + fname + "\",\n");
    json.append("\"lastName\": \"" + lname + "\"\n");
    json.append("}");
    return json.toString();
  }
  
  @Test
  public void postAccountWorks() throws Exception {
    final String json = makeJson("uname", "fname", "lname", "pw");
    mockMvc.perform(post("/accounts")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
    ).andExpect(status().isOk());
    final var a = accountRepository.findByUsername("uname");
    assertNotNull(a);
  }

  private byte[] sendTestPicture(final long id) throws Exception {
    final File image = new ClassPathResource("coupletest.png").getFile();
    assertNotNull(image);
    final byte[] arr = FileUtils.readFileToByteArray(image);
    final MockMultipartFile multipartFile
        = new MockMultipartFile("file", arr);
    mockMvc.perform(multipart("/accounts/" + id + "/image").file(multipartFile))
      .andExpect(status().isOk());
    return arr;
  }

  private void getTestPicture(final long id, final byte[] arr) throws Exception {
    mockMvc.perform(get("/accounts/" + id + "/image"))
      .andExpect(content().contentType(MediaType.IMAGE_PNG))
      .andExpect(content().bytes(arr))
      .andExpect(status().isOk());
  }

  @Test
  public void postAndGetImageToFromAccountWorks() throws Exception {
    final String accountJson = makeJson("uname", "fname", "lname", "pw");
    mockMvc.perform(post("/accounts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(accountJson)
    ).andReturn();
    final long id = accountRepository.findByUsername("uname").getId();
    final byte[] arr = sendTestPicture(id);
    getTestPicture(id, arr);
    List<ProfilePicture> pps = profilePictureRepository.findAll();
    assertEquals(1, pps.size());
  }

  @Test
  public void getImageTestEqualityAnotherWay() throws Exception {
    final String accountJson = makeJson("uname", "fname", "lname", "pw");
    mockMvc.perform(post("/accounts")
      .contentType(MediaType.APPLICATION_JSON)
      .content(accountJson)
    ).andReturn();
    final long id = accountRepository.findByUsername("uname").getId();
    final byte[] arr = sendTestPicture(id);
    ProfilePicture pp = profilePictureRepository.findProfilePicture(id);
    assertNotNull(pp);
    assertArrayEquals(arr, pp.getData());
  }

  private static String makeFriendJson(String friendA, String friendB) {
    StringBuilder json = new StringBuilder();
    json.append("{");
    json.append("\"from\": \"" + friendA + "\",\n");
    json.append("\"to\": \"" + friendB + "\"\n");
    json.append("}");
    return json.toString();
  }

  @Test
  public void addFriendRequestWorks() throws Exception {
    accountService.addAccountToDB("one", "fname", "lname", "pw");
    accountService.addAccountToDB("two", "fname", "lname", "pw");
    final String frJson = makeFriendJson("one", "two");
    mockMvc.perform(post("/accounts/friendrequests")
        .contentType(MediaType.APPLICATION_JSON)
        .content(frJson)
    ).andExpect(status().isOk());
    var res = friendRequestRepository.findAll();
    assertEquals(1, res.size());
    assertTrue(res
        .stream()
        .anyMatch(friendreq -> friendreq.getIssuer().getUsername().equals("one")
          && friendreq.getTargetFriend().getUsername().equals("two")
        )
    );
  }

  @Test
  public void deleteFriendRequestWorks() throws Exception {
    accountService.addAccountToDB("one", "fname", "lname", "pw");
    accountService.addAccountToDB("two", "fname", "lname", "pw");
    final String frJson = makeFriendJson("one", "two");
    mockMvc.perform(post("/accounts/friendrequests")
        .contentType(MediaType.APPLICATION_JSON)
        .content(frJson)
    ).andExpect(status().isOk());
    assertEquals(1, friendRequestRepository.findAll().size());
    assertEquals(1, friendRequestRepository.findByEitherSenderOrReceiver("one", "two").size());
    mockMvc.perform(delete("/accounts/friendrequests")
        .contentType(MediaType.APPLICATION_JSON)
        .content(frJson)
    ).andExpect(status().isOk());
    assertTrue(friendRequestRepository.findAll().isEmpty());
  }

}
