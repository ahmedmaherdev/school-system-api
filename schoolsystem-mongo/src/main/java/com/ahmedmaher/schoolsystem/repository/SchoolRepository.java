package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.document.SchoolDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SchoolRepository extends MongoRepository<SchoolDocument, String> {
    @Query("{ $text: { $search: ?0 }}")
    List<SchoolDocument> searchBy(String word, Pageable pageable);

    @Query("{id: ?0}")
    SchoolDocument findSchoolWithClassrooms(String schoolId);
}
