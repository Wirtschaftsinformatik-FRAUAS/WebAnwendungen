package edu.fra.uas.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import edu.fra.uas.model.Todo;
import edu.fra.uas.repository.TodoRepsitory;

@Service
public class TodoService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TodoService.class);

    @Autowired
    private TodoRepsitory todoRepsitory;

    private long nextTodoId = 1;

    public Todo createTodo(long userId, String description) {
        Todo todo = new Todo();
        todo.setTodoId(nextTodoId++);
        log.debug("createTodo: " + todo.getTodoId()+" for User: "+ userId);
        todo.setUserId(userId);
        todo.setDescription(description);
        todoRepsitory.put(todo.getTodoId(), todo);
        return todoRepsitory.get(todo.getTodoId());
    }

    public Iterable<Todo> getAllTodosByUserId(long userId) {
        log.debug("getAllTodosByUserId" + userId);
        Iterable<Todo> todoIter = todoRepsitory.values();
        ArrayList<Todo> todos = new ArrayList<>();
        for (Todo todo : todoIter) {
            if (todo.getUserId() == userId) {
                todos.add(todo);
            }
        }

        return todos;
    }

    public Todo getTodoByUserId(long userId, long todoId) {
        log.debug("get Todo : " + todoId + " for User: " + userId);
        for (Todo toDo : todoRepsitory.values()) {
            if (toDo.getUserId() == userId && toDo.getTodoId() == todoId) {
                return toDo;
            }
        }
        return null;
    }

    public Todo updateTodoForUserId(long userId, long todoId, Todo newTodo) {
        log.debug("update Todo: " + todoId + " for User: " + userId);
        Todo todo = getTodoByUserId(userId, todoId);
        todo.setCompleted(newTodo.isCompleted());
        todoRepsitory.put(todo.getTodoId(), todo);
        return todoRepsitory.get(todo.getTodoId());
    }

    public Todo deleteTodoByUserId(long userId, long todoId) {
        log.debug("delete Todo: " + todoId + " for User: " + userId);
        Todo todo = getTodoByUserId(userId, todoId);
        return todoRepsitory.remove(todo.getTodoId());
    }

}
