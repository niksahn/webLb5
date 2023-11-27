package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.dto.UserDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<UserDto, Long> {
    @Query(value = "SELECT * FROM user  WHERE login = ?1", nativeQuery = true)
    UserDto findByLogin(String login);

    @Query(value = "SELECT * FROM user  WHERE id = ?1", nativeQuery = true)
    UserDto findByUserId(Long id);
    // List<User> findAllByLogin(String login);
}