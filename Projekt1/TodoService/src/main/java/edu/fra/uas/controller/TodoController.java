package edu.fra.uas.controller;

import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.model.Todo;
import edu.fra.uas.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

@RestController
public class TodoController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(TodoController.class);

    @Autowired
    TodoService todoService;

    @GetMapping(value = "/users/{userId}/todos", produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public ResponseEntity<List<Todo>> getAllTodosByUserId(@PathVariable("userId") Long userId) {
        log.info("getAllTodosByUserId() is called");
        Iterable<Todo> todoIter = todoService.getAllTodosByUserId(userId);
        List<Todo> todos = new ArrayList<>();
        for (Todo oneTodo : todoIter) {
            todos.add(oneTodo);
        }
        if (todos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<List<Todo>>(todos, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}/todos/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public ResponseEntity<Todo> getTodoByUserId(@PathVariable("userId") Long userId,
            @PathVariable("todoId") Long todoId) {
        log.info("getAllTodosByUserId() is called");
        Todo todo = todoService.getTodoByUserId(userId, todoId);

        if (todo == null) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<Todo>(todo, HttpStatus.OK);
    }

    @PostMapping(value = "/users/{userId}/todos", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@PathVariable("userId") Long userId, @RequestBody String description) {
        log.info("Create new todo for user:" + userId);

        String detail = null;
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);

        if (description == null || description.isEmpty()) {
            detail = "discription cannot be empty or null";
            return ResponseEntity.unprocessableEntity().body(pd);
        }

        Todo newTodo = todoService.createTodo(userId, description);
        return new ResponseEntity<Todo>(newTodo, HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{userId}/todos/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable("todoId") Long todoId, @PathVariable("userId") Long userId,
            @RequestBody Todo todo) {
        log.info("update todo:" + todoId + "for User: " + userId);

        Todo newTodo = todoService.updateTodoForUserId(userId, todoId, todo);
        return new ResponseEntity<Todo>(newTodo, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{userId}/todos/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("userId") Long userId, @PathVariable("todoId") Long todoId) {
        log.debug("delete() is called");
        Todo todo = todoService.getTodoByUserId(userId, todoId);
        if (todo == null) {
            return ResponseEntity.notFound().build();
        }
        todoService.deleteTodoByUserId(userId, todoId);
        return new ResponseEntity<Todo>(todo, HttpStatus.OK);
    }

}
