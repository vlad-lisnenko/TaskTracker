package com.lisnenko.tasktracker.dao;

import com.lisnenko.tasktracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
