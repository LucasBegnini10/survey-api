package com.survey.server.domain.exception;

public class SurveyNotFoundException extends RuntimeException {

    public SurveyNotFoundException(){
        super("Pesquisa não encontrada");
    }
}
