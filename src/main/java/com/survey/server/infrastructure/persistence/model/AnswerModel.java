package com.survey.server.infrastructure.persistence.model;

import com.survey.server.domain.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Document("answers")
@NoArgsConstructor
@AllArgsConstructor
public class AnswerModel {
    private String _id;
    private String value;

    public AnswerModel(AnswerModel answer){
        this._id = answer.get_id();
        this.value = answer.getValue();
    }

    public AnswerModel(Answer answer){
        this._id = answer.getId();
        this.value = answer.getValue();
    }

    public static List<AnswerModel> fromDomain(List<Answer> answers){
        if(answers == null) return List.of();
        return answers.stream().map(AnswerModel::new).collect(Collectors.toList());
    }

    public static Answer toDomain(AnswerModel answer){
        return new Answer(
                answer._id,
                answer.value
        );
    }

    public static List<Answer> toDomain(List<AnswerModel> answers){
        return answers.stream().map(AnswerModel::toDomain).collect(Collectors.toList());
    }
}
