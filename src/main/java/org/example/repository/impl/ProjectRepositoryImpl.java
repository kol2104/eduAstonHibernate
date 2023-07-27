package org.example.repository.impl;

import org.example.exception.MyNullPointerException;
import org.example.exception.PositionNotFoundException;
import org.example.exception.ProjectNotFoundException;
import org.example.model.Employee;
import org.example.model.Position;
import org.example.model.Project;
import org.example.repository.ProjectRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public ProjectRepositoryImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    public Optional<Project> get(Long id) {
        checkNotNull(id);
        Session session = sessionFactory.getCurrentSession();
        Project project = session.get(Project.class, id);
        if (project == null) {
            return Optional.empty();
        }
        return Optional.of(project);
    }

    @Override
    public List<Project> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Project").list();
    }

    @Override
    public boolean save(Project project) {
        checkNotNull(project);
        Session session = sessionFactory.getCurrentSession();
        session.persist(project);
        return true;
    }

    @Override
    public void update(Project project) {
        checkNotNull(project);
        checkNotNull(project.getId());
        Session session = sessionFactory.getCurrentSession();
        Project oldProject = session.get(Project.class, project.getId());
        if (oldProject == null) {
            throw new ProjectNotFoundException(project.getId());
        }
        Query query = session.createQuery("update Project p set p.name = :name, p.client = :client " +
                "where p.id = :id");
        query.setParameter("id", project.getId());
        query.setParameter("name", project.getName() != null ? project.getName() : oldProject.getName());
        query.setParameter("client", project.getClient() != null ? project.getClient() : oldProject.getClient());
        query.executeUpdate();
    }

    @Override
    public void delete(Long project) {
        checkNotNull(project);
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Project p where p.id = :id");
        query.setParameter("id", project);
        query.executeUpdate();
    }

    @Override
    public List<Project> getProjectsByEmployeeLastName(String lastName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Employee> query = session.createQuery("from Employee e where e.lastName = :lastName", Employee.class);
        query.setParameter("lastName", lastName);
        return query.uniqueResult().getProjects();
    }

    private void checkNotNull(Object o) {
        if (o == null) {
            throw new MyNullPointerException();
        }
    }
}
