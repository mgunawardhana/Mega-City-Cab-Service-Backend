package com.megacity.backend.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    private String statusMessage;
    private String statusCode;
    private String transactionId;
    private String responseTime;
    private String origin;
    private String errorType;
    private Object result;
}