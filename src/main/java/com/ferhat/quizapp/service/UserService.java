package com.ferhat.quizapp.service;

import com.ferhat.quizapp.entity.Todo;
import com.ferhat.quizapp.entity.TodoUser;
import com.ferhat.quizapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public TodoUser save(TodoUser todoUser) {
        return userRepository.save(todoUser);
    }

    public List<TodoUser> getAll() {
        return userRepository.findAll();
    }

    public TodoUser getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User Not Found"));
    }

    public TodoUser login(String userName, String password) {
        TodoUser user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new NoSuchElementException("Check your username or password");
        }
        if (user.getPassword().equals(password)) {
            return user;
        }
        throw new NoSuchElementException("Check your username or password");
    }


    public String completeTodoById(Long userId, Long todoId) {
        TodoUser user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User Not Found"));

        Todo todo = user.getTodoList().stream().filter(n -> n.getId() == todoId).findFirst().orElseThrow(() -> new NoSuchElementException("Given user does not have the given todo"));
        int i = user.getTodoList().indexOf(todo);
        if (user.getTodoList().get(i).getIsCompleted() == 1) {
            throw new RuntimeException("Todo is already completed.");
        }
        user.getTodoList().get(i).setIsCompleted(1);
        userRepository.save(user);
        return "Todo is completed successfully";
    }

    public String revertTodoById(Long userId, Long todoId) {
        TodoUser user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User Not Found"));
        Todo todo = user.getTodoList().stream().filter(n -> n.getId() == todoId).findFirst().orElseThrow(() -> new NoSuchElementException("Given user does not have the given todo"));
        int i = user.getTodoList().indexOf(todo);
        if (user.getTodoList().get(i).getIsCompleted() == 0) {
            throw new RuntimeException("Todo cannot be reverted.");
        }
        user.getTodoList().get(i).setIsCompleted(0);
        userRepository.save(user);
        return "Todo is reverted successfully";
    }

    ;

    public TodoUser getByUserName(String userName) {
        TodoUser user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new NoSuchElementException("User Not Found");
        }
        return user;
    }

    public List<Todo> getTodosByUserName(String userName) {
        List<Todo> todos = userRepository.findByUserName(userName).getTodoList();
        return todos;
    }


}
