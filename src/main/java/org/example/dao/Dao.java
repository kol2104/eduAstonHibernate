package org.example.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(int id);

    List<T> getAll();

    boolean save(T t);

    void update(T t);

    void delete(int t);
}