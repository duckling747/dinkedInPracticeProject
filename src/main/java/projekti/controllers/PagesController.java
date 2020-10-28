package projekti.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import projekti.models.Account;
import projekti.models.ProfilePicture;
import projekti.services.AccountService;
import projekti.services.FriendService;
import projekti.services.ProfilePictureService;

@Controller
public class PagesController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private FriendService friendService;

  @Autowired
  private ProfilePictureService picService;

  @GetMapping("/")
  public String index(final Model model) {
    final String uname = accountService.getLoggedInUser();
    final Account a = accountService.findByUsername(uname);
    model.addAttribute("username", uname);
    model.addAttribute("currentUser", a);
    return "index";
  }

  @GetMapping("/settings")
  public String settings(final Model model) {
    final String uname = accountService.getLoggedInUser();
    final Account a = accountService.findByUsername(uname);
    model.addAttribute("username", uname);
    model.addAttribute("currentUser", a);
    return "settings";
  }

  @PostMapping(path = "/settings/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public String postAccount(
      @PathVariable Long id,
      @RequestParam("file") MultipartFile image,
      @RequestParam("firstName") String fname,
      @RequestParam("lastName") String lname,
      @RequestParam("password") String pw) throws Exception {
    final Account a = accountService.findById(id);
    if (!fname.isEmpty()) {
      a.setFirstName(fname);
    }
    if (!lname.isEmpty()) {
      a.setLastName(lname);
    }
    if (!pw.isEmpty()) {
      a.setPassword(pw);
    }
    if (image != null && !image.isEmpty()) {
      final ProfilePicture pp
          = new ProfilePicture(a, image.getBytes());
      a.setImage(pp);
      picService.addProfilePicToDB(pp);
    }
    accountService.addAccountToDB(a);
    return "redirect:/settings";
  }

  @GetMapping("/join")
  public String reactJoin() {
    return "redirect:/register/index.html";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/wall")
  public String userWall(@RequestParam Long id) {
    return "redirect:/wall/index.html?id=" + id;
  }

  @GetMapping("/people")
  public String findPeople(
      final Model model,
      @RequestParam(defaultValue = "0") final Integer page,
      @RequestParam(defaultValue = "2") final Integer size) {

    final Page<Account> accountPage = accountService
        .findPaginated(PageRequest.of(page, size));
    model.addAttribute("accountPage", accountPage);
    final int totalPages = accountPage.getTotalPages();
    if (totalPages > 0) {
      final List<Integer> pageNums = IntStream.range(0, totalPages)
          .boxed()
          .collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNums);
    }

    final String uname = accountService.getLoggedInUser();
    final Set<Account> friends = friendService.getFriends(uname);
    final Set<Account> sent = friendService.getPendingSent(uname);
    final Set<Account> received = friendService.getPendingReceived(uname);
    model.addAttribute("me", uname);
    model.addAttribute("username", uname);
    model.addAttribute("friends", friends);
    model.addAttribute("sent", sent);
    model.addAttribute("received", received);

    return "people";
  }

}
