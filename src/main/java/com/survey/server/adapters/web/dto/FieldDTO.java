package com.survey.server.adapters.web.dto;

import com.survey.server.adapters.web.validator.EnumValid;
import com.survey.server.domain.model.Field;
import com.survey.server.domain.model.FieldType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldDTO {

    @NotBlank(message = "O label é obrigatório")
    private String label;

    @EnumValid(enumClass = FieldType.class, message = "O tipo é inválido")
    private String type;

    private String helper;

    @NotNull(message = "O campo obrigatório é obrigatório")
    private Boolean required;

    public static Field toDomain(FieldDTO dto){
        Field field = new Field();
        field.setLabel(dto.label);
        field.setType(FieldType.valueOf(dto.type));
        field.setHelper(dto.helper);
        field.setRequired(dto.required);
        return field;
    }

    public static List<Field> toDomain(List<FieldDTO> dtos){
        return dtos.stream().map(FieldDTO::toDomain).toList();
    }
}
