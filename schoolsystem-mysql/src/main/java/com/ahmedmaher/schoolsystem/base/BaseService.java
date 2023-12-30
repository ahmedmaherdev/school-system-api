package com.ahmedmaher.schoolsystem.base;

import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T> {
    List<T> getAll(Pageable pageable);

    T getOne(long id) throws NotFoundException;

    T createOne(T entity);

    T updateOne(long id, T entity) throws NotFoundException;

    void deleteOne(long id) throws NotFoundException;

    List<T> search(String word , Pageable pageable);
}
