package projekti.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projekti.models.Account;
import projekti.repositories.AccountRepository;
import projekti.services.AccountService;

@Controller
public class PagesController {

  @Autowired
  private AccountRepository accountRepo;

  @Autowired
  private AccountService accountService;
    
  @GetMapping("/")
  public String index() {
    return "index";
  }

  @GetMapping("/jobs")
  public String jobs() {
    return "jobs";
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
  public String userWall() {
    return "wall";
  }

  @GetMapping("/people")
  public String findPeople(
      final Model model,
      @RequestParam(defaultValue = "0") final Integer page,
      @RequestParam(defaultValue = "5") final Integer size) {

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

    return "people";
  }

}
