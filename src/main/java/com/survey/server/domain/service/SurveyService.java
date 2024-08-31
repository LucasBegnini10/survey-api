package com.survey.server.domain.service;

import com.survey.server.domain.model.Answer;
import com.survey.server.domain.model.Survey;

import java.util.List;

public interface SurveyService {
    List<Survey> getAll();
    Survey get(String id);
    Survey create(Survey survey);
    void delete(String id);
    void saveAnswer(String id, List<Answer> answer);
}
