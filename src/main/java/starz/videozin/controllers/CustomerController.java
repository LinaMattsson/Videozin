package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import starz.videozin.entities.Customer;
import starz.videozin.repositories.CustomerRepository;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping("")
    public String home() {
        return "test";
    }

    @GetMapping("byname")
    public String byname(@RequestParam String socialSecurity, Model model) {
        model.addAttribute("customers",
                customerRepository.findById(socialSecurity));
        return "test";
    }

    @GetMapping("/customers")
    public String showCustomers(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("customerlist", customerRepository.findAll());
        return "showcustomers";
    }

    @PostMapping("/customers/result")
    public String userResult(@ModelAttribute Customer customer, Model model) {
//        model.addAttribute("customerlist", customerRepository.findByLName(customer.getlName()));
//        model.addAttribute("customerlist", customerRepository.findByFName(customer.getfName()));
//        model.addAttribute("customerlist", customerRepository.findCustomerBySocialSecurity(customer.getSocialSecurity()));
        model.addAttribute("customerlist", customerRepository.
                findByFNameOrLNameOrSocialSecurityOrPhone(
                        customer.getfName(),
                        customer.getlName(),
                        customer.getSocialSecurity(),
                        customer.getPhone()));
        if(customer.getSocialSecurity().equals("*")){
            model.addAttribute("customerlist", customerRepository.findAll());
        }

        return "showcustomers";
    }

    @GetMapping("/addcustomer")
    public String addCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "userform";
    }

    @PostMapping("/addcustomer")
    public String submitCustomer(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("customer", customer);
        model.addAttribute("message", "Du har lagt till kund:");
        model.addAttribute("addedcustomer",customer);
        model.addAttribute("display",true);
        customerRepository.save(customer);



        return "userform";
    }

}

//
//    @RequestMapping(value="/form", method=RequestMethod.POST)
//    public String customerSubmit(@ModelAttribute Customer customer, Model model) {
//
//        model.addAttribute("customer", customer);
//        String info = String.format("Customer Submission: id = %d, firstname = %s, lastname = %s",
//                customer.getId(), customer.getFirstname(), customer.getLastname());
//        log.info(info);
//        customerRepository.save(customer);
//
//        return "result";
//    }
