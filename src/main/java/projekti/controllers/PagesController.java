package projekti.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projekti.models.Account;
import projekti.services.AccountService;
import projekti.services.FriendService;

@Controller
public class PagesController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private FriendService friendService;

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
