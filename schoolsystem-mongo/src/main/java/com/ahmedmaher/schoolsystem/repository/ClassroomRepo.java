package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.document.ClassroomDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ClassroomRepo extends MongoRepository<ClassroomDoc, String> {

    List<ClassroomDoc> findBySchool(String school, Pageable pageable);

    @Query(value = "{school: ?0}" , count = true)
    long findCountBySchool(String school);

    @Query("{ $text: { $search: ?0 }}")
    List<ClassroomDoc> searchByName(String name, Pageable pageable);

}
