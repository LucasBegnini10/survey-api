package com.survey.server.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDTO {

    @JsonProperty("id")
    String id;

    @JsonProperty("message")
    String message;

    @JsonProperty("data")
    Object data;

    @JsonProperty("status")
    int status;

    @JsonProperty("timestamp")
    String timestamp;
}