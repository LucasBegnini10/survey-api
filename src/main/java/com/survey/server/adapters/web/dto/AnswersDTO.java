package com.survey.server.adapters.web.dto;

import com.survey.server.domain.model.Answer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class AnswersDTO {
    @NotEmpty(message = "A lista não pode estar vazia")
    @Valid
    private List<@Valid AnswerDTO> answers;

    public AnswersDTO(List<AnswerDTO> answerDTOS) {
        this.answers = answerDTOS;
    }

    public List<Answer> toDomain(){
        return AnswerDTO.toDomain(answers);
    }
}
