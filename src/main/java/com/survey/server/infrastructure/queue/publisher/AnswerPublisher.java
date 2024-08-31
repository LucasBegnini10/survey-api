package com.survey.server.infrastructure.queue.publisher;

import com.survey.server.domain.model.Answer;

import java.util.List;

public interface AnswerPublisher {
    void send(String id, List<Answer> answers);
}
