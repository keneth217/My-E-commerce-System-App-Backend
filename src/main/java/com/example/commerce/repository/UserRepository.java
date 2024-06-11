package com.example.commerce.repository;

import com.example.commerce.entity.User;
import com.example.commerce.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findFirstByPhone(String phone);
    User findByRole(Role role);
    @Query("SELECT u FROM User u WHERE u.role IN :roles")
    List<User> findAllByRole(@Param("roles") List<Role> roles);


}
