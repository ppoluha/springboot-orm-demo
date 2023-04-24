package se.hkr.java.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.hkr.java.db.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
