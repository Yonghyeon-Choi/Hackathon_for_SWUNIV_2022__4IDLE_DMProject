package com.idle.spring.jwt.mongodb.controllers;

import com.idle.spring.jwt.mongodb.models.Disease;
import com.idle.spring.jwt.mongodb.repository.DiseaseRepository;
import com.idle.spring.jwt.mongodb.models.Symptom;
import com.idle.spring.jwt.mongodb.repository.SymptomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/authority/all") // @RequestMapping("/api/authority/user") 사용자별 다른 API 접근 가능
public class DefaultDataController {
    @Autowired
    DiseaseRepository diseaseRepository;

    @Autowired
    SymptomRepository symptomRepository;

    @GetMapping("/defaultdata")
    public ResponseEntity<Map<String, Object>> getAllData() {

        try {
            List<Disease> diseases = new ArrayList<Disease>();
            diseases = diseaseRepository.findAll();

            List<Symptom> symptoms = new ArrayList<Symptom>();
            symptoms = symptomRepository.findAll();

            Map<String, Object> response = new HashMap<>();
            response.put("diseases", diseases);
            response.put("symptoms", symptoms);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
