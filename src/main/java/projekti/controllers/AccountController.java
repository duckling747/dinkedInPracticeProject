package projekti.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import projekti.models.Account;
import projekti.models.FriendRequest;
import projekti.models.ProfilePicture;
import projekti.services.AccountService;
import projekti.services.FriendService;
import projekti.services.ProfilePictureService;

@RestController
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private FriendService friendService;

  @Autowired
  private ProfilePictureService picService;

  private static final String ACCOUNTS = "/accounts";

  @GetMapping(ACCOUNTS)
  public List<Account> listAccounts() {
    return accountService.findAll();
  }

  @PostMapping(ACCOUNTS)
  public Account addAccount(@RequestBody Account account) {

    if (accountService.findByUsername(account.getUsername()) != null) {
      throw new RuntimeException();
    }
    return accountService.addAccountToDB(account);
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

  @GetMapping(ACCOUNTS + "/logged")
  public Map<String, String> getLogged() {
    final String uname = accountService.getLoggedInUser();
    return Map.of("username", uname);
  }

  @GetMapping(ACCOUNTS + "/manualtest")
  public Map<String,String> addManuallyDefaultStuff() throws Exception {
    accountService.clearAccounts();
    friendService.clearFriends();
    accountService.addAccountToDB("Arnold", "b", "c", "pw");
    accountService.addAccountToDB("Bertrand", "b", "c", "pw");
    accountService.addAccountToDB("Bartholomew", "b", "c", "pw");
    accountService.addAccountToDB("Christine", "b", "c", "pw");
    accountService.addAccountToDB("Quentin", "b", "c", "pw");
    accountService.addAccountToDB("Ruth", "b", "c", "pw");
    friendService.addFriendRequestToDB("Arnold", "Bertrand");
    friendService.addFriendRequestToDB("Arnold", "Bartholomew");
    friendService.addFriendRequestToDB("Bartholomew", "Christine");
    return Map.of("result", "success");
  }

  @GetMapping(ACCOUNTS + "/friendrequests")
  public List<FriendRequest> getFReqs() {
    return friendService.getAllFriendRequests();
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

}
