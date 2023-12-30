package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.document.UserDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<UserDocument, String> {

    @Query("{username: ?0}")
    UserDocument findByUsername(String username);

    @Query("{email: ?0}")
    UserDocument findByEmail(String email);

    @Query("{ $text: { $search: ?0 }}")
    List<UserDocument> searchBy(String word, Pageable pageable);

}
