package org.example.service;

import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public List<Project> getAllProjects() {
        return projectRepository.getAll();
    }

    @Transactional
    public boolean saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Transactional
    public Optional<Project> getProject(Long id) {
        return projectRepository.get(id);
    }

    @Transactional
    public void updateProject(Project project) {
        projectRepository.update(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.delete(id);
    }

    @Transactional
    public List<Project> getProjectsByEmployeeLastName(String name) {
        return projectRepository.getProjectsByEmployeeLastName(name);
    }
}
