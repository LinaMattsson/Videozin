package starz.videozin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import starz.videozin.entities.Rented;

import java.util.List;


public interface RentedRepository extends JpaRepository<Rented, String> {
    List<Rented> findByPid(String pid);
    List<Rented> findBySsn(String ssn);



}
