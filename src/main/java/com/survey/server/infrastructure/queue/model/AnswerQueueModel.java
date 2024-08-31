package com.survey.server.infrastructure.queue.model;

import com.survey.server.domain.model.Answer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AnswerQueueModel {
    private String id;
    private List<Answer> answers;

    public AnswerQueueModel(String id, List<Answer> answers) {
        this.id = id;
        this.answers = answers;
    }
}
