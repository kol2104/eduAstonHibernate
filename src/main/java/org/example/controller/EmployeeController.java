package org.example.controller;

import org.example.exception.EmployeeNotFoundException;
import org.example.exception.MyNullPointerException;
import org.example.model.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
@ControllerAdvice
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity getEmployee(@PathVariable("id") Long id) {
        Optional<Employee> employee = employeeService.getEmployee(id);
        if (employee.isPresent()){
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity< List<Employee> > getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveEmployee(@RequestBody Employee employee) {
        if (employeeService.saveEmployee(employee)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/position")
    public ResponseEntity getEmployeeByPositionName(@RequestParam("name") String name) {
        return new ResponseEntity<>(employeeService.getEmployeeByPositionName(name), HttpStatus.OK);
    }

    @GetMapping("/project")
    public ResponseEntity getEmployeesByProjectName(@RequestParam("name") String name) {
        return new ResponseEntity<>(employeeService.getEmployeesByProjectName(name), HttpStatus.OK);
    }

    @ExceptionHandler({EmployeeNotFoundException.class})
    public ResponseEntity handleExceptionNotFound(Exception exception) {
        Object errorBody = exception.getMessage();
        return new ResponseEntity(errorBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MyNullPointerException.class})
    public ResponseEntity handleExceptionNull(Exception exception) {
        Object errorBody = exception.getMessage();
        return new ResponseEntity(errorBody, HttpStatus.BAD_REQUEST);
    }
}
