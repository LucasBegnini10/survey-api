package com.survey.server.adapters.web.controller.v1;

import com.survey.server.adapters.web.dto.AnswersDTO;
import com.survey.server.adapters.web.dto.SurveyDTO;
import com.survey.server.domain.model.Survey;
import com.survey.server.domain.service.SurveyService;
import com.survey.server.infrastructure.queue.publisher.AnswerPublisher;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/surveys")
@AllArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;
    private AnswerPublisher answerPublisher;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Survey> getSurveys() {
        return surveyService.getAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Survey getSurvey(@PathVariable String id) {
        return surveyService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Survey createSurvey(@Valid @RequestBody SurveyDTO survey) {
        return surveyService.create(survey.toDomain());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSurvey(@PathVariable String id) {
        surveyService.delete(id);
    }

    @PostMapping("{id}/answers")
    @ResponseStatus(HttpStatus.CREATED)
    public void answerSurvey(
            @NotBlank(message = "O id da pesquisa é obrigatório") @PathVariable String id,
            @Valid @RequestBody AnswersDTO answers) {
        answerPublisher.send(id, answers.toDomain());
    }
}
