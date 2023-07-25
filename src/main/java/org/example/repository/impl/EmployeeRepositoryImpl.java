package org.example.repository.impl;

import org.example.model.Employee;
import org.example.model.Position;
import org.example.model.Project;
import org.example.repository.EmployeeRepository;
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
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final String GET_EMPLOYEE_BY_ID = "select * from employee where id = ?;";
    private final String GET_ALL_EMPLOYEES = "select * from employee;";
    private final String SAVE_EMPLOYEE = "insert into employee (name, positions_id) values (?,?);";
    private final String UPDATE_EMPLOYEE = "update employee set name = ?, positions_id = ? where id = ?;";
    private final String DELETE_EMPLOYEE = "delete from employee where id = ?;";
    private final String GET_EMPLOYEES_BY_POSITION_NAME = "select e.* from employee e join positions pos " +
            "on e.positions_id = pos.id where pos.name = ?;";
    private final String GET_EMPLOYEES_BY_PROJECT_NAME = "select e.* from employee e join projects_employee pe " +
            "on e.id = pe.employee_id join project p on p.id = pe.project_id where p.name = ?;";

    private final Connection connection;

    @Autowired
    public EmployeeRepositoryImpl(Connection connection) {this.connection = connection;}

    @Override
    public Optional<Employee> get(int id) {
        try (PreparedStatement prst =  connection.prepareStatement(GET_EMPLOYEE_BY_ID)){
            prst.setInt(1, id);

            ResultSet resultSet = prst.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            Employee employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setName(resultSet.getString("name"));
            Position position = new Position(resultSet.getInt("positions_id"));
            employee.setPosition(position);
            return Optional.of(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement prst =  connection.prepareStatement(GET_ALL_EMPLOYEES)){
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                Position position = new Position(resultSet.getInt("positions_id"));
                employee.setPosition(position);

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public boolean save(Employee employee) {
        try (PreparedStatement prst =  connection.prepareStatement(SAVE_EMPLOYEE)){
            prst.setString(1, employee.getName());
            prst.setInt(2, employee.getPosition().getId());

            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void update(Employee employee) {
        try (PreparedStatement prst =  connection.prepareStatement(UPDATE_EMPLOYEE)){
            prst.setString(1, employee.getName());
            prst.setInt(2, employee.getPosition().getId());

            prst.setInt(3, employee.getId());
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int employeeId) {
        try (PreparedStatement prst =  connection.prepareStatement(DELETE_EMPLOYEE)){
            prst.setInt(1, employeeId);
            prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> getEmployeeByPositionName(String name) {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement prst =  connection.prepareStatement(GET_EMPLOYEES_BY_POSITION_NAME)){
            prst.setString(1, name);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                Position position = new Position(resultSet.getInt("positions_id"));
                employee.setPosition(position);

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public List<Employee> getEmployeesByProjectName(String name) {
        List<Employee> employees = new ArrayList<>();
        try (PreparedStatement prst =  connection.prepareStatement(GET_EMPLOYEES_BY_PROJECT_NAME)){
            prst.setString(1, name);
            ResultSet resultSet = prst.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                Position position = new Position(resultSet.getInt("positions_id"));
                employee.setPosition(position);

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
