package starz.videozin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import starz.videozin.entities.Movie;

import java.util.Date;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String>{ //Linas: typen ska vara samma som primary key

    List<Movie> findMovieByTitle(String title);
    List<Movie> findMovieByCategory(String category);
    List<Movie> findMovieByReleasedate(Date releasedate);

}
