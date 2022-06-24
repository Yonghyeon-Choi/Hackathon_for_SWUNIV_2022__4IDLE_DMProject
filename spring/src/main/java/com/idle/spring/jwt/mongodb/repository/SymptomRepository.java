package com.idle.spring.jwt.mongodb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.idle.spring.jwt.mongodb.models.Symptom;
import org.springframework.data.mongodb.repository.Query;

public interface SymptomRepository extends MongoRepository<Symptom, String> {
//  Page<Tutorial> findByPublished(boolean published, Pageable pageable);
//
//  Page<Tutorial> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    //전체 검색 (wrCode 카테고리 없을경우)
    @Query(value="{$or:[ " +
            "{symptomid: {$regex: ?0, $options: 'i'}},"+
            "{symptomname: {$regex: ?1, $options: 'i'}}," +
            "]}", sort="{symptomid: 1}")
    Page<Symptom> findSymptomByKeyword
    (String symptomid, String symptomname, Pageable pageable);

    //위 쿼리와 동일한 쿼리 문법
//  Page<Tutorial> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByTitle
//          (String title, String description);
}
