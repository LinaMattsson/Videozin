package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import starz.videozin.entities.Customer;
import starz.videozin.entities.Movie;
import starz.videozin.repositories.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/addmovie")
    public String addMovie(Model model) {
        model.addAttribute("movie", new Movie());
        return "views/addmovieform";
    }

    @PostMapping("/addmovie")
    public String submitmovie(@ModelAttribute Movie movie, Model model) {
        if (movie.getMid().equals("") ||
                movie.getTitle().equals("") ||
                movie.getCategory().equals("") ||
                movie.getFormat().equals("") ||
                movie.getDescription().equals("") ||
                movie.getReleasedate().equals("") ||
                movie.getShelf().equals("") ||
                movie.getPrice() == 0) {
            model.addAttribute("message", "Du måste fylla i alla fält");
            model.addAttribute("movie", movie);
        } else {
            // movie.setmovie(movieRepository.findById("0").get());
            movieRepository.save(movie);
            model.addAttribute("message", movie.getTitle() + " är tillagd!");
            model.addAttribute("movie", new Movie());
        }
        return "views/addmovieform";
    }
    @GetMapping("/editmovie/{mid}")
    public String editMovie(@PathVariable String mid, Model model){
        model.addAttribute("movie", movieRepository.findById(mid).get());
        return "views/addmovieform";
    }

    @GetMapping("/deletemovie/{mid}")
    public String deleteMovie(@PathVariable String mid, Model model){
        movieRepository.deleteById(mid);
        model.addAttribute("movie", new Movie());
        return "views/index";
    }
     

}
