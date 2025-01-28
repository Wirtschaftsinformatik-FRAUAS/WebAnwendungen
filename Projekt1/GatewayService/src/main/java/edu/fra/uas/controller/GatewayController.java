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

import edu.fra.uas.model.Bookmark;
import edu.fra.uas.model.Todo;
import edu.fra.uas.model.User;
import edu.fra.uas.service.BookmarkService;
import edu.fra.uas.service.TodoService;
import edu.fra.uas.service.UserService;

@RestController
@RequestMapping("/api")

public class GatewayController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(GatewayController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private BookmarkService bookmarkService;

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
    // ToDo Integration
    // ##############################################################################################################

    @GetMapping(value = "/users/{userId}/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getTodosByUserId(@PathVariable("userId") Long userId) {
        log.debug("getTodosByUserId() is called");
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        ResponseEntity<?> response = todoService.getAllTodosByUserId(userId);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @PostMapping(value = "/users/{userId}/todos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createToDo(@PathVariable("userId") Long userId, @RequestBody String description) {
        log.debug("createToDo() is called for userId: " + userId);
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        ResponseEntity<?> response = todoService.createTodoByUserId(userId, description);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.CREATED)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/users/" + userId + "/todos"));
            return new ResponseEntity<>(response.getBody(), headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response.getBody(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping(value = "/users/{userId}/todos/{todoId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateToDo(@PathVariable("userId") Long userId, @PathVariable("todoId") Long todoId,
            @RequestBody Todo todo) {
        log.debug("updateToDo() is called for todoId: " + todoId);
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        ResponseEntity<?> response = todoService.updateTodo(userId, todoId, todo);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{userId}/todos/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteToDo(@PathVariable("userId") Long userId, @PathVariable("todoId") Long todoId) {
        log.debug("deleteToDo() is called for todoId: " + todoId);
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        ResponseEntity<?> response = todoService.deleteTodoByUserId(userId, todoId);
        if (response.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return response;
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    // ##############################################################################################################
    // Bookmark
    // ##############################################################################################################

    @GetMapping(value = "/users/{userId}/bookmarks", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getAllBookmarksByUserId(@PathVariable("userId") Long userId) {
        log.debug("getBookmarksByUserId() is called");
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        List<Bookmark> bookmarks = bookmarkService.getAllBookmarksByUserId(userId);
        if (bookmarks != null && !bookmarks.isEmpty()) {
            return new ResponseEntity<>(bookmarks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No bookmarks found for user.", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/users/{userId}/bookmarks/{bookmarkId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getBookmarkByUserId(@PathVariable("userId") Long userId,@PathVariable("bookmarkId")  Long bookmarkId) {
        log.debug("getBookmarksByUserId() is called");
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        Bookmark bookmark = bookmarkService.getBookMarkByUserId(userId, bookmarkId);
        if (bookmark != null) {
            return new ResponseEntity<>(bookmark, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No bookmarks found for user.", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping(value = "/users/{userId}/bookmarks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createBookmark(@PathVariable("userId") Long userId, @RequestBody Bookmark bookmark) {
        log.debug("createBookmark() is called for userId: " + userId);
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        Bookmark createdBookmark = bookmarkService.createBookmark(userId, bookmark.getTitle(), bookmark.getLink());
        if (createdBookmark != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    URI.create("/api/users/" + userId + "/bookmark/"
                            + createdBookmark.getBookmarkId()));
            return new ResponseEntity<>(createdBookmark, headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create bookmark", HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @PutMapping(value = "/users/{userId}/bookmarks/{bookmarkId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateBookmarkByUserId(@PathVariable("userId") Long userId,
            @PathVariable("bookmarkId") Long bookmarkId,
            @RequestBody Bookmark bookmark) {
        log.debug("updateBookmark() is called for bookmarkId: " + bookmarkId);
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        Bookmark updatedBookmark = bookmarkService.updateBookmarkForUserId(userId, bookmarkId, bookmark.getTitle(),
                bookmark.getLink());
        if (updatedBookmark != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(
                    URI.create("/api/users/" + userId + "/bookmarks/" + bookmarkId
                            + updatedBookmark.getBookmarkId()));
            return new ResponseEntity<>(updatedBookmark, headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create bookmark", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping(value = "/users/{userId}/bookmarks/{bookmarkId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> deleteBookmarkByUserId(@PathVariable("userId") Long userId,
            @PathVariable("bookmarkId") Long bookmarkId) {
        log.debug("deletebookmark() is called for todoId: " + bookmarkId);
        ResponseEntity<?> userResponse = userService.getUserById(userId);
        if (userResponse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return userResponse;
        }
        Bookmark deletedBookmark = bookmarkService.deleteBookmarkByUserId(userId, bookmarkId);
        if (deletedBookmark != null) {
            return new ResponseEntity<>(deletedBookmark, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No bookmarks found for user.", HttpStatus.NO_CONTENT);
        }

    }
}