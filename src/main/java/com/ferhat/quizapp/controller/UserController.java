package com.ferhat.quizapp.controller;

import com.ferhat.quizapp.entity.TodoUser;
import com.ferhat.quizapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<TodoUser> save(@RequestBody TodoUser todoUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(todoUser));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<TodoUser>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            TodoUser todoUser = userService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(todoUser);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody TodoUser todoUser) {

        try {
            TodoUser currentUser = userService.getById(id);
            currentUser.setUserName(todoUser.getUserName());
            currentUser.setPassword(todoUser.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(userService.save(currentUser));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("complete/{userId}/{todoId}")
    public ResponseEntity<String> completeByUserIdAndTodoId(@PathVariable Long userId, @PathVariable Long todoId) {
        try {
            String result = userService.completeTodoById(userId, todoId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("revert/{userId}/{todoId}")
    public ResponseEntity<String> revertByUserIdAndTodoId(@PathVariable Long userId, @PathVariable Long todoId) {
        try {
            String result = userService.revertTodoById(userId, todoId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String password) {
        try {
            TodoUser user = userService.login(userName, password);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
