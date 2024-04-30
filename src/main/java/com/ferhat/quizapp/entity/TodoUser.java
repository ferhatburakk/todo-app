package com.ferhat.quizapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todo_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TodoUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todoList;

    public void addTodo(Todo todo) {
        if (todoList == null) {
            todoList = new ArrayList<>();
        }
        todoList.add(todo);
    }

    public TodoUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
