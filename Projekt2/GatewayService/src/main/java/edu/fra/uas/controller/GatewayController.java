package edu.fra.uas.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.fra.uas.model.Order;
import edu.fra.uas.model.Payment;
import edu.fra.uas.model.User;
import edu.fra.uas.service.OrderService;
import edu.fra.uas.service.PaymentService;
import edu.fra.uas.service.UserService;

@RestController
@RequestMapping("/api")

public class GatewayController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(GatewayController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

      @Autowired
    private PaymentService paymentService; 

    // User CRUD Operations

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> listUsers() {
        log.debug("listUsers() is called");
        ResponseEntity<?> response = userService.getAllUsers();
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NO_CONTENT)) {
            return new ResponseEntity<>("no users", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> findUser(@PathVariable("userId") Long userId) {
        log.debug("findUser() is called");
        ResponseEntity<?> response = userService.getUserById(userId);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody User user) {
        log.info("Create new user: ", user.getEmail());
        ResponseEntity<?> response = userService.createUser(user);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/users"));
            return new ResponseEntity<>(response.getBody(), headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response.getBody(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        log.info("deleteUser() is called");
        ResponseEntity<?> response = userService.deleteUser(userId);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @PutMapping(value = "/users/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @RequestBody User user) {
        log.info("updateUser() is called: ");
        ResponseEntity<?> response = userService.updateUser(userId, user);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    // ##############################################################################################################
    // Order CRUD Operations
    // ##############################################################################################################

    @GetMapping(value = "/users/{userId}/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> findOrder(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) {
        log.debug("findOrder() is called for userId: {}, orderId: {}", userId, orderId);
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        ResponseEntity<?> orderResponse = orderService.getOrderById(orderId);
        if (orderResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return orderResponse;
        }
        return new ResponseEntity<>(orderResponse.getBody(), HttpStatus.OK);
    }

    @PostMapping(value = "/users/{userid}/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createOrder(@PathVariable("userid") Long userid, @RequestBody Order order) {
        log.debug("createOrder() is called for userId: {}", userid);
        ResponseEntity<?> userResponse = userService.getUserById(userid);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        ResponseEntity<?> orderResponse = orderService.createOrder(userid, order);
        if (orderResponse.getStatusCode().isSameCodeAs(HttpStatus.CREATED)) {
            HttpHeaders headers = new HttpHeaders();
            // headers.setLocation(URI.create("/api/users/" + userid + "/orders/" + ((Order)
            // orderResponse.getBody()).getId())); //Ich hab keine Ahnung warum das nicht
            // funktioniert :(, pls halp
            return new ResponseEntity<>(orderResponse.getBody(), headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(orderResponse.getBody(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping(value = "/users/{userId}/orders/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateOrder(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId,
            @RequestBody Order order) {
        log.debug("updateOrder() is called for userId: {}, orderId: {}", userId, orderId);
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        ResponseEntity<?> orderResponse = orderService.updateOrder(orderId, order);
        if (orderResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return orderResponse;
        }
        return new ResponseEntity<>(orderResponse.getBody(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{userId}/orders/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteOrder(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) {
        log.debug("deleteOrder() is called for userId: {}, orderId: {}", userId, orderId);
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        ResponseEntity<?> orderResponse = orderService.deleteOrder(userId, orderId);
        if (orderResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return orderResponse;
        }
        return new ResponseEntity<>(orderResponse.getBody(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userid}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getOrdersByUserId(@PathVariable("userid") Long userid) {
        log.debug("getOrdersByUserId() is called for user id: " + userid);
        ResponseEntity<?> response = orderService.getOrdersByUserId(userid);
        if (response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    // ##############################################################################################################
    // Payment Operations
    // ##############################################################################################################

    @PostMapping(value = "/users/{userId}/orders/{orderId}/payments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createPayment(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId, @RequestBody Payment payment) {
        log.debug("createPayment() is called for userId: {}, orderId: {}", userId, orderId);
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        ResponseEntity<?> orderResponse = orderService.getOrderById(orderId);
        if (orderResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return orderResponse;
        }
        // Erstellung der Zahlung über den PaymentService
        Payment createdPayment = paymentService.addPayment(orderId, userId, payment.getAmount());
        if (createdPayment != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/api/users/" + userId + "/orders/" + orderId + "/payments/" + createdPayment.getId()));
            return new ResponseEntity<>(createdPayment, headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create payment", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    @GetMapping(value = "/users/{userId}/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> showMyPayments(@PathVariable("userId") Long userId) {
        log.debug("showMyPayments() is called for userId: {}", userId);
        
        List<Payment> payments = paymentService.showMyPayments(userId);
        if (payments != null && !payments.isEmpty()) {
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No payments found for user.", HttpStatus.NO_CONTENT);
        }
    }

}
