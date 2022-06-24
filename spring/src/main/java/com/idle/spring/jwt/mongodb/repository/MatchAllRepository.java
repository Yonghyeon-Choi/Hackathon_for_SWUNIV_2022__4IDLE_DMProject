package com.idle.spring.jwt.mongodb.repository;

import com.idle.spring.jwt.mongodb.models.Match;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatchAllRepository extends MongoRepository<Match, String> {
    List<Match> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByTitle
            (String title, String description);
}
