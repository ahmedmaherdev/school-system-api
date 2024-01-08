package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.document.ClassroomDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ClassroomRepository extends MongoRepository<ClassroomDocument, String> {

    List<ClassroomDocument> findBySchool(String school, Pageable pageable);

    @Query(value = "{school: ?0}" , count = true)
    long findCountBySchool(String school);

    @Query("{ $text: { $search: ?0 }}")
    List<ClassroomDocument> searchByName(String name, Pageable pageable);

}
