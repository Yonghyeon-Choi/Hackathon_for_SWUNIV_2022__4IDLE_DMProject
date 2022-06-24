package com.idle.spring.jwt.mongodb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.idle.spring.jwt.mongodb.models.Tutorial;
import org.springframework.data.mongodb.repository.Query;

public interface TutorialRepository extends MongoRepository<Tutorial, String> {
//  Page<Tutorial> findByPublished(boolean published, Pageable pageable);
//
//  Page<Tutorial> findByTitleContainingIgnoreCase(String title, Pageable pageable);

  //전체 검색 (wrCode 카테고리 없을경우)
  @Query(value="{$or:[ " +
          "{title: {$regex: ?0, $options: 'i'}},"+
          "{description: {$regex: ?1, $options: 'i'}}," +
          "]}", sort="{title: 1}")
  Page<Tutorial> findTutorialByKeyword
  (String title, String description, Pageable pageable);

  //위 쿼리와 동일한 쿼리 문법
//  Page<Tutorial> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByTitle
//          (String title, String description);
}
