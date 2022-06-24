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

import com.idle.spring.jwt.mongodb.models.Disease;
import com.idle.spring.jwt.mongodb.repository.DiseaseRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authority/admin") // @RequestMapping("/api/authority/user") 사용자별 다른 API 접근 가능
public class DiseaseController {

    @Autowired
    DiseaseRepository diseaseRepository;

    @GetMapping("/disease")
    public ResponseEntity<Map<String, Object>> getAllDiseasePage(
            @RequestParam(required = false) String diseaseid,
            @RequestParam(required = false) String disasename,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            List<Disease> diseases = new ArrayList<Disease>();
            Pageable paging = PageRequest.of(page, size);

            Page<Disease> pageTuts;
            if (diseaseid == null)
                pageTuts = diseaseRepository.findAll(paging);
            else
                pageTuts = diseaseRepository.findDiseaseByKeyword(diseaseid, disasename, paging);
            //pageTuts = diseaseRepository.findByDiseaseContainingIgnoreCase(disasename, paging);

            diseases = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("diseases", diseases);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/disease/{id}")
    public ResponseEntity<Disease> getDiseaseById(@PathVariable("id") String id) {
        Optional<Disease> diseaseData = diseaseRepository.findById(id);

        if (diseaseData.isPresent()) {
            return new ResponseEntity<>(diseaseData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/disease")
    public ResponseEntity<Disease> createDisease(@RequestBody Disease disease) {
        try {
            Disease _disease = diseaseRepository.save(new Disease(disease.getDiseaseid(),disease.getDiseasename(),disease.getSymptoms()));
            return new ResponseEntity<>(_disease, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/disease/{id}")
    public ResponseEntity<Disease> updateDisease(@PathVariable("id") String id, @RequestBody Disease disease) {
        Optional<Disease> diseaseData = diseaseRepository.findById(id);

        if (diseaseData.isPresent()) {
            Disease _disease = diseaseData.get();
            _disease.setDiseaseid(disease.getDiseaseid());
            _disease.setDiseasename(disease.getDiseasename());
            _disease.setSymptoms(disease.getSymptoms());
            return new ResponseEntity<>(diseaseRepository.save(_disease), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/disease/{id}")
    public ResponseEntity<HttpStatus> deleteDisease(@PathVariable("id") String id) {
        try {
            diseaseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/disease")
    public ResponseEntity<HttpStatus> deleteAllDiseases() {
        try {
            diseaseRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}