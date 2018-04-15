package starz.videozin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import starz.videozin.entities.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findByName(String name);
    List<Movie> findByCategory(String category);
    List<Movie> findByNameOrCategoryOrPid(String name, String category, String pid);
    Movie findByPid(String pid);

    @Query("select m from Movie m where rented = 'true'")
    List<Movie> findAllMoviesRented();

    @Query("select m " +
            "from Movie m " +
            "where m.pid = (select pid " +
            "from starz.videozin.entities.Rented " +
            "where ssn=?1)")
    List<Movie> findMoviesRentedByCustomer(String ssn);



//    @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
//    User findByLastnameOrFirstname(@Param("lastname") String lastname,
//                                   @Param("firstname") String firstname);


}
