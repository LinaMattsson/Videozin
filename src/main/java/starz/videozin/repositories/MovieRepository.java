package starz.videozin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import starz.videozin.entities.Movie;

import java.util.Date;
import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, String>{ //Linas: typen ska vara samma som primary key

    List<Movie> findMovieByTitle(String title);
    List<Movie> findMovieByCategory(String category);


    @Query("select m from Movie m where year(releasedate) = :releasedate")
    List<Movie> findMovieByReleasedate(@Param("releasedate") int releasedate);


    @Query("select m from Movie m where rentdate is not null")
    List<Movie> findMovieByRented();
    List<Movie> findMovieByRentdate(Date date);

}
