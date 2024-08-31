package com.survey.server.infrastructure.queue.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.server.domain.model.Answer;
import com.survey.server.infrastructure.queue.model.AnswerQueueModel;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AnswerPublisherImpl implements AnswerPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    public void send(String id, List<Answer> answers) {
        AnswerQueueModel model = new AnswerQueueModel(id, answers);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(model);
            rabbitTemplate.convertAndSend(this.queue.getName(), json);
        }
        catch (Exception ex){
            throw new RuntimeException("Erro ao enviar resposta para fila", ex);
        }
    }
}
