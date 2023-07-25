package org.example.service;

import org.example.model.ProjectToEmployee;
import org.example.repository.ProjectToEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectToEmployeeService {

    @Autowired
    private ProjectToEmployeeRepository projectToEmployeeRepository;

    public List<ProjectToEmployee> getAll() {
        return projectToEmployeeRepository.getAll();
    }

    public boolean save(ProjectToEmployee projectToEmployee) {
        return projectToEmployeeRepository.save(projectToEmployee);
    }

    public void delete(ProjectToEmployee projectToEmployee) {
        projectToEmployeeRepository.delete(projectToEmployee);
    }
}
