package com.survey.server.domain.repository;

import com.survey.server.domain.model.Survey;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository {
    Survey save(Survey survey);
    Optional<Survey> findById(String id);
    List<Survey> findAll();
    void deleteById(String id);
}