package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import starz.videozin.repositories.CustomerRepository;
import starz.videozin.repositories.MovieRepository;

@Controller
public class HomeController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    MovieRepository movieRepository;


    @GetMapping("/")
    public String home(){
        return "views/index";
    }


}
