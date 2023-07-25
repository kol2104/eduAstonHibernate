package org.example.controller;

import org.example.model.ProjectToEmployee;
import org.example.service.ProjectToEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/project-employee")
public class ProjectToEmployeeController {

    @Autowired
    private ProjectToEmployeeService projectToEmployeeService;

    @GetMapping
    public ResponseEntity getAll() {
        return new ResponseEntity<>(projectToEmployeeService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ProjectToEmployee projectToEmployee) {
        if (projectToEmployeeService.save(projectToEmployee)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody ProjectToEmployee projectToEmployee) {
        projectToEmployeeService.delete(projectToEmployee);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
