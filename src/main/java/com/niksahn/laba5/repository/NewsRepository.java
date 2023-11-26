package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.dto.NewsDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends CrudRepository<NewsDto,Long> {
}