package guru.springfamework.repositories;

import guru.springfamework.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReposity extends JpaRepository<Customer, Long> {
    Customer findByFirstName(String firstName);
}
