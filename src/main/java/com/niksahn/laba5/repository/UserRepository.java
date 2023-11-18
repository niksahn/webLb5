package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.UserDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<UserDto,Long>
{
   // @Query("SELECT u FROM users u WHERE u.login = ?1")
    UserDto findByLogin(String login);
   // List<User> findAllByLogin(String login);
}