package org.example.repository.impl;

import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final String GET_PROJECT_BY_ID = "select * from project where id = ?;";
    private final String GET_ALL_PROJECTS = "select * from project;";
    private final String SAVE_PROJECT = "insert into project (name, client) values (?,?);";
    private final String UPDATE_PROJECT = "update project set name = ?, client = ? where id = ?;";
    private final String DELETE_PROJECT = "delete from project where id = ?;";
    private final String GET_PROJECTS_BY_EMPLOYEE_NAME = "select p.* from employee e join projects_employee pe " +
            "on e.id = pe.employee_id join project p on p.id = pe.project_id where e.name = ?;";

    private final Connection connection;

    @Autowired
    public ProjectRepositoryImpl(Connection connection) {this.connection = connection;}

    @Override
    public Optional<Project> get(int id) {
        try (PreparedStatement prst =  connection.prepareStatement(GET_PROJECT_BY_ID)){
            prst.setInt(1, id);

            ResultSet resultSet = prst.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            Project project = new Project();
            project.setId(resultSet.getInt("id"));
            project.setName(resultSet.getString("name"));
            project.setClient(resultSet.getString("client"));
            return Optional.of(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Project> getAll() {
        List<Project> projects = new ArrayList<>();
        try (PreparedStatement prst =  connection.prepareStatement(GET_ALL_PROJECTS)){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setClient(resultSet.getString("client"));

                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public boolean save(Project project) {
        try (PreparedStatement prst =  connection.prepareStatement(SAVE_PROJECT)){
            prst.setString(1, project.getName());
            prst.setString(2, project.getClient());

            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void update(Project project) {
        try (PreparedStatement prst =  connection.prepareStatement(UPDATE_PROJECT)){
            prst.setString(1, project.getName());
            prst.setString(2, project.getClient());


            prst.setInt(3, project.getId());
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Project project) {
        try (PreparedStatement prst =  connection.prepareStatement(DELETE_PROJECT)){
            prst.setInt(1, project.getId());
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> getProjectsByEmployeeName(String name) {
        List<Project> projects = new ArrayList<>();
        try (PreparedStatement prst =  connection.prepareStatement(GET_PROJECTS_BY_EMPLOYEE_NAME)){
            prst.setString(1, name);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setClient(resultSet.getString("client"));

                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
