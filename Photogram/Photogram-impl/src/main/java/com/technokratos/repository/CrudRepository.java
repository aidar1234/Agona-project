package com.technokratos.repository;

import java.util.Optional;

public interface CrudRepository<T, V> {

    V save(T t);

    Optional<T> findById(V v);

    void update(T t);

    void deleteById(V v);
}
