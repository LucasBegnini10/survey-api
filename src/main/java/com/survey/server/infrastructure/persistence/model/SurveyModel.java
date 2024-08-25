package com.survey.server.infrastructure.persistence.model;

import com.survey.server.domain.model.Survey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("surveys")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SurveyModel {

    @Id
    private String _id;

    private String name;
    private String description;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private LocalDateTime deleted_at;
    private Boolean is_deleted;
    private List<FieldModel> fields;

    public SurveyModel(Survey survey){
        this._id = survey.getId();
        this.name = survey.getName();
        this.description = survey.getDescription();
        this.created_at = survey.getCreatedAt();
        this.updated_at = survey.getUpdatedAt();
        this.deleted_at = survey.getDeletedAt();
        this.is_deleted = survey.getIsDeleted();
        this.fields = FieldModel.fromDomain(survey.getFields());
    }

    public Survey toDomain(){
        return new Survey(
                this._id,
                this.name,
                this.description,
                this.created_at,
                this.updated_at,
                this.deleted_at,
                this.is_deleted,
                FieldModel.toDomain(this.fields)
        );
    }
}
