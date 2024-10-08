package com.survey.server.domain.service;


import com.survey.server.domain.exception.SurveyNotFoundException;
import com.survey.server.domain.model.Answer;
import com.survey.server.domain.model.Survey;
import com.survey.server.domain.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository repository;

    @Override
    public List<Survey> getAll() {
        return repository.findAll();
    }

    @Override
    public Survey get(String id) {
        return repository.findById(id).orElseThrow(SurveyNotFoundException::new);
    }

    @Override
    public Survey create(Survey survey) {
        return repository.save(
                survey.withId(UUID.randomUUID().toString())
                        .withCreatedAt(LocalDateTime.now())
                        .withUpdatedAt(LocalDateTime.now())
                        .withIsDeleted(false)
                        .withFields(survey.getFields().stream().map(field -> field
                                .withId(UUID.randomUUID().toString())).toList())
                        .withAnswers(List.of())
        );
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void saveAnswer(String id, List<Answer> answers) {
        Survey survey = get(id);

        List<Answer> answersWithId = answers.stream()
                .map(a -> a.withId(UUID.randomUUID().toString()))
                .toList();

        if (survey.getAnswers() == null) {
            survey.setAnswers(new ArrayList<>());
        }

        survey.getAnswers().addAll(answersWithId);

        survey.getFields().forEach(field ->
                answersWithId.stream()
                        .filter(answer -> field.getId().equals(answer.getFieldId()))
                        .forEach(field.getAnswers()::add)
        );

        repository.save(survey);
    }
}
