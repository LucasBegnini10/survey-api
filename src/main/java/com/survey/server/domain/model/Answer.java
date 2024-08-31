package com.survey.server.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
public class Answer {
    private String id;
    private String value;
    private String fieldId;

    public Answer(String id, String value, String fieldId) {
        this.id = id;
        this.value = value;
        this.fieldId = fieldId;

    }
}
