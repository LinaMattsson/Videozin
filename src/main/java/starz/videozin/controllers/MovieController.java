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
import starz.videozin.repositories.MovieRepository;

@Controller
public class MovieController {

    @Autowired
    MovieRepository movieRepository;


    @GetMapping("/movies")
    public String showMovies(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("displaymovie", new Movie());
        model.addAttribute("customer",new Customer());
        return "showmovies";
    }

    @GetMapping("/movies/result")
    public String findMovies(Movie movie, Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("movielist", movieRepository.findByNameOrCategoryOrPid(movie.getName(),movie.getCategory(),movie.getPid()));
        model.addAttribute("displaymovieresultdiv", true);
        model.addAttribute("displaymovie", new Movie());
        model.addAttribute("customer",new Customer());

        if(movie.getCategory().equals("")&&movie.getName().equals("")&&movie.getPid().equals("")){
            model.addAttribute("movielist", movieRepository.findAll());
        }
        return "showmovies";
    }

    @GetMapping("/movie/byId/{pid}")
    public String movieById(@PathVariable String pid, Model model){
        model.addAttribute("movie", new Movie());
        model.addAttribute("displaymovie", movieRepository.findByPid(pid));
        model.addAttribute("displaymovieinfo",true);
        model.addAttribute("customer",new Customer());
        return "showmovies";
    }

    @GetMapping("/movies/rented")
    public String showRented(@ModelAttribute Customer customer, Model model){
        model.addAttribute("customer",customer);
        if(customer.getSocialSecurity()==null){
            model.addAttribute("movielist", movieRepository.findAllMoviesRented());
        }
        else{
            model.addAttribute("movielist", movieRepository.findMoviesRentedByCustomer(customer.getSocialSecurity()));
        }
        model.addAttribute("movie", new Movie());
        model.addAttribute("displaymovie",new Movie());
        model.addAttribute("displaymovieresultdiv",true);
        return "showmovies";
    }
}
