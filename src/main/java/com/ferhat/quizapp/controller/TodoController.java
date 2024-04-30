package com.ferhat.quizapp.controller;

import com.ferhat.quizapp.entity.Todo;
import com.ferhat.quizapp.service.TodoService;
import com.ferhat.quizapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<Todo> save(@RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.save(todo));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Todo>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Todo todo = todoService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(todo);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Todo todo) {
        try {
            Todo currentTodo = todoService.getById(id);
            currentTodo.setContent(todo.getContent());
            currentTodo.setHeader(todo.getHeader());
            currentTodo.setIsCompleted(todo.getIsCompleted());
            return ResponseEntity.status(HttpStatus.OK).body(todoService.save(currentTodo));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/saveByUserName/{userName}")
    public ResponseEntity<?> saveByUserName(@PathVariable String userName, @RequestBody Todo todo) {
        Todo result = null;
        try {
            result = todoService.saveByUserName(userName, todo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/getTodosByUserName/{userName}")
    public ResponseEntity<List<Todo>> getTodosByUserName(@PathVariable String userName) {
        List<Todo> result = userService.getTodosByUserName(userName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/deleteById/{id}/{userName}")
    public ResponseEntity<String> deleteById(@PathVariable Long id, @PathVariable String userName) {
        todoService.deleteById(id, userName);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }
}
