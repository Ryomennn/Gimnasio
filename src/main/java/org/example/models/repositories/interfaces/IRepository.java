package org.example.models.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRepository<T>{
    void save(T t) throws SQLException;
    List<T> findAll() throws SQLException;
    Optional<T> findById(Integer id) throws SQLException;
    void delete(Integer id) throws SQLException;
    public void update(T t) throws SQLException;
}
