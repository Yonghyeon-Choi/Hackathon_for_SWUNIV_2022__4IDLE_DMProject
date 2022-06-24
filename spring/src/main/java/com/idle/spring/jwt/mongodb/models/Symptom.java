package com.idle.spring.jwt.mongodb.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "symptoms")
public class Symptom {

    @Id
    private String id;

    private String symptomid;
    private String symptomname;
    private int weight;
    // private String weight;


    public Symptom(){

    }

    public Symptom(String symptomid, String symptomname, int weight){
        this.symptomid = symptomid;
        this.symptomname = symptomname;
        this.weight = weight;
    }


    public void setSymptomname(String symptom) {
        this.symptomname = symptom;
    }

    public void setSymptomid(String symptomid) {
        this.symptomid = symptomid;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public String getSymptomname() {
        return symptomname;
    }

    public String getSymptomid() {
        return symptomid;
    }

    public int getWeight() {
        return weight;
    }
}
