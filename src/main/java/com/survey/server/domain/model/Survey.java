package com.survey.server.domain.model;

import lombok.Data;
import lombok.With;

import java.time.LocalDateTime;
import java.util.List;

@Data
@With
public class Survey {
    private String id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Boolean isDeleted;
    private List<Field> fields;

    public Survey() {
    }

    public Survey(String id, String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, Boolean isDeleted, List<Field> fields) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        this.isDeleted = isDeleted;
        this.fields = fields;
    }
}
