package by.epam.web.dao;

import java.io.Serializable;

public interface Dao<T> {
    T save(T t);
    void delete(Serializable id);
    T get (Serializable id);
    T update(T t);
}
