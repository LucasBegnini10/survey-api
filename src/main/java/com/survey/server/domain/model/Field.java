package com.survey.server.domain.model;

import lombok.Data;
import lombok.With;

import java.util.List;

@Data
@With
public class Field {
    private String id;
    private String label;
    private FieldType type;
    private String helper;
    private Boolean required;
    private List<Answer> answers;

    public Field() {
    }

    public Field(String id, String label, FieldType type, String helper, Boolean required, List<Answer> answers) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.helper = helper;
        this.required = required;
        this.answers = answers;
    }
}
