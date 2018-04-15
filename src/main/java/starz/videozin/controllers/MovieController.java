package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "showmovies";
    }

    @GetMapping("/movies/result")
    public String findMovies(@ModelAttribute Movie movie, Model model) {
        model.addAttribute("movie", movie);
        model.addAttribute("movielist", movieRepository.findByNameOrCategoryOrPid(movie.getName(),movie.getCategory(),movie.getPid()));
        model.addAttribute("displaymovieresultdiv", true);
        model.addAttribute("displaymovie", new Movie());

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
        return "showmovies";
    }
}
