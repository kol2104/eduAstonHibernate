package org.example.repository;

import org.example.dao.Dao;
import org.example.model.Project;

import java.util.List;

public interface ProjectRepository extends Dao<Project> {
    List<Project> getProjectsByEmployeeName(String name);
}
