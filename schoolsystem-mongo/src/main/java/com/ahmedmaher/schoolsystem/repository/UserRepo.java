package com.ahmedmaher.schoolsystem.repository;

import com.ahmedmaher.schoolsystem.document.UserDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface UserRepo extends MongoRepository<UserDoc, String> {
    UserDoc findByUsername(String username);
    UserDoc findByEmail(String email);
    UserDoc findByPasswordResetToken(String passwordResetToken);
    @Query("{ $text: { $search: ?0 }}")
    List<UserDoc> searchByName(String name, Pageable pageable);
}
