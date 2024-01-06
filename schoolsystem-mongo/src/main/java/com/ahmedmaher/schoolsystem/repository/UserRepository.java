package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.document.UserDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<UserDocument, String> {

    UserDocument findByUsername(String username);

    UserDocument findByEmail(String email);

    UserDocument findByPasswordResetToken(String passwordResetToken);
    @Query("{ $text: { $search: ?0 }}")
    List<UserDocument> searchByName(String name, Pageable pageable);

}
