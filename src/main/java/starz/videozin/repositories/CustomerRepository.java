package starz.videozin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import starz.videozin.entities.Customer;


public interface CustomerRepository extends JpaRepository<Customer, String> {
}
