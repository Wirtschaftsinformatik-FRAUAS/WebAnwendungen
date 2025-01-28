package edu.fra.uas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import edu.fra.uas.model.ApiError;
import edu.fra.uas.model.Order;

import org.slf4j.Logger;

@Service
public class OrderService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(OrderService.class);

    @Value("${orderservice.url}")
    String apiUrl;

    // GET /orders
    public ResponseEntity<?> getAllOrders() {
        log.debug("forward request to " + apiUrl + "/orders");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/orders";
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    // GET /orders/{id}
    public ResponseEntity<?> getOrderById(Long id) {
        log.debug("forward request to " + apiUrl + "/orders/" + id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/orders/" + id;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    // POST /users/{userid}/orders
    public ResponseEntity<?> createOrder(Long userId, Order order) {
        log.debug("forward request to " + apiUrl + "/users/"+userId+"/orders");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/"+userId+"/orders";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Order> request = new HttpEntity<>(order, headers);

        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    // DELETE /users/{userid}/orders
    public ResponseEntity<?> deleteOrder(Long userid,Long id) {
        log.debug("forward request to " + apiUrl + "/users/"+userid+"/orders"+id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/"+userid+"/orders"+id;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    // PUT /users/{userid}/orders
    public ResponseEntity<?> updateOrder(Long id, Order order) {
        log.debug("forward request to " + apiUrl + "/orders/" + id);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/orders/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Order> request = new HttpEntity<>(order, headers);

        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    // GET /users/{userid}/orders
public ResponseEntity<?> getOrdersByUserId(Long userId) {
    log.debug("forward request to " + apiUrl + "/users/" + userId + "/orders");
    RestTemplate restTemplate = new RestTemplate();
    String url = apiUrl + "/users/" + userId + "/orders"; // URL zur API anpassen
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<String> request = new HttpEntity<>(headers);

    try {
        ResponseEntity<Order[]> response = restTemplate.exchange(url, HttpMethod.GET, request, Order[].class);
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    } catch (HttpClientErrorException e) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

}
