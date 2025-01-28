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
import edu.fra.uas.model.Todo;

import org.slf4j.Logger;

@Service
public class TodoService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TodoService.class);

    @Value("${todoservice.url}")

    String apiUrl;

    public ResponseEntity<?> getAllTodosByUserId(long userId) {
        log.debug("forward request to " + apiUrl + "/users/"+userId+"/todos");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/"+userId+"/todos";
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

    public ResponseEntity<?> getTodoByUserId(long userId, long todoId) {
        log.debug("forward request to " + apiUrl + "/users/"+userId+"/todos/"+todoId);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/{userId}/todos";
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

    public ResponseEntity<?> createTodoByUserId(Long userId, String description) {
        log.debug("forward request to " + apiUrl + "/users/"+userId+"/todos");
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/"+userId+"/todos";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(description, headers);

        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    public ResponseEntity<?> updateTodo(Long userId, Long todoId, Todo todo) {
        log.debug("forward request to " + apiUrl + "/users/"+userId+"/todos/"+todoId);
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + userId + "/todos/" + todoId;
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Todo> request = new HttpEntity<>(todo,headers);

        ResponseEntity<?> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        } catch (HttpClientErrorException e) {
            ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getResponseBodyAsString());
            response = new ResponseEntity<>(apiError, apiError.getStatus());
        }
        return response;
    }

    public ResponseEntity<?> deleteTodoByUserId(Long userId, Long todoId) {

        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "/users/" + userId + "/todos/" + todoId;
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

}
