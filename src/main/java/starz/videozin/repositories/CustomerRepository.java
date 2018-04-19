package starz.videozin.repositories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import starz.videozin.entities.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    List<Customer> findByfName(String name);
    List<Customer> findBylName(String name);
    List<Customer> findByphone(String name);
    List<Customer> findByaddress(String name);
}


