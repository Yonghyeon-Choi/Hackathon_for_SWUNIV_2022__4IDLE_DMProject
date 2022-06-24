package com.idle.spring.jwt.mongodb.repository;

import com.idle.spring.jwt.mongodb.models.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchUserRepository extends MongoRepository<Match, String> {
//  Page<Tutorial> findByPublished(boolean published, Pageable pageable);
//
//  Page<Match> findByTitleContainingIgnoreCase(String title, Pageable pageable);

  //전체 검색 (wrCode 카테고리 없을경우)
//  @Query(value="{$or:[ " +
//          "{title: {$regex: ?0, $options: 'i'}},"+
//          "{description: {$regex: ?1, $options: 'i'}}," +
//          "]}", sort="{title: 1}")
//  Page<Match> findTutorialByKeyword
//  (String title, String description, Pageable pageable);

//  위 쿼리와 동일한 쿼리 문법
  Page<Match> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByTitle
          (String title, String description, Pageable pageable);
}
