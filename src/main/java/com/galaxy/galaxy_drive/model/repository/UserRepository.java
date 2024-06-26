package com.galaxy.galaxy_drive.model.repository;

import com.galaxy.galaxy_drive.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findByUserName(String username);

   boolean existsByUserName(String userName);
}
