package com.survey.server.domain.model;

import lombok.Data;

@Data
public class Answer {
    private String id;
    private String value;

    public Answer(){

    }

    public Answer(String id, String value){
        this.id = id;
        this.value = value;
    }
}
