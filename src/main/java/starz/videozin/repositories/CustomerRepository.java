package starz.videozin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import starz.videozin.entities.Customer;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findByLName(String lName);
    List<Customer> findByFName(String fName);
    List<Customer> findCustomerBySocialSecurity(String socialSecurity);
    List<Customer> findByFNameOrLNameOrSocialSecurityOrPhone(String fName,String lName,String socialSecurity,String phone);
    Customer findBySocialSecurity(String socialSecurity);

}
