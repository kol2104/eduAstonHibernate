package org.example.repository.impl;

import org.example.exception.MyNullPointerException;
import org.example.exception.PositionNotFoundException;
import org.example.model.Position;
import org.example.repository.PositionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PositionRepositoryImpl implements PositionRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public PositionRepositoryImpl(SessionFactory sessionFactory) {this.sessionFactory = sessionFactory;}

    @Override
    public Optional<Position> get(Long id) {
        checkNotNull(id);
        Session session = sessionFactory.getCurrentSession();
        Position position = session.get(Position.class, id);
        if (position == null) {
            return Optional.empty();
        }
        return Optional.of(position);
    }

    @Override
    public List<Position> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Position").list();
    }

    @Override
    public boolean save(Position position) {
        checkNotNull(position);
        Session session = sessionFactory.getCurrentSession();
        session.persist(position);
        return true;
    }

    @Override
    public void update(Position position) {
        checkNotNull(position);
        checkNotNull(position.getId());
        Session session = sessionFactory.getCurrentSession();
        Position oldPosition = session.get(Position.class, position.getId());
        if (oldPosition == null) {
            throw new PositionNotFoundException(position.getId());
        }
        Query query = session.createQuery("update Position p set p.name = :name where p.id = :id");
        query.setParameter("id", position.getId());
        query.setParameter("name", position.getName() != null ? position.getName() : oldPosition.getName());
        query.executeUpdate();
    }

    @Override
    public void delete(Long position) {
        checkNotNull(position);
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Position p where p.id = :id");
        query.setParameter("id", position);
        query.executeUpdate();
    }

    private void checkNotNull(Object o) {
        if (o == null) {
            throw new MyNullPointerException();
        }
    }
}
