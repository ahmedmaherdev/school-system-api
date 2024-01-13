package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.document.SchoolDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SchoolRepo extends MongoRepository<SchoolDoc, String> {
    @Query("{ $text: { $search: ?0 }}")
    List<SchoolDoc> searchByName(String name, Pageable pageable);

}
