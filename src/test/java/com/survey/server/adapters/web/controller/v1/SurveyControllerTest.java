package com.survey.server.adapters.web.controller.v1;

import com.survey.server.adapters.web.dto.AnswerDTO;
import com.survey.server.adapters.web.dto.AnswersDTO;
import com.survey.server.adapters.web.dto.SurveyDTO;
import com.survey.server.domain.model.Survey;
import com.survey.server.domain.service.SurveyService;
import com.survey.server.infrastructure.queue.publisher.AnswerPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SurveyControllerTest {

    @Mock
    private SurveyService surveyService;

    @Mock
    private AnswerPublisher publisher;

    @InjectMocks
    private SurveyController surveyController;

    @Test
    public void should_ReturnListOfSurveys(){
        List<Survey> expected = List.of(
                new Survey(
                        "id",
                        "name",
                        "description",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null,
                        false,
                        List.of(),
                        List.of()
                )
        );

        when(surveyService.getAll()).thenReturn(expected);

        List<Survey> received = surveyController.getSurveys();

        assertEquals(expected, received);
    }

    @Test
    public void should_ReturnSurvey(){
        Survey expected = new Survey(
                "id",
                "name",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                false,
                List.of(),
                List.of()
        );

        when(surveyService.get("id")).thenReturn(expected);

        Survey received = surveyController.getSurvey("id");

        assertEquals(expected, received);
    }

    @Test
    public void should_CreateSurvey(){
        SurveyDTO expected = new SurveyDTO(
                "name",
                "description",
                List.of()
        );

        when(surveyService.create(expected.toDomain())).thenReturn(expected.toDomain());

        Survey received = surveyController.createSurvey(expected);

        assertEquals(expected.toDomain(), received);
    }

    @Test
    public void should_DeleteSurvey(){
        surveyController.deleteSurvey("id");
        verify(surveyService, times(1)).delete("id");
    }

    @Test
    public void should_AnswerSurvey(){
        AnswersDTO answersDTO = new AnswersDTO(List.of(new AnswerDTO("questionId", "answer")));

        surveyController.answerSurvey("id", answersDTO);

        verify(publisher, times(1)).send("id", answersDTO.toDomain());
    }
}
