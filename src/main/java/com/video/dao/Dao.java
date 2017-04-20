package com.video.dao;

import java.util.Collection;
import java.util.List;

/**
 * Created by kotabek on 4/20/17.
 */
public interface Dao<E> {
    List<E> getAll();

    void save(E obj);

    void delete(E obj);

    E get(Long id);

    List<E> get(Collection<Long> ids);

    void deleteByID(Long id);
}