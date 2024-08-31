package com.survey.server.domain.service;

import com.survey.server.domain.exception.SurveyNotFoundException;
import com.survey.server.domain.model.Answer;
import com.survey.server.domain.model.Field;
import com.survey.server.domain.model.FieldType;
import com.survey.server.domain.model.Survey;
import com.survey.server.domain.repository.SurveyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceImplTest {

    @Mock
    private SurveyRepository repository;

    @InjectMocks
    private SurveyServiceImpl service;

    @Test
    public void should_ReturnListOfSurvey() {
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

        when(repository.findAll()).thenReturn(expected);

        List<Survey> received = service.getAll();

        assertEquals(expected, received);
    }

    @Test
    public void should_ReturnSurvey() {
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

        when(repository.findById("id")).thenReturn(java.util.Optional.of(expected));

        Survey received = service.get("id");

        assertEquals(expected, received);
    }

    @Test
    public void should_ThrowsException_WhenSurveyNotFound() {
        when(repository.findById("id")).thenReturn(java.util.Optional.empty());

        assertThrows(SurveyNotFoundException.class, () -> service.get("id"));
    }

    @Test
    public void should_ReturnCreatedSurvey() {
        Survey expected = new Survey(
                "id",
                "name",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                false,
                List.of(
                        new Field(
                                "id-field",
                                "name",
                                FieldType.TEXT,
                                null,
                                false,
                                List.of()
                        )
                ),
                List.of()
        );

        Survey survey = new Survey(
                null,
                "name",
                "description",
                null,
                null,
                null,
                false,
                List.of(
                        new Field(
                                null,
                                "name",
                                FieldType.TEXT,
                                null,
                                false,
                                List.of()
                        )
                ),
                List.of()
        );

        when(repository.save(any(Survey.class))).thenReturn(expected);

        Survey received = service.create(survey);

        assertEquals(expected, received);
    }

    @Test
    public void should_DeleteSurvey() {
        service.delete("id");

        verify(repository).deleteById("id");
    }

    @Test
    public void should_SaveAnswer() {
        Survey survey = new Survey(
                "id",
                "name",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                false,
                List.of(
                        new Field(
                                "field-id",
                                "name",
                                FieldType.TEXT,
                                null,
                                false,
                                new ArrayList<>()
                        )
                ),
                null
        );

        List<Answer> answers = List.of(
                new Answer(null, "answer1", "field-id"),
                new Answer(null, "answer2", "field-id")
        );

        when(repository.findById(anyString())).thenReturn(Optional.of(survey));

        service.saveAnswer("id", answers);

        assertEquals(2, survey.getAnswers().size());
        assertEquals(2, survey.getFields().get(0).getAnswers().size());
        verify(repository).save(survey);
    }
}
