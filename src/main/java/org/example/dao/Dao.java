package org.example.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(Long id);

    List<T> getAll();

    boolean save(T t);

    void update(T t);

    void delete(Long t);
}
