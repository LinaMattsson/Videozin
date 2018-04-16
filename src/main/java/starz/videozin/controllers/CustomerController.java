package starz.videozin.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import starz.videozin.entities.Customer;
import starz.videozin.repositories.CustomerRepository;

import java.util.Arrays;
import java.util.List;


@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/user")
    public String addUser(Model model){
        model.addAttribute("customer", new Customer());
        return "userpage";
    }

    @PostMapping("/user")
    public String seNewUser(@ModelAttribute Customer customer){

       return "testtest";
    }
    @GetMapping("")
    public String start(){
        return "testtest";
    }
    @GetMapping("/User")
    public String start1(){
        return "user page";
    }
//    @GetMapping("/addUser")
//    public String addUser(Model model,){
//        return "user";
//    }
//    @GetMapping("/Users")
//    public String getCustomer(Model model){
//        model.addAttribute()
//        return "user"
//    }
}
