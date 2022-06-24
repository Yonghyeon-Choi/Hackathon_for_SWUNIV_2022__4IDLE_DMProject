package com.idle.spring.jwt.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "matches")
public class Match {

    @Id
    private String id;      // 몽고DB 아이디

    private String writer; // 작성자 ID

    private String title; // 작성 글 제목 (단어 or 문장
    private String img; // 작성 글 이미지
    private String description; // 작성 글 설명 (문장)

    private Set<Symptom> symptoms = new HashSet<>(); // 작성된 증상들 및 가중치
    private Set<String> predict = new HashSet<>();  // 작성된 증상들 및 가중치 기반 예측 질병 (Top3)

    private String resultimg; // 작성 글 진단 결과 사진
    private String result; // 작성 글 진단 결과 (단어)

    public Match(String writer,
                 String title, String img, String description,
                 Set<Symptom> symptoms, Set<String> predict,
                 String resultimg, String result){
        this.writer = writer;
        this.title = title;
        this.img = img;
        this.description = description;
        this.symptoms = symptoms;
        this.predict = predict;
        this.resultimg = resultimg;
        this.result = result;
    }

    public Match(){

    }

    public String getId() {
        return id;
    }


    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<String> getPredict() {
        return predict;
    }

    public void setPredict(Set<String> predict) {
        this.predict = predict;
    }

    public String getResultimg() {
        return resultimg;
    }

    public void setResultimg(String resultimg) {
        this.resultimg = resultimg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}





