package org.example.repository.impl;

import org.example.exception.EmployeeNotFoundException;
import org.example.exception.MyNullPointerException;
import org.example.exception.PositionNotFoundException;
import org.example.exception.ProjectNotFoundException;
import org.example.model.Employee;
import org.example.model.Position;
import org.example.model.Project;
import org.example.repository.EmployeeRepository;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public EmployeeRepositoryImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    public Optional<Employee> get(Long id) {
        checkNotNull(id);
        Session session = sessionFactory.getCurrentSession();
        Employee employee = session.get(Employee.class, id);
        if (employee == null) {
            return Optional.empty();
        }
        return Optional.of(employee);
    }

    @Override
    public List<Employee> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select e from Employee e left join fetch e.position").list();
    }

    @Override
    public boolean save(Employee employee) {
        checkNotNull(employee);
        Session session = sessionFactory.getCurrentSession();
        if (employee.getPosition() != null) {
            checkNotNull(employee.getPosition().getId());
            employee.setPosition(session.get(Position.class, employee.getPosition().getId()));
        }
        List<Project> projects = getProjectsValue(session, employee);
        employee.setProjects(projects);

        session.persist(employee);
        return true;
    }

    @Override
    public void update(Employee employee) {
        checkNotNull(employee);
        checkNotNull(employee.getId());
        Session session = sessionFactory.getCurrentSession();
        Employee oldEmployee = session.get(Employee.class, employee.getId());
        if (oldEmployee == null) {
            throw new EmployeeNotFoundException(employee.getId());
        }
        employee.setFirstName(employee.getFirstName() != null ? employee.getFirstName() : oldEmployee.getFirstName());
        employee.setLastName(employee.getLastName() != null ? employee.getLastName() : oldEmployee.getLastName());

        Position position = getPositionValue(session, employee, oldEmployee);
        employee.setPosition(position);

        List<Project> projects = getProjectsValue(session, employee);
        employee.setProjects(projects);

        session.merge(employee);
    }

    @Override
    public void delete(Long employee) {
        checkNotNull(employee);
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Employee e where e.id = :id");
        query.setParameter("id", employee);
        query.executeUpdate();
    }

    @Override
    public List<Employee> getEmployeeByPositionName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Position> query = session.createQuery("from Position p where p.name = :name", Position.class);
        query.setParameter("name", name);
        Position position = query.uniqueResult();
        Hibernate.initialize(position.getEmployees());
        return position.getEmployees();
    }

    @Override
    public List<Employee> getEmployeesByProjectName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Project> query = session.createQuery("from Project p where p.name = :name", Project.class);
        query.setParameter("name", name);
        Project project = query.uniqueResult();
        Hibernate.initialize(project.getEmployees());
        return project.getEmployees();
    }

    private void checkNotNull(Object o) {
        if (o == null) {
            throw new MyNullPointerException();
        }
    }

    private Position getPositionValue(Session session, Employee employee, Employee oldEmployee) {
        Position position = null;
        if (employee.getPosition() != null) {
            position = session.get(Position.class, employee.getPosition().getId());
            if (position == null) {
                throw new PositionNotFoundException(employee.getPosition().getId());
            }
        } else {
            position = oldEmployee.getPosition();
        }
        return position;
    }

    private List<Project> getProjectsValue(Session session, Employee employee) {
        List<Project> projects = null;
        if (employee.getProjects() != null) {
            projects = new ArrayList<>();
            for (Project project : employee.getProjects()) {
                checkNotNull(project.getId());
                Project projectFromDB = session.get(Project.class, project.getId());
                if (projectFromDB == null){
                    throw new ProjectNotFoundException(project.getId());
                }
                projects.add(projectFromDB);
            }
        }
        return projects;
    }
}
