package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.dto.SessionDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<SessionDto, Long> {

    @Query(value = "select * from sessions where user_id = :user_id", nativeQuery = true)
    SessionDto findByUserId(Long user_id);

    @Modifying
    @Query(value = "delete from sessions  where :time - creation_time > :session_life", nativeQuery = true)
    Integer deleteOutdates(@Param("time") Long time, @Param("session_life") Long session_life);
}

