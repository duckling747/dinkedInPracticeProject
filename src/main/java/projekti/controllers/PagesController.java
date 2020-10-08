package projekti.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    
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
}
