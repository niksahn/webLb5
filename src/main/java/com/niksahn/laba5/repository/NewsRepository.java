package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.dto.NewsDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends CrudRepository<NewsDto,Long> {

    @Modifying
    @Query(value = "delete from news where  user_id = :user_id", nativeQuery = true)
    Void dellByUserId(Long user_id);
}