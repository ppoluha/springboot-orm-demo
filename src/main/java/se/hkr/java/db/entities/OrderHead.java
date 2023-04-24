package se.hkr.java.db.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderHead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderDate;
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderLine> orderLines = new ArrayList<>();
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Customer customer;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate bought) {
        this.orderDate = bought;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee staff) {
        this.employee = staff;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
