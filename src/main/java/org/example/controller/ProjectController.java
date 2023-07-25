package org.example.controller;

import org.example.model.Project;
import org.example.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity getProject(@PathVariable("id") Integer id) {
        Optional<Project> project = projectService.getProject(id);
        if (project.isPresent()){
            return new ResponseEntity<>(project, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity getAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveProject(@RequestBody Project project) {
        if (projectService.saveProject(project)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity updateProject(@RequestBody Project project) {
        projectService.updateProject(project);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProject(@PathVariable("id") Integer id) {
        projectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/employee")
    public ResponseEntity getProjectsByEmployeeName(@RequestParam("name") String name) {
        return new ResponseEntity<>(projectService.getProjectsByEmployeeName(name), HttpStatus.OK);
    }
}
