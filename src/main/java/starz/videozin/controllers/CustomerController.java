package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import starz.videozin.entities.Customer;
import starz.videozin.repositories.CustomerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/addcustomer")
    public String addCustomer(Model model){
        model.addAttribute("customer" ,new Customer());
        return "views/addcustomerform";
    }

    @PostMapping("/addcustomer")
    public String submitCustomer(@ModelAttribute Customer customer, Model model){
        if(customer.getSsn().equals("")||
                customer.getfName().equals("")||
                customer.getlName().equals("")||
                customer.getAddress().equals("")||
                customer.getZip().equals("")||
                customer.getCity().equals("")||
                customer.getCountry().equals("")||
                customer.getPhone().equals("")||
                customer.getMail().equals("")) {
            model.addAttribute("message", "Du måste fylla i alla fält");
        }

       else  {
            customerRepository.save(customer);
            model.addAttribute("message", customer.getfName()+" "+customer.getlName()+" har sparats!");
            model.addAttribute("customer", new Customer());
        }
        return "views/addcustomerform";
    }

    @GetMapping("/showcustomer")
    public String showCustomer(Model model){
        model.addAttribute("customer", new Customer());
        return "views/showcustomers";
    }

    @GetMapping("/showcustomer/result")
    public String showCustomer(@ModelAttribute Customer customer, Model model){
        if(customer.getSsn().equals("")&&customer.getfName().equals("")&&customer.getlName().equals("")&&customer.getPhone().equals("")){
            model.addAttribute("customerlist", customerRepository.findAll());
        }
        else if (!customer.getSsn().equals("")) {
            model.addAttribute("customerlist", customerRepository.findById(customer.getSsn())); //.get()
        }
        else if (!customer.getPhone().equals("")){
            model.addAttribute("customerlist", customerRepository.findByphone(customer.getPhone()));
        }
        else if (!customer.getfName().equals("") && !customer.getlName().equals("")) {
            List<Customer> customerList = customerRepository.findByfName(customer.getfName());

            customerList = customerList.stream()
                    .filter(streamCustomer -> streamCustomer.getlName().equals(customer.getlName()))
                    .collect(Collectors.toList());

            model.addAttribute("customerlist", customerList);
        }
        else if (!customer.getfName().equals("")) {
            model.addAttribute("customerlist", customerRepository.findByfName(customer.getfName()));
        }
        else if (!customer.getlName().equals("")) {
            model.addAttribute("customerlist", customerRepository.findBylName(customer.getlName()));
        }
        
        return "views/showcustomers";
    }

    @GetMapping("/editcustomer/{ssn}")
    public String editCustomer(@PathVariable String ssn, Model model){
        model.addAttribute("customer", customerRepository.findById(ssn).get());
        return "views/addcustomerform";
    }

    @GetMapping("/deletecustomer/{ssn}")
    public String deleteCustomer(@PathVariable String ssn, Model model){
        customerRepository.deleteById(ssn);
        model.addAttribute("customer", new Customer());
        return "views/showcustomers";
    }

}
