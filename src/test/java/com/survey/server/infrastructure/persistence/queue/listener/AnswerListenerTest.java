package com.survey.server.infrastructure.persistence.queue.listener;

import ch.qos.logback.core.read.ListAppender;
import com.survey.server.domain.service.SurveyService;
import com.survey.server.infrastructure.queue.listener.AnswerListener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.verify;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.LoggerFactory;


@ExtendWith(MockitoExtension.class)
public class AnswerListenerTest {

    @Mock
    private SurveyService service;

    @InjectMocks
    private AnswerListener listener;

    private ListAppender<ILoggingEvent> logWatcher;

    @BeforeEach
    void setup() {
        logWatcher = new ListAppender<>();
        logWatcher.start();
        ((Logger) LoggerFactory.getLogger(AnswerListener.class)).addAppender(logWatcher);
    }

    @Test
    public void should_CallService_When_ReceiveMessage() {
        String json = "{}";

        listener.receive(json);

        verify(service).saveAnswer(any(), any());
    }

    @Test
    public void should_Log_When_ReceiveMessageWithError(){
        String json = "{}";

        doThrow(new RuntimeException("Erro ao receber mensagem")).when(service).saveAnswer(any(), any());

        listener.receive(json);

        assertEquals(1, logWatcher.list.size());
        assertTrue(logWatcher.list.get(0).getMessage().contains("Erro ao receber mensagem"));
    }
}
