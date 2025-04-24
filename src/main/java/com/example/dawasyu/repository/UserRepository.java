package com.example.dawasyu.repository;

import com.example.dawasyu.domain.user.entity.User;
import com.example.dawasyu.domain.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    Optional<User> findUserById (Long userId);

    default User findUserByIdOrElseThrow(Long userId) {
        return findUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다."));
    }

    default User findUserByOnwerIdOrElseThrow(Long onwerId) {
        User user = findUserById(onwerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 id입니다."));

        if(!user.getUserRole().equals(UserRole.OWNER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"해당 권한은 OWNER만 가능합니다.");
        }
        return user;
    }





}
