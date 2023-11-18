package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.SessionDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<SessionDto, Long> {
   // SessionDto findAllByUserId(Long user_id);

    @Modifying
    @Query(value = "delete from session s where s.user_id=:user_id and :time - s.creation_time > :session_life", nativeQuery = true)
    Void deleteOutdates(@Param("user_id") Long user_id, @Param("time") Long time, @Param("session_life") Long session_life);
}

