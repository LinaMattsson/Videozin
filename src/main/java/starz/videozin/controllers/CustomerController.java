package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import starz.videozin.entities.Customer;
import starz.videozin.repositories.CustomerRepository;

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
            model.addAttribute("message", customer.getfName()+" "+customer.getlName()+" har lagts till!");
            model.addAttribute("customer", new Customer());
        }

        return "views/addcustomerform";
    }

}
