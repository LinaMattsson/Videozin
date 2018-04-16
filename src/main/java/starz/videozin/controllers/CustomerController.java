package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import starz.videozin.entities.Customer;
import starz.videozin.repositories.CustomerRepository;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping("")
    public String home() {
        return "index";
    }

    @GetMapping("byname")
    public String byname(@RequestParam String socialSecurity, Model model) {
        model.addAttribute("customers", customerRepository.findById(socialSecurity));
        return "index";
    }

    @GetMapping("/customers")
    public String findCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("customerinfo", new Customer());
        return "showcustomers";
    }


    @PostMapping("/customers/result")
    public String userResult(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("displaycustomerresultdiv", true);
        model.addAttribute("customerinfo", new Customer());

        model.addAttribute("customerlist", customerRepository.
                findByFNameOrLNameOrSocialSecurityOrPhone(
                        customer.getfName(),
                        customer.getlName(),
                        customer.getSocialSecurity(),
                        customer.getPhone()));
        if (customer.getfName().equals("") &&
                customer.getlName().equals("") &&
                customer.getSocialSecurity().equals("") &&
                customer.getPhone().equals("")) {
            model.addAttribute("customerlist", customerRepository.findAll());
        }

        return "showcustomers";
    }


    @GetMapping("/customer/edit/byid/{socialSecurity}")
    public String editCustomer(@PathVariable String socialSecurity, Model model) {
        model.addAttribute("customer", customerRepository.findBySocialSecurity(socialSecurity));
        return "userform";
    }

    @GetMapping("/customer/delete/byid/{socialSecurity}")
    public String deleteCustomer(@PathVariable String socialSecurity, Model model) {
        String name = customerRepository.findBySocialSecurity(socialSecurity).getfName() + " " + customerRepository.findBySocialSecurity(socialSecurity).getlName();
        customerRepository.deleteById(socialSecurity);
        model.addAttribute("message", "Du har raderat " + name);
        model.addAttribute("customer", new Customer());
        model.addAttribute("customerinfo", new Customer());
        return "showcustomers";
    }

    @GetMapping("/customers/byid/{socialSecurity}")
    public String showCustomer(@PathVariable String socialSecurity, Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("customerinfo", customerRepository.findBySocialSecurity(socialSecurity));
        model.addAttribute("displaycustomerinfodiv", true);
        return "showcustomers";
    }


    @GetMapping("/addcustomer")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "userform";
    }

    @PostMapping("/addcustomer")
    public String submitCustomer(@ModelAttribute Customer customer, Model model) {
        if (customer.getfName().equals("") ||
                customer.getlName().equals("") ||
                customer.getSocialSecurity().equals("") ||
                customer.getPhone().equals("") ||
                customer.getAdress().equals("") ||
                customer.getCity().equals("") ||
                customer.getCountry().equals("") ||
                customer.getMail().equals("")) {
            model.addAttribute("message", "Du måste fylla i alla fält");
            return "userform";
        }

        model.addAttribute("customer", customer);
        model.addAttribute("addedcustomer", customer);
        model.addAttribute("displayaddeddiv", true);
        customerRepository.save(customer);
        model.addAttribute("message", "Du har lagt till kund:");
        return "userform";
    }

}