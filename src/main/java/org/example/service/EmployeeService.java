package org.example.service;

import org.example.exception.EmployeeNotFoundException;
import org.example.exception.PositionNotFoundException;
import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionService positionService;

    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAll();
    }

    @Transactional
    public boolean saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional
    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.get(id);
    }
    @Transactional
    public void updateEmployee(Employee employee) {
        employeeRepository.update(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.delete(id);
    }

    @Transactional
    public List<Employee> getEmployeeByPositionName(String name) {
        return employeeRepository.getEmployeeByPositionName(name);
    }

    @Transactional
    public List<Employee> getEmployeesByProjectName(String name) {
        return employeeRepository.getEmployeesByProjectName(name);
    }
}
