package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import starz.videozin.entities.Movie;
import starz.videozin.repositories.CustomerRepository;
import starz.videozin.repositories.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    MovieRepository movieRepository;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movie", new Movie());
        return "views/index";
    }

    @GetMapping("/showmovie/result/")
    public String showMovie(@ModelAttribute Movie movie, Model model) {
        model.addAttribute("movie", new Movie());

        if (movie.getTitle().equals("") && movie.getMid().equals("")&& movie.getCategory().equals("")/*&& movie.getReleasedate().equals("")*/ && movie.getCategory().equals("")) {
            model.addAttribute("movielist", movieRepository.findAll());
        } else if (!movie.getMid().equals("")) {
            model.addAttribute("movielist", movieRepository.findById(movie.getMid()).get());
        } else if (!movie.getTitle().equals("")&& movie.getCategory().equals("")){
            List<Movie> movieList = movieRepository.findMovieByTitle(movie.getTitle());
            movieList = movieList.stream()
                    .filter(streamMovie -> streamMovie.getCategory().equals(movie.getCategory()))
                    .collect(Collectors.toList());

            model.addAttribute("movielist", movieList);
        }
        else if (!movie.getTitle().equals("")) {
            model.addAttribute("movielist", movieRepository.findMovieByTitle(movie.getTitle()));
        } else if (!movie.getCategory().equals("")) {
            model.addAttribute("movielist", movieRepository.findMovieByCategory(movie.getCategory()));
        }

        return "views/index";
    }


}
