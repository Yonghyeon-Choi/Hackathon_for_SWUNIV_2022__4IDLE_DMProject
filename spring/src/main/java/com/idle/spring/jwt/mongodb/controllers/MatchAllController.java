package com.idle.spring.jwt.mongodb.controllers;

import com.idle.spring.jwt.mongodb.models.Match;
import com.idle.spring.jwt.mongodb.repository.MatchAllRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authority/all")
public class MatchAllController {

  @Autowired
  MatchAllRepository matchAllRepository;

  @GetMapping("/match")
  public ResponseEntity<List<Match>> getAllMatches(
          @RequestParam(required = false) String word) {
    try {
      List<Match> matches = new ArrayList<Match>();

      if (word == null)
        matches = matchAllRepository.findAll();
      else
        matches = matchAllRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByTitle
                (word, word);

      if (matches.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(matches, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/match/{id}")
  public ResponseEntity<Match> getTutorialById(@PathVariable("id") String id) {
    Optional<Match> matchData = matchAllRepository.findById(id);

    if (matchData.isPresent()) {
      return new ResponseEntity<>(matchData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
