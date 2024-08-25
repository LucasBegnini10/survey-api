package com.survey.server.infrastructure.persistence.model;

import com.survey.server.domain.model.Field;
import com.survey.server.domain.model.FieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Document("fields")
@NoArgsConstructor
@AllArgsConstructor
public class FieldModel {
    private String _id;
    private String label;
    private FieldType type;
    private String helper;
    private Boolean required;
    private List<AnswerModel> answer;

    public FieldModel(Field field){
        this._id = field.getId();
        this.label = field.getLabel();
        this.type = field.getType();
        this.helper = field.getHelper();
        this.required = field.getRequired();
        this.answer = AnswerModel.fromDomain(field.getAnswer());
    }

    public static List<FieldModel> fromDomain(List<Field> fields){
        return fields.stream().map(FieldModel::new).collect(Collectors.toList());
    }

    public static Field toDomain(FieldModel field){
        return new Field(
                field._id,
                field.label,
                field.type,
                field.helper,
                field.required,
                AnswerModel.toDomain(field.answer)
        );
    }

    public static List<Field> toDomain(List<FieldModel> fields){
        return fields.stream().map(FieldModel::toDomain).collect(Collectors.toList());
    }
}
