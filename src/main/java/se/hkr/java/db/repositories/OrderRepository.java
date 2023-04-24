package se.hkr.java.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.hkr.java.db.entities.OrderHead;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderHead, Long> {
    List<OrderHead> findByEmployeeIdOrderByOrderDateDesc(Long id);
}
