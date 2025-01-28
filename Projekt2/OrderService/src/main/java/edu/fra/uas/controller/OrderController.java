package edu.fra.uas.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.fra.uas.model.Order;
import edu.fra.uas.service.OrderService;

@RestController
public class OrderController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    //check
    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Order>> list() {
        log.debug("list() is called");
        Iterable<Order> orderIter = orderService.getAllOrders();
        List<Order> orders = new ArrayList<>();
        for (Order order : orderIter) {
            orders.add(order);
        }
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    //check
    @GetMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> find(@PathVariable("id") Long orderId) {
        log.debug("find() is called");
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    
    //check
    @PostMapping(value = "/users/{userid}/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> add(@PathVariable("userid") Long userid, @RequestBody Order order) {
        log.debug("add() is called");
        order = orderService.createOrder(userid, order.getProductTitle());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/orders/" + order.getId()));
        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }

    //check
    @PutMapping(value = "/users/{userid}/orders/{orderid}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> update(@RequestBody Order newOrder, @PathVariable("orderid") Long orderId, @PathVariable("userid") Long userId) {
        log.debug("update() is called");
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        order.setProductTitle(newOrder.getProductTitle());
        order.setStatus(newOrder.getStatus());
        order = orderService.updateOrder(userId, order);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/orders/" + order.getId()));
        return new ResponseEntity<>(order, headers, HttpStatus.OK);
    }

    //check
    @DeleteMapping(value = "/users/{userid}/orders/{orderid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("orderid") Long orderId ,@PathVariable("userid") Long userId) {
        log.debug("delete() is called");
        Order order = orderService.deleteOrder(userId, orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @GetMapping(value = "/users/{userid}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable("userid") Long userId) {
    log.debug("getOrdersByUserId() is called for userId: " + userId);
    Iterable<Order> orderIter = orderService.getOrdersByUserId(userId);
    List<Order> orders = new ArrayList<>();
    for (Order order : orderIter) {
        orders.add(order);
    }
    if (orders.isEmpty()) {
        return ResponseEntity.noContent().build();
    }
    return new ResponseEntity<>(orders, HttpStatus.OK);
}

}
