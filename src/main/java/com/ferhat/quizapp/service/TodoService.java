package com.ferhat.quizapp.service;

import com.ferhat.quizapp.entity.Todo;
import com.ferhat.quizapp.entity.TodoUser;
import com.ferhat.quizapp.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    private final UserService userService;

    public TodoService(TodoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public Todo getById(Long id) {
        return todoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Todo Not Found"));
    }

    @Transactional
    public void deleteById(Long todoId, String userName) {
        TodoUser user = userService.getByUserName(userName);
        Todo todo = user.getTodoList().stream().filter(n -> n.getId() == todoId).findFirst().get();
        user.getTodoList().remove(todo);
        userService.save(user);
        todoRepository.deleteById(todoId);
    }

    @Transactional
    public Todo saveByUserName(String userName, Todo todo) throws Exception {
        todoRepository.save(todo);
        try {
            TodoUser user = userService.getByUserName(userName);
            user.addTodo(todo);
            userService.save(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return todo;
    }

    public Todo updateById(Long id, Todo todo) {
        Todo currentTodo = todoRepository.getById(id);
        if (currentTodo == null) {
            throw new NoSuchElementException("Todo not found.");
        }
        currentTodo.setContent(todo.getContent());
        currentTodo.setHeader(todo.getHeader());
        currentTodo.setIsCompleted(todo.getIsCompleted());
        return todoRepository.save(currentTodo);
    }
}
