package com.survey.server.adapters.web.dto;

import com.survey.server.domain.model.Answer;
import lombok.Data;

import java.util.List;

@Data
public class AnswerDTO {
    private String value;

    public static Answer toDomain(AnswerDTO answerDTO){
        Answer answer = new Answer();
        answer.setValue(answerDTO.value);
        return answer;
    }

    public static List<Answer> toDomain(List<AnswerDTO> answers){
        if(answers == null) return List.of();
        return answers.stream().map(AnswerDTO::toDomain).toList();
    }

}
