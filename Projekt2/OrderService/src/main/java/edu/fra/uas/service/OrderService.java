package edu.fra.uas.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.model.Order;
import edu.fra.uas.repository.OrderRepository;

/**
 * This class represents the service for the orders.
 */
@Service
public class OrderService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    private long nextOrderId = 1;

    public Order createOrder(Long userId, String productTitle) {
        Order order = new Order(nextOrderId++, userId, productTitle, "Processing");
        log.debug("createOrder: " + order);
        orderRepository.put(order.getId(), order);
        return orderRepository.get(order.getId());
    }

    public Iterable<Order> getAllOrders() {
        log.debug("getAllOrders");
        return orderRepository.values();
    }

    public Order getOrderById(long id) {
        log.debug("getOrder: " + id);
        return orderRepository.get(id);
    }

    public Order updateOrder(Long userId, Order order) {
        log.debug("updateOrder: " + order);
        orderRepository.put(order.getId(), order);
        return orderRepository.get(order.getId());
    }

    public Order deleteOrder(Long userId, long id) {
        log.debug("deleteOrder: " + id);
        return orderRepository.remove(id);
    }

    public Iterable<Order> getOrdersByUserId2(Long userId) {
        log.debug("getOrdersByUserId: " + userId);
        return orderRepository.values().stream()
                .filter(order -> order.getUserId().equals(userId))
                .toList(); // Alle Bestellungen des Benutzers filtern
    }

    public Iterable<Order> getOrdersByUserId(Long userId) {
        log.debug("getOrdersByUserId: " + userId);
        List<Order> orders = new ArrayList<>();
        for (Order order : orderRepository.values()) {
            if (order.getUserId().equals(userId)) {
                orders.add(order);
            }
        }
        return orders;

    }

}
