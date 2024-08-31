package com.survey.server.infrastructure.persistence.queue.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.server.domain.model.Answer;
import com.survey.server.infrastructure.queue.model.AnswerQueueModel;
import com.survey.server.infrastructure.queue.publisher.AnswerPublisherImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AnswerPublisherImplTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private Queue queue;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AnswerPublisherImpl publisher;

    @Test
    public void should_SendMessageToQueue_When_ValidParams() throws JsonProcessingException {
        List<Answer> answers = List.of(new Answer("id1", "field1", "response1"));
        AnswerQueueModel model = new AnswerQueueModel("id", answers);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(model);

        publisher.send("id", answers);

        verify(rabbitTemplate).convertAndSend(queue.getName(), json);
    }

    @Test
    public void  should_ThrowException_When_TryConvertAndSend() throws JsonProcessingException {
        List<Answer> answers = List.of(new Answer("id1", "field1", "response1"));

        doThrow(new AmqpException("Erro ao enviar resposta para fila"))
                .when(rabbitTemplate).convertAndSend(anyString(), anyString());

        assertThrows(RuntimeException.class, () -> publisher.send("id", answers));
    }
}
