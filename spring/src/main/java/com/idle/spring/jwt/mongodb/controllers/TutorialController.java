package com.idle.spring.jwt.mongodb.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idle.spring.jwt.mongodb.models.Tutorial;
import com.idle.spring.jwt.mongodb.repository.TutorialRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authority/all") // @RequestMapping("/api/authority/user") 사용자별 다른 API 접근 가능
public class TutorialController {

  @Autowired
  TutorialRepository tutorialRepository;

  @GetMapping("/tutorial")
  public ResponseEntity<Map<String, Object>> getAllTutorialsPage(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String description,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    try {
      List<Tutorial> tutorials = new ArrayList<Tutorial>();
      Pageable paging = PageRequest.of(page, size);
      
      Page<Tutorial> pageTuts;
      if (title == null)
        pageTuts = tutorialRepository.findAll(paging);
      else
        pageTuts = tutorialRepository.findTutorialByKeyword(title, description, paging);
        //pageTuts = tutorialRepository.findByTitleContainingIgnoreCase(title, paging);

      tutorials = pageTuts.getContent();

      Map<String, Object> response = new HashMap<>();
      response.put("tutorials", tutorials);
      response.put("currentPage", pageTuts.getNumber());
      response.put("totalItems", pageTuts.getTotalElements());
      response.put("totalPages", pageTuts.getTotalPages());

      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

//  @GetMapping("/tutorial/published")
//  public ResponseEntity<Map<String, Object>> findByPublished(
//      @RequestParam(defaultValue = "0") int page,
//      @RequestParam(defaultValue = "3") int size) {
//
//    try {
//      List<Tutorial> tutorials = new ArrayList<Tutorial>();
//      Pageable paging = PageRequest.of(page, size);
//
//      Page<Tutorial> pageTuts = tutorialRepository.findByPublished(true, paging);
//      tutorials = pageTuts.getContent();
//
//      if (tutorials.isEmpty()) {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//      }
//
//      Map<String, Object> response = new HashMap<>();
//      response.put("tutorials", tutorials);
//      response.put("currentPage", pageTuts.getNumber());
//      response.put("totalItems", pageTuts.getTotalElements());
//      response.put("totalPages", pageTuts.getTotalPages());
//
//      return new ResponseEntity<>(response, HttpStatus.OK);
//    } catch (Exception e) {
//      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }

  @GetMapping("/tutorial/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") String id) {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

    if (tutorialData.isPresent()) {
      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/tutorial")
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
    try {
      Tutorial _tutorial = tutorialRepository.save(
              new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.getImg()));
      return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/tutorial/{id}")
  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") String id, @RequestBody Tutorial tutorial) {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

    if (tutorialData.isPresent()) {
      Tutorial _tutorial = tutorialData.get();
      _tutorial.setTitle(tutorial.getTitle());
      _tutorial.setDescription(tutorial.getDescription());
      return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/tutorial/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") String id) {
    try {
      tutorialRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/tutorial")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      tutorialRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
}
