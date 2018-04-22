package starz.videozin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/")
    public String getLogin() {
        return "views/login";
    }

}
