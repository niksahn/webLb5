package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.dao.UserDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<UserDto,Long>
{
  // @Query(value = "SELECT * FROM users  WHERE login = ?1", nativeQuery = true)
    UserDto findByLogin(String login);
   // List<User> findAllByLogin(String login);
}