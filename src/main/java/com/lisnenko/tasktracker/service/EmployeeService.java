package com.lisnenko.tasktracker.service;

import com.lisnenko.tasktracker.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int id);

    Employee save(Employee employee);

    void deleteById(int id);

}
