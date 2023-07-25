package org.example.repository;

import org.example.dao.Dao;
import org.example.model.Employee;

import java.util.List;

public interface EmployeeRepository extends Dao<Employee> {
    List<Employee> getEmployeeByPositionName(String name);
    List<Employee> getEmployeesByProjectName(String name);
}
