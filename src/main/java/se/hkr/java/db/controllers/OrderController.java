package se.hkr.java.db.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.hkr.java.db.entities.*;
import se.hkr.java.db.repositories.CustomerRepository;
import se.hkr.java.db.repositories.EmployeeRepository;
import se.hkr.java.db.repositories.FurnitureRepository;
import se.hkr.java.db.repositories.OrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FurnitureRepository furnitureRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/new")
    public String showOrderForm(Model model, @RequestParam("employeeId") Long employeeId) {
        model.addAttribute("employeeId", employeeId);
        model.addAttribute("furnitures", furnitureRepository.findAll());
        model.addAttribute("customers", customerRepository.findAll());
        return "order-new";
    }

    @PostMapping()
    public String createOrder(@RequestParam Long employeeId,
                              @RequestParam LocalDate orderDate,
                              @RequestParam Long customerId,
                              @RequestParam(name = "furniture") List<Long> furnitureIds,
                              @RequestParam(name = "quantity") List<Integer> quantities) {

        // find employee by id
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));

        // find customer by id
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        // create order head
        OrderHead orderHead = new OrderHead();
        orderHead.setEmployee(employee);
        orderHead.setCustomer(customer);
        orderHead.setOrderDate(orderDate);

        // create order lines
        List<OrderLine> orderLines = new ArrayList<>();
        for (int i = 0; i < furnitureIds.size(); i++) {
            Long furnitureId = furnitureIds.get(i);
            if (furnitureId == null) continue;
            Furniture furniture = furnitureRepository.findById(furnitureId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid furniture ID"));
            OrderLine orderLine = new OrderLine();
            orderLine.setFurniture(furniture);
            orderLine.setQuantity(quantities.get(i));
            orderLine.setOrder(orderHead);
            orderLines.add(orderLine);
        }

        // save order
        orderHead.setOrderLines(orderLines);
        orderRepository.save(orderHead);

        return "redirect:/employees";
    }
}
