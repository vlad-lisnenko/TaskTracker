package com.lisnenko.tasktracker.dao;

import com.lisnenko.tasktracker.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@RepositoryRestResource(path = "members")
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // add a method to sort by lustName
    public List<Employee> findAllByOrderByLastName();

}
