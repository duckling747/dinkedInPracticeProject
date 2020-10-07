package projekti.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Pages {
    
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/wall")
    public String wall() {
        return "wall";
    }
}
