package com.survey.server.adapters.web.dto;

import com.survey.server.domain.model.Answer;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AnswerDTO {

    public AnswerDTO(String value, String fieldId) {
        this.value = value;
        this.fieldId = fieldId;
    }

    @NotBlank(message = "O valor da resposta é obrigatório")
    private String value;

    @NotBlank(message = "O id do campo é obrigatório")
    private String fieldId;


    public static Answer toDomain(AnswerDTO answerDTO){
        return new Answer(null, answerDTO.getValue(), answerDTO.getFieldId());
    }

    public static List<Answer> toDomain(List<AnswerDTO> answers){
        return answers.stream().map(AnswerDTO::toDomain).toList();
    }
}
