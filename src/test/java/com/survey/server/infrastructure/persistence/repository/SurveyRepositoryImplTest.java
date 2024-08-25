package com.survey.server.infrastructure.persistence.repository;

import com.survey.server.domain.model.Survey;
import com.survey.server.infrastructure.persistence.model.SurveyModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SurveyRepositoryImplTest {

    @Mock
    private MongoTemplate mongo;

    @InjectMocks
    private SurveyRepositoryImpl repository;

    @Test
    public void should_ReturnSurveySaved() {
        Survey survey =
                new Survey(
                        "id",
                        "name",
                        "description",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null,
                        false,
                        List.of()
                );
        SurveyModel surveyModel = new SurveyModel(survey);
        when(mongo.save(surveyModel)).thenReturn(surveyModel);

        Survey result = repository.save(survey);

        assertNotNull(result);
        verify(mongo, times(1)).save(surveyModel);
    }

    @Test
    public void should_ReturnSurveyById() {
        Survey survey =
                new Survey(
                        "id",
                        "name",
                        "description",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null,
                        false,
                        List.of()
                );

        SurveyModel surveyModel = new SurveyModel(survey);

        when(mongo.find(any(), any())).thenReturn(List.of(surveyModel));

        Survey result = repository.findById("id").orElse(null);

        assertNotNull(result);
        assertEquals(result, survey);
    }

    @Test
    public void should_ReturnAllSurveys() {
        Survey survey =
                new Survey(
                        "id",
                        "name",
                        "description",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null,
                        false,
                        List.of()
                );

        SurveyModel surveyModel = new SurveyModel(survey);
        when(mongo.find(any(), any())).thenReturn(List.of(surveyModel));

        List<Survey> result = repository.findAll();

        assertNotNull(result);
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), survey);
    }

    @Test
    public void should_DeleteSurveyById() {
        Survey survey =
                new Survey(
                        "id",
                        "name",
                        "description",
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null,
                        false,
                        List.of()
                );
        SurveyModel surveyModel = new SurveyModel(survey);
        when(mongo.findById("id", SurveyModel.class)).thenReturn(surveyModel);

        repository.deleteById("id");

        verify(mongo, times(1)).findById("id", SurveyModel.class);
        verify(mongo, times(1)).save(surveyModel);
    }
}
