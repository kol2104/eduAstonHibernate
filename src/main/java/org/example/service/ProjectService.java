package org.example.service;

import org.example.model.Employee;
import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.getAll();
    }

    public boolean saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> getProject(Integer id) {
        return projectRepository.get(id);
    }

    public void updateProject(Project project) {
        projectRepository.update(project);
    }

    public void deleteProject(Integer id) {
        projectRepository.delete(id);
    }

    public List<Project> getProjectsByEmployeeName(String name) {
        return projectRepository.getProjectsByEmployeeName(name);
    }
}
