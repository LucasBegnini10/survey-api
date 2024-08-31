package com.survey.server.adapters.web.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.server.adapters.web.dto.AnswerDTO;
import com.survey.server.adapters.web.dto.AnswersDTO;
import com.survey.server.adapters.web.dto.FieldDTO;
import com.survey.server.adapters.web.dto.SurveyDTO;
import com.survey.server.domain.model.Field;
import com.survey.server.domain.model.FieldType;
import com.survey.server.domain.model.Survey;
import com.survey.server.domain.service.SurveyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SurveyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static String databaseName;

    @DynamicPropertySource
    static void setMongoDbProperties(DynamicPropertyRegistry registry) {
        databaseName = "test-db-" + UUID.randomUUID();
        registry.add("spring.data.mongodb.database", () -> databaseName);
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mongoTemplate.getDb().drop();
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
    }


    @Test
    void should_ReturnSurveys() throws Exception {
        Survey survey1 = new Survey(
                null,
                "Survey1",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                false,
                List.of(),
                List.of()
        );
        Survey survey2 = new Survey(
                null,
                "Survey2",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                false,
                List.of(),
                List.of()
        );

        surveyService.create(survey1);
        surveyService.create(survey2);

        mockMvc.perform(get("/v1/surveys")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void should_ReturnSurvey() throws Exception {
        Survey survey = new Survey(
                "id",
                "Survey1",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                false,
                List.of(),
                List.of()
        );
        Survey surveyCreated = surveyService.create(survey);

        mockMvc.perform(get("/v1/surveys/{id}", surveyCreated.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(survey.getName()));
    }

    @Test
    void should_ReturnNotFound() throws Exception {
        mockMvc.perform(get("/v1/surveys/{id}", "id-teste")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_CreateSurvey() throws Exception {
        SurveyDTO surveyDTO = new SurveyDTO(
                "name",
                "description",
                List.of(
                        new FieldDTO(
                                "label",
                                FieldType.TEXT.name(),
                                "helper",
                                true)
                )
        );

        mockMvc.perform(post("/v1/surveys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(surveyDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(surveyDTO.getName()));
    }

    @Test
    void should_ReturnBadRequest_WhenSendInvalidParams() throws Exception {
        SurveyDTO surveyDTO = new SurveyDTO(
                "name",
                "description",
                List.of(
                        new FieldDTO(
                                "label",
                                "invalid-param",
                                "helper",
                                true)
                )
        );

        mockMvc.perform(post("/v1/surveys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(surveyDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_DeleteSurvey() throws Exception {
        Survey survey = new Survey(
                "id",
                "Survey1",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                false,
                List.of(),
                List.of()
        );
        Survey surveyCreated = surveyService.create(survey);

        mockMvc.perform(delete("/v1/surveys/{id}", surveyCreated.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_AnswerSurvey() throws Exception {
        Survey survey = new Survey(
                "id",
                "Survey1",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null,
                false,
                List.of(
                        new Field(
                                null,
                                "name",
                                FieldType.TEXT,
                                null,
                                false,
                                new ArrayList<>()
                        )
                ),
                List.of()
        );

        Survey surveyCreated = surveyService.create(survey);

        AnswersDTO answersDTO = new AnswersDTO(
                List.of(
                        new AnswerDTO("value", surveyCreated.getFields().get(0).getId())
                )
        );


        mockMvc.perform(post("/v1/surveys/{id}/answers", surveyCreated.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answersDTO)))
                .andExpect(status().isCreated());

        // Async operation
        Thread.sleep(1000);

        mockMvc.perform(get("/v1/surveys/{id}", surveyCreated.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answers").isNotEmpty())
                .andExpect(jsonPath("$.answers").isArray())
                .andExpect(jsonPath("$.answers[0].value").value("value"))
                .andExpect(jsonPath("$.fields[0].answers[0].value").value("value"));
    }
}

