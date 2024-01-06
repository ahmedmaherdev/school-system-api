package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.document.SchoolDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SchoolRepository extends MongoRepository<SchoolDocument, String> {
    @Query("{ $text: { $search: ?0 }}")
    List<SchoolDocument> searchByName(String name, Pageable pageable);

}
