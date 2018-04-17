package starz.videozin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import starz.videozin.entities.Movie;
import starz.videozin.repositories.CustomerRepository;
import starz.videozin.repositories.MovieRepository;

@Controller
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @GetMapping("/addmovie")
    public String addMovie(Model model){
        model.addAttribute("movie" ,new Movie());
        return "views/addmovieform";
    }

    @PostMapping("/addmovie")
    public String submitmovie(@ModelAttribute Movie movie, Model model){
        if(movie.getMid().equals("")||
                movie.getTitle().equals("")||
                movie.getCategory().equals("")||
                movie.getFormat().equals("")||
                movie.getDescription().equals("")||
                movie.getReleasedate().equals("")||
                movie.getShelf().equals("")||
                movie.getPrice()==0){
            model.addAttribute("message", "Du måste fylla i alla fält");
            model.addAttribute("movie", movie);

        }
       else {
           // movie.setCustomer(customerRepository.findById("0").get());
            movieRepository.save(movie);
            model.addAttribute("message", movie.getTitle()+" är tillagd!");

            model.addAttribute("movie" ,new Movie());

        }
        return "views/addmovieform";

    }


}
