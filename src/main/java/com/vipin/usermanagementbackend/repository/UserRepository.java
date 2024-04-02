package com.vipin.usermanagementbackend.repository;

import com.vipin.usermanagementbackend.entity.Roles;
import com.vipin.usermanagementbackend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    List<User> findByRoleNot(Roles roles);

    boolean existsById(String id);

    Optional<User> findById(String id);

    boolean existsByUsername(String username);

    void deleteById(String id);

    User findByUsername(String username);
}
