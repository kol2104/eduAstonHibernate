package org.example.service;

import org.example.exception.EmployeeNotFoundException;
import org.example.exception.PositionNotFoundException;
import org.example.model.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionService positionService;

    public List<Employee> getAllEmployees() {
        return setPositionById(employeeRepository.getAll());
    }

    public boolean saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Integer id) {
        Employee employee = employeeRepository.get(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employee.setPosition(positionService.getPosition(employee.getPosition().getId())
                .orElseThrow(() -> new PositionNotFoundException(employee.getPosition().getId())));
        return employee;
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.update(employee);
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.delete(id);
    }

    public List<Employee> getEmployeeByPositionName(String name) {
        return setPositionById(employeeRepository.getEmployeeByPositionName(name));
    }

    public List<Employee> getEmployeesByProjectName(String name) {
        return setPositionById(employeeRepository.getEmployeesByProjectName(name));
    }

    private List<Employee> setPositionById(List<Employee> employees) {
        employees.forEach(empl -> empl.setPosition(positionService.getPosition(empl.getPosition().getId())
                .orElseThrow(() -> new PositionNotFoundException(empl.getPosition().getId())))
        );
        return employees;
    }
}
