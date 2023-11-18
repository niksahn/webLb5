package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User,Long>
{
    User findByLogin(String login);
    List<User> findAllByLogin(String login);
}