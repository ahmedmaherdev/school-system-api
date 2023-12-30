package com.ahmedmaher.schoolsystem.base;

import com.ahmedmaher.schoolsystem.exception.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T> {
    List<T> getAll(Pageable pageable);

    T getOne(String id) throws NotFoundException;

    T createOne(T document);

    T updateOne(String id, T document) throws NotFoundException;

    void deleteOne(String id) throws NotFoundException;

    List<T> search(String word , Pageable pageable);
}
