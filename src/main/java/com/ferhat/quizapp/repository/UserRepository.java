package com.ferhat.quizapp.repository;

import com.ferhat.quizapp.entity.TodoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<TodoUser, Long> {
    public TodoUser findByUserName(String username);
}
