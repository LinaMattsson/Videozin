package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import starz.videozin.entities.Customer;
import starz.videozin.entities.Movie;
import starz.videozin.handlers.PagingHandler;
import starz.videozin.repositories.CustomerRepository;
import starz.videozin.repositories.MovieRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static starz.videozin.VideozinApplication.activecustomer;
import static starz.videozin.VideozinApplication.cart;

@Controller
public class HomeController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/start")
    public String home(Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("movie", new Movie());
        model.addAttribute("activecustomer", activecustomer);
        return "views/index";
    }

    @GetMapping("/showmovie/result/{currentpage}")
    public String showMovie(@PathVariable int currentpage, @ModelAttribute Movie movie, Model model) {
        int pageSize = 5;
        List<Movie> movielist = new ArrayList<>();

        model.addAttribute("cart", cart);
        model.addAttribute("movie", movie);
        model.addAttribute("activecustomer", activecustomer);

        if (movie.getTitle().equals("") && movie.getMid().equals("") && movie.getCategory().equals("")) {
            movielist = movieRepository.findAll();
        } else if (!movie.getMid().equals("")) {
            movielist.add(movieRepository.getOne(movie.getMid()));
        } else if (!movie.getCategory().equals("")) {
            movielist = movieRepository.findMovieByCategory(movie.getCategory());
        } else if (!movie.getTitle().equals("")) {
            movielist = movieRepository.findMovieByTitle(movie.getTitle());
        }

        model.addAttribute("pages", PagingHandler.getPageList(movielist, pageSize));

        movielist = PagingHandler.getPagedMovieList(movielist,currentpage,pageSize);

        model.addAttribute("movielist", movielist);
        model.addAttribute("currentpage", currentpage);
        model.addAttribute("paging", "result");
        return "views/index";
    }

    @PostMapping("/activecustomer/")
    public String setActiveCustomer(@ModelAttribute Customer customer, Model model) {
        if (customerRepository.findById(customer.getSsn()).isPresent())
            activecustomer = customerRepository.getOne(customer.getSsn());
        else return "redirect:/addcustomer/";
        model.addAttribute("activecustomer", activecustomer);
        model.addAttribute("cart", cart);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @GetMapping("/activecustomer/addmovie/{mid}")
    public String addToCartLink(@PathVariable String mid, Model model) {
        boolean duplicate = false;
        for (Movie m : cart) {
            if (m.getMid().equals(mid))
                duplicate = true;
        }
        if (!duplicate && movieRepository.findById(mid).isPresent() && movieRepository.findById(mid).get().getRentdate() == null)
            cart.add(movieRepository.findById(mid).get());
        else model.addAttribute("message", "Denna filmen är redan uthyrd!");
        model.addAttribute("activecustomer", activecustomer);
        model.addAttribute("movie", new Movie());
        model.addAttribute("movietocart", new Movie());
        model.addAttribute("cart", cart);
        System.out.println(cart);
        return "views/index";
    }

    @PostMapping("/activecustomer/addmovie/")
    public String addToCartForm(@ModelAttribute Movie movie, Model model) {
        boolean duplicate = false;
        for (Movie m : cart) {
            if (m.getMid().equals(movie.getMid()))
                duplicate = true;
        }
        if (!duplicate && movieRepository.findById(movie.getMid()).isPresent() && movieRepository.findById(movie.getMid()).get().getRentdate() == null)
            cart.add(movieRepository.findById(movie.getMid()).get());
        else if (!movieRepository.findById(movie.getMid()).isPresent())
            model.addAttribute("message", "Detta FilmID finns inte");
        else model.addAttribute("message", "Denna filmen är redan uthyrd!");
        model.addAttribute("activecustomer", activecustomer);
        model.addAttribute("cart", cart);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @GetMapping("/activecustomer/deletefromcart/{dropmoviefromcart}")
    public String deleteFromCart(@PathVariable String dropmoviefromcart, Model model) {
        cart = cart.stream().
                filter(movie -> !movie.getMid().equals(dropmoviefromcart)).
                collect(Collectors.toList());

        model.addAttribute("cart", cart);
        model.addAttribute("activecustomer", activecustomer);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @PostMapping("/activecustomer/rent/")
    public String rentMovies(Model model) {
        Customer customer = customerRepository.getOne(activecustomer.getSsn());
        for (Movie m : cart) {
            m.setCustomer(customer);
            m.setRentdate(Date.valueOf(LocalDate.now()));
        }
        customerRepository.save(customerRepository.getOne(customer.getSsn()));
        movieRepository.saveAll(cart);
        cart.clear();
        activecustomer = new Customer();
        model.addAttribute("cart", cart);
        model.addAttribute("activecustomer", activecustomer);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @GetMapping("/dispatchcustomer/")
    public String dispatchCustomer(Model model) {
        cart.clear();
        activecustomer = new Customer();
        model.addAttribute("cart", cart);
        model.addAttribute("activecustomer", activecustomer);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @GetMapping("/deletemovie/{mid}")
    public String deleteMovie(@PathVariable String mid, Model model) {
        movieRepository.deleteById(mid);
        model.addAttribute("cart", cart);
        model.addAttribute("activecustomer", activecustomer);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @GetMapping("showmovie/rented/{currentpage}/")
    public String rentedMovies(@PathVariable int currentpage, Model model) {
        int pageSize = 5;
        List<Movie> movielist = movieRepository.findMovieByRented();

        model.addAttribute("pages", PagingHandler.getPageList(movielist, pageSize));

        movielist = PagingHandler.getPagedMovieList(movielist,currentpage,pageSize);

        model.addAttribute("currentpage", currentpage);

        model.addAttribute("movielist", movielist);
        model.addAttribute("cart", cart);
        model.addAttribute("activecustomer", activecustomer);
        model.addAttribute("movie", new Movie());
        model.addAttribute("paging", "rented");

        return "views/index";
    }
    @GetMapping("showmovie/late/{currentpage}")
    public String lateMovies(@PathVariable int currentpage, Model model){
        List<Movie> lateList = movieRepository.findMovieByRented();
        int pageSize = 5;

        lateList = lateList.stream()
                .filter(m -> m.getRentdate().before(Date.valueOf(LocalDate.now().minusDays(1))))
                .collect(Collectors.toList());

        model.addAttribute("pages", PagingHandler.getPageList(lateList, pageSize));

        model.addAttribute("currentpage", currentpage);
        lateList = PagingHandler.getPagedMovieList(lateList,currentpage,pageSize);
        model.addAttribute("movielist", lateList);
        model.addAttribute("cart",cart);
        model.addAttribute("activecustomer",activecustomer);
        model.addAttribute("movie", new Movie());

       return "views/index";
    }

    @PostMapping("activecustomer/returnmovie/{mid}")
    public String returnMovie(@PathVariable String mid, Model model) {
        movieRepository.getOne(mid).setRentdate(null);
        movieRepository.getOne(mid).setCustomer(null);
        movieRepository.save(movieRepository.getOne(mid));
        activecustomer = customerRepository.getOne(activecustomer.getSsn());
        model.addAttribute("cart", cart);
        model.addAttribute("movie", new Movie());
        model.addAttribute("activecustomer", activecustomer);
        return "views/index";
    }

    @PostMapping("activecustomer/returnallmovies/")
    public String returnMovie(Model model) {
        List<Movie> movielist = activecustomer.getRentedMovies();
        for (Movie m : movielist) {
            movieRepository.getOne(m.getMid()).setRentdate(null);
            movieRepository.getOne(m.getMid()).setCustomer(null);
        }
        movielist.clear();
        movieRepository.saveAll(movielist);
        activecustomer = customerRepository.getOne(activecustomer.getSsn());
        model.addAttribute("cart", cart);
        model.addAttribute("movie", new Movie());
        model.addAttribute("activecustomer", activecustomer);
        return "views/index";
    }

    @GetMapping("/showrentcustomer/{ssn}")
    public String showCustomer(@PathVariable String ssn, Model model) {
        model.addAttribute("customerlist", customerRepository.getOne(ssn));
        model.addAttribute("customer", customerRepository.getOne(ssn));
        return "views/showcustomers";
    }
}
