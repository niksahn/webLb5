package com.niksahn.laba5.repository;

import com.niksahn.laba5.model.dto.ImageNewsDto;
import com.niksahn.laba5.model.dto.UserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ImagesRepository extends CrudRepository<ImageNewsDto, Long> {

    @Query(value = "SELECT * FROM image_news  WHERE new_id = ?1", nativeQuery = true)
    ArrayList<ImageNewsDto> findByNewsId(Long new_id);
}
