package starz.videozin.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class CustomerController {
    @GetMapping("/hi")
    public String sayhi(){
        return "hi";
    }

    @GetMapping("")
    public String start(){
        return "hellotest";
    }
    @PostMapping("")
    public String start1(){
        return "hellotest";
    }
}
