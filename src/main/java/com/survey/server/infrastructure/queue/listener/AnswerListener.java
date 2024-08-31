package com.survey.server.infrastructure.queue.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.server.domain.service.SurveyService;
import com.survey.server.infrastructure.queue.model.AnswerQueueModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@Slf4j
public class AnswerListener {

    private final SurveyService surveyService;

    @RabbitListener(queues = {"${queue.answer.name}"})
    public void receive(@Payload String json) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            AnswerQueueModel answer = objectMapper.readValue(json, AnswerQueueModel.class);

            surveyService.saveAnswer(answer.getId(), answer.getAnswers());
        } catch (Exception exception){
            log.error("Erro ao receber mensagem: {}", exception.getMessage());
        }
    }

}
