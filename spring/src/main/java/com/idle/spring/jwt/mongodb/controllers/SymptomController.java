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

import com.idle.spring.jwt.mongodb.models.Symptom;
import com.idle.spring.jwt.mongodb.repository.SymptomRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authority/admin") // @RequestMapping("/api/authority/user") 사용자별 다른 API 접근 가능
public class SymptomController {

    @Autowired
    SymptomRepository symptomRepository;

    @GetMapping("/symptom")
    public ResponseEntity<Map<String, Object>> getAllSymptomsPage(
            @RequestParam(required = false) String symptomid,
            @RequestParam(required = false) String symptomname,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            List<Symptom> symptoms = new ArrayList<Symptom>();
            Pageable paging = PageRequest.of(page, size);

            Page<Symptom> pageTuts;
            if (symptomid == null)
                pageTuts = symptomRepository.findAll(paging);
            else
                pageTuts = symptomRepository.findSymptomByKeyword(symptomid, symptomname, paging);
            //pageTuts = symptomRepository.findBySymptomContainingIgnoreCase(symptomname, paging);

            symptoms = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("symptoms", symptoms);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/symptom/{id}")
    public ResponseEntity<Symptom> getSymptomById(@PathVariable("id") String id) {
        Optional<Symptom> symptomData = symptomRepository.findById(id);

        if (symptomData.isPresent()) {
            return new ResponseEntity<>(symptomData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/symptom")
    public ResponseEntity<Symptom> createSymptom(@RequestBody Symptom symptom) {
        try {
            Symptom _symptom = symptomRepository.save(new Symptom(symptom.getSymptomid(),symptom.getSymptomname(),symptom.getWeight()));
            return new ResponseEntity<>(_symptom, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/symptom/{id}")
    public ResponseEntity<Symptom> updateSymptom(@PathVariable("id") String id, @RequestBody Symptom symptom) {
        Optional<Symptom> symptomData = symptomRepository.findById(id);

        if (symptomData.isPresent()) {
            Symptom _symptom = symptomData.get();
            _symptom.setSymptomid(symptom.getSymptomid());
            _symptom.setSymptomname(symptom.getSymptomname());
            return new ResponseEntity<>(symptomRepository.save(_symptom), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/symptom/{id}")
    public ResponseEntity<HttpStatus> deleteSymptom(@PathVariable("id") String id) {
        try {
            symptomRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/symptom")
    public ResponseEntity<HttpStatus> deleteAllSymptoms() {
        try {
            symptomRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}