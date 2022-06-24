package com.idle.spring.jwt.mongodb.controllers;

import com.idle.spring.jwt.mongodb.models.Match;
import com.idle.spring.jwt.mongodb.repository.MatchUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authority/user")
public class MatchUserController {

  @Autowired
  MatchUserRepository matchUserRepository;

  @PostMapping("/match")
  public ResponseEntity<Match> createTutorial(@RequestBody Match match) {
    try {
      Match _match = matchUserRepository.save(
              new Match(
                      match.getWriter(),
                      match.getTitle(), match.getImg(), match.getDescription(),
                      match.getSymptoms(), match.getPredict(),
                      match.getResultimg(), match.getResult()));
      return new ResponseEntity<>(_match, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/match/{id}")
  public ResponseEntity<Match> getTutorialById(@PathVariable("id") String id) {
    Optional<Match> matchData = matchUserRepository.findById(id);

    if (matchData.isPresent()) {
      return new ResponseEntity<>(matchData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/match/{id}")
  public ResponseEntity<Match> updateTutorial(@PathVariable("id") String id, @RequestBody Match match) {
    Optional<Match> matchData = matchUserRepository.findById(id);

    if (matchData.isPresent()) {
      Match _match = matchData.get();
      _match.setTitle(match.getTitle());
      _match.setDescription(match.getDescription());
      _match.setResult(match.getResult());
      _match.setResultimg(match.getResultimg());
      return new ResponseEntity<>(matchUserRepository.save(_match), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/match/{id}")
  public ResponseEntity<HttpStatus> deleteMatch(@PathVariable("id") String id) {
    try {
      matchUserRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/match")
  public ResponseEntity<HttpStatus> deleteAllMatches() {
    try {
      matchUserRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
