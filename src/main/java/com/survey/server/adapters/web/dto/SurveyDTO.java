package com.survey.server.adapters.web.dto;

import com.survey.server.domain.model.Survey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDTO {
    @NotBlank(message = "O nome é obrigatório")
    private String name;

    private String description;

    @NotEmpty(message = "A lista não pode estar vazia")
    @Valid
    private List<@Valid FieldDTO> fields;

    public Survey toDomain(){
        Survey survey = new Survey();
        survey.setName(this.name);
        survey.setDescription(this.description);
        survey.setFields(FieldDTO.toDomain(this.fields));
        return survey;
    }
}
