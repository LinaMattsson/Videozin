package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import starz.videozin.entities.Customer;
import starz.videozin.entities.Movie;
import starz.videozin.repositories.CustomerRepository;
import starz.videozin.repositories.MovieRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static starz.videozin.VideozinApplication.activecustomer;
import static starz.videozin.VideozinApplication.cart;

@Controller
public class HomeController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("cart",cart);
        model.addAttribute("movie", new Movie());
        model.addAttribute("activecustomer", activecustomer);
        return "views/index";
    }

    @GetMapping("/showmovie/result/")
    public String showMovie(@ModelAttribute Movie movie, Model model) {
        model.addAttribute("cart",cart);
        model.addAttribute("movie",new Movie());
        model.addAttribute("activecustomer",activecustomer);

        if(movie.getTitle().equals("") && movie.getMid().equals("") && movie.getCategory().equals("")){
            model.addAttribute("movielist", movieRepository.findAll());
        }
        else if(!movie.getMid().equals("")){
            model.addAttribute("movielist", movieRepository.findById(movie.getMid()).get());
        }
        else if (!movie.getCategory().equals("")) {
            model.addAttribute("movielist", movieRepository.findMovieByCategory(movie.getCategory()));
        }
        else if (!movie.getTitle().equals("")) {
            model.addAttribute("movielist", movieRepository.findMovieByTitle(movie.getTitle()));
        }

        return "views/index";
    }

    @PostMapping("/activecustomer/")
    public String setActiveCustomer(@ModelAttribute Customer customer, Model model){
        if(customerRepository.findById(customer.getSsn()).isPresent())
        activecustomer = customerRepository.getOne(customer.getSsn());
        else return "redirect:/addcustomer/";
        model.addAttribute("activecustomer",activecustomer);
        model.addAttribute("cart",cart);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @GetMapping("/activecustomer/addmovie/{mid}")
    public String addToCartLink(@PathVariable String mid, Model model){
        if(movieRepository.findById(mid).isPresent() && movieRepository.findById(mid).get().getRentdate() == null)
            cart.add(movieRepository.findById(mid).get());
        else model.addAttribute("message", "Denna filmen är redan uthyrd!");
        model.addAttribute("activecustomer",activecustomer);
        model.addAttribute("movie", new Movie());
        model.addAttribute("movietocart", new Movie());
        model.addAttribute("cart",cart);
        System.out.println(cart);
        return "views/index";
    }

    @PostMapping("/activecustomer/addmovie/")
    public String addToCartForm(@ModelAttribute Movie movie, Model model){
        if(movieRepository.findById(movie.getMid()).isPresent() && movieRepository.findById(movie.getMid()).get().getRentdate() == null)
            cart.add(movieRepository.findById(movie.getMid()).get());
        else model.addAttribute("message", "Denna filmen är redan uthyrd!");
        model.addAttribute("activecustomer",activecustomer);
        model.addAttribute("cart",cart);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }


    @GetMapping("/activecustomer/deletefromcart/{dropmoviefromcart}")
    public String deleteFromCart(@PathVariable String dropmoviefromcart, Model model){
        cart = cart.stream().
                filter(movie -> !movie.getMid().equals(dropmoviefromcart)).
                collect(Collectors.toList());

        model.addAttribute("cart",cart);
        model.addAttribute("activecustomer",activecustomer);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @PostMapping("/activecustomer/rent/")
    public String rentMovies(Model model){
        Customer customer = customerRepository.getOne(activecustomer.getSsn());
        for(Movie m:cart){
            m.setCustomer(customer);
            m.setRentdate(Date.valueOf(LocalDate.now()));
        }
        customerRepository.save(customerRepository.getOne(customer.getSsn()));
        movieRepository.saveAll(cart);
        cart.clear();
        activecustomer = new Customer();
        model.addAttribute("cart",cart);
        model.addAttribute("activecustomer",activecustomer);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @GetMapping("/dispatchcustomer/")
    public String dispatchCustomer(Model model){
        cart.clear();
        activecustomer = new Customer();
        model.addAttribute("cart",cart);
        model.addAttribute("activecustomer",activecustomer);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @GetMapping("/deletemovie/{mid}")
    public String deleteMovie(@PathVariable String mid, Model model){
        movieRepository.deleteById(mid);
        model.addAttribute("cart",cart);
        model.addAttribute("activecustomer",activecustomer);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }
    @GetMapping("showmovie/rented")
    public String rentedMovies(Model model) {
        model.addAttribute("movielist", movieRepository.findMovieByRented());
        model.addAttribute("cart",cart);
        model.addAttribute("activecustomer",activecustomer);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }
}
