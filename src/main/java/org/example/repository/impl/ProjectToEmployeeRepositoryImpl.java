package org.example.repository.impl;

import org.example.model.ProjectToEmployee;
import org.example.repository.ProjectToEmployeeRepository;
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
public class ProjectToEmployeeRepositoryImpl implements ProjectToEmployeeRepository {

    private final String GET_ALL_PROJECT_EMPLOYEE = "select * from projects_employee;";
    private final String SAVE_PROJECT_EMPLOYEE = "insert into projects_employee (project_id, employee_id) values (?,?);";
    private final String DELETE_PROJECT_EMPLOYEE = "delete from projects_employee where project_id = ? and employee_id = ?;";

    private final Connection connection;

    @Autowired
    public ProjectToEmployeeRepositoryImpl(Connection connection) {this.connection = connection;}

    @Override
    public Optional<ProjectToEmployee> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<ProjectToEmployee> getAll() {
        List<ProjectToEmployee> projectToEmployees = new ArrayList<>();
        try (PreparedStatement prst =  connection.prepareStatement(GET_ALL_PROJECT_EMPLOYEE)){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                ProjectToEmployee projectToEmployee = new ProjectToEmployee();
                projectToEmployee.setProjectId(resultSet.getInt("project_id"));
                projectToEmployee.setEmployeeId(resultSet.getInt("employee_id"));

                projectToEmployees.add(projectToEmployee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectToEmployees;
    }

    @Override
    public boolean save(ProjectToEmployee projectToEmployee) {
        try (PreparedStatement prst =  connection.prepareStatement(SAVE_PROJECT_EMPLOYEE)){
            prst.setInt(1, projectToEmployee.getProjectId());
            prst.setInt(2, projectToEmployee.getEmployeeId());

            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void update(ProjectToEmployee projectToEmployee) {

    }

    @Override
    public void delete(ProjectToEmployee t) {
        try (PreparedStatement prst =  connection.prepareStatement(DELETE_PROJECT_EMPLOYEE)){
            prst.setInt(1, t.getProjectId());
            prst.setInt(2, t.getEmployeeId());

            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
