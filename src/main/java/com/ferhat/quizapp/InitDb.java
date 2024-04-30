package com.ferhat.quizapp;

import com.ferhat.quizapp.entity.Todo;
import com.ferhat.quizapp.entity.TodoUser;
import com.ferhat.quizapp.service.TodoService;
import com.ferhat.quizapp.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InitDb {

    private final UserService userService;

    private final TodoService todoService;

    public InitDb(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @PostConstruct
    public void initDb() {
        Todo todo1 = new Todo(1L, "Java", "Learn Java", 0);
        Todo todo2 = new Todo(2L, "Spring", "Learn Security", 1);
        Todo todo3 = new Todo(3L, "Docker", "Learn Docker", 0);
        todoService.save(todo1);
        todoService.save(todo2);
        todoService.save(todo3);

        List<Todo> todos = Arrays.asList(todo1);
        List<Todo> todos1 = Arrays.asList(todo2, todo3);


        TodoUser todoUser1 = new TodoUser(1L, "ferhatb", "test123", todos);
        TodoUser todoUser2 = new TodoUser(2L, "ahmet", "test123", todos1);
        userService.save(todoUser1);
        userService.save(todoUser2);
    }
}
