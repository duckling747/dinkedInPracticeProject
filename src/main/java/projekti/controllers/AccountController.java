package projekti.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import projekti.models.Account;
import projekti.models.Comment;
import projekti.models.FriendRequest;
import projekti.models.Post;
import projekti.models.ProfilePicture;
import projekti.services.AccountService;
import projekti.services.FriendService;
import projekti.services.PostService;
import projekti.services.ProfilePictureService;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private FriendService friendService;

  @Autowired
  private ProfilePictureService picService;

  @Autowired
  private PostService postService;

  private static final String ACCOUNTS = "/accounts";

  @GetMapping(ACCOUNTS)
  public List<Account> listAccounts() {
    return accountService.findAll();
  }

  @PostMapping(ACCOUNTS)
  public Long addAccount(@RequestBody Map<String,String> account) throws Exception {

    if (accountService.findByUsername(account.get("username")) != null) {
      throw new RuntimeException();
    }
    return accountService.addAccountToDB(account.get("username"),
        account.get("firstName"), account.get("lastName"), account.get("password"));
  }

  @GetMapping(ACCOUNTS + "/{id}")
  public Account getAccount(@PathVariable Long id) {
    return accountService.findById(id);
  }

  @GetMapping(path = ACCOUNTS + "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] getProfilePic(@PathVariable Long id) throws Exception {
    return picService.getProfilePic(id);
  }

  @PostMapping(path = ACCOUNTS + "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Long addProfilePic(@PathVariable Long id,
      @RequestParam("file") MultipartFile file) throws IOException {
    final Account a = accountService.findById(id);
    if (a == null) {
      throw new RuntimeException();
    }
    ProfilePicture pp = new ProfilePicture();
    pp.setAccount(a);
    pp.setData(file.getBytes());
    return picService.addProfilePicToDB(pp).getId();
  }

  @GetMapping(path = ACCOUNTS + "/{id}/posts")
  public List<Post> getPostsForThisGuy(@PathVariable Long id) {
    final List<Post> res = postService.getUsersAndFriendsPosts(id);
    return res;
  }

  @PostMapping(path = ACCOUNTS + "/{id}/posts", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Post addUserPost(@PathVariable Long id, @RequestBody Map<String,String> postJson) {
    return postService.addPost(id, postJson.get("post"));
  }

  @PostMapping(path = ACCOUNTS + "/{userId}/posts/{postId}/like")
  @ResponseStatus(value = HttpStatus.OK)
  public void sendLike(@PathVariable Long userId, @PathVariable Long postId) {
    postService.likePost(userId, postId);
  }

  @GetMapping(path = ACCOUNTS + "/{userId}/posts/{postId}/comment")
  public List<Comment> getComments(@PathVariable Long userId, @PathVariable Long postId) {
    return postService.getComments(postId);
  }

  @PostMapping(path = ACCOUNTS + "/{userId}/posts/{postId}/comment",
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public void sendComment(@PathVariable Long userId, @PathVariable Long postId,
      @RequestBody Map<String,String> commentJson) {
    postService.addComment(userId, postId, commentJson.get("comment"));
  }

  @GetMapping(ACCOUNTS + "/logged")
  public Map<String, String> getLogged() {
    final String uname = accountService.getLoggedInUser();
    return Map.of("username", uname);
  }

  @GetMapping(ACCOUNTS + "/test")
  public Map<String,String> testingRouteSetUp() throws Exception {
    accountService.clearAccounts();
    friendService.clearFriends();
    final long arnieId = accountService.addAccountToDB("Arnold", "b", "c", "pw");
    accountService.addAccountToDB("Bertrand", "b", "c", "pw");
    final long bartId = accountService.addAccountToDB("Bartholomew", "b", "c", "pw");
    accountService.addAccountToDB("Christine", "b", "c", "pw");
    accountService.addAccountToDB("Quentin", "b", "c", "pw");
    accountService.addAccountToDB("Ruth", "b", "c", "pw");
    friendService.addFriendRequestToDB("Arnold", "Bertrand");
    friendService.addFriendRequestToDB("Arnold", "Bartholomew");
    friendService.addFriendRequestToDB("Bartholomew", "Christine");
    friendService.acceptFriendship("Bartholomew", "Christine");
    postService.addPost(bartId, "Tämä on kaikista hienoin postaus");
    postService.addPost(bartId, "Toinen postaus");
    postService.addPost(bartId, "Blablablabldbadsfijdsaöofjösaodijfoöasdjfiosadjföoad. aosdjfasd.");
    postService.addPost(arnieId, "Ei jumankekka tät ohjelmointii tää on nii kivaa!");
    
    return Map.of("result", "success");
  }

  @GetMapping(ACCOUNTS + "/friendrequests")
  public List<FriendRequest> getFReqs() {
    return friendService.getAllFriendRequests();
  }

  @GetMapping(ACCOUNTS + "/{id}/friendrequests")
  public List<FriendRequest> getUsersFReqs(@PathVariable Long id) {
    return friendService.getUsersPendingRequests(id);
  }

  @PostMapping(path = ACCOUNTS + "/friendrequests", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Long postFriendRequest(@RequestBody Map<String,String> json) {
    return friendService.addFriendRequestToDB(
        json.get("from"),
        json.get("to"));
  }

  @DeleteMapping(path = ACCOUNTS + "/friendrequests", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Long deleteFriendRequest(@RequestBody Map<String,String> json) {
    return friendService.removeFriendRequestFromDB(
        json.get("from"),
        json.get("to"));
  }

  @PutMapping(path = ACCOUNTS + "/friendrequests", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Long acceptFriendship(@RequestBody Map<String,String> json) {
    return friendService.acceptFriendship(
        json.get("from"),
        json.get("to")
    );
  }

}
