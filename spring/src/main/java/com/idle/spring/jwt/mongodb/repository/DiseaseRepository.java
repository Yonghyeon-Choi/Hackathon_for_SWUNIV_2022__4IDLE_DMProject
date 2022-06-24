package com.idle.spring.jwt.mongodb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.idle.spring.jwt.mongodb.models.Disease;
import org.springframework.data.mongodb.repository.Query;

public interface DiseaseRepository extends MongoRepository<Disease, String> {
//  Page<Tutorial> findByPublished(boolean published, Pageable pageable);
//
//  Page<Tutorial> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    //전체 검색 (wrCode 카테고리 없을경우)
    @Query(value="{$or:[ " +
            "{diseaseid: {$regex: ?0, $options: 'i'}},"+
            "{diseasename: {$regex: ?1, $options: 'i'}}," +
            "]}", sort="{diseaseid: 1}")
    Page<Disease> findDiseaseByKeyword
    (String diseaseid, String diseasename, Pageable pageable);

    //위 쿼리와 동일한 쿼리 문법
//  Page<Tutorial> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByTitle
//          (String title, String description);
}
