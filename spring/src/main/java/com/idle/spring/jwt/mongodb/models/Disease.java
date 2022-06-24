package com.idle.spring.jwt.mongodb.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "diseases")
public class Disease {

    @Id
    private String id;

    private String diseaseid;
    private String diseasename;
    private Set<Symptom> symptoms = new HashSet<>();

    public Disease(){

    }

    public Disease(String diseaseid, String diseasename, Set<Symptom> symptoms){
        this.diseaseid = diseaseid;
        this.diseasename = diseasename;
        this.symptoms = symptoms;
    }

    public String getId() {
        return id;
    }

    public String getDiseasename() {
        return diseasename;
    }

    public void setDiseasename(String diseasename) {
        this.diseasename = diseasename;
    }

    public String getDiseaseid() {
        return diseaseid;
    }

    public void setDiseaseid(String diseaseid) {
        this.diseaseid = diseaseid;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }
}
