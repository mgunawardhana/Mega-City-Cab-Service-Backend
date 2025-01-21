package com.megacity.backend.util;

import com.megacity.backend.domain.enums.StatusCode;
import com.megacity.backend.domain.enums.StatusMessage;
import com.megacity.backend.domain.response.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
public class ResponseUtil {

    private final HttpServletRequest servletRequest;

    public ResponseUtil(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public ResponseEntity<APIResponse> wrapSuccess(Object value, HttpStatus httpStatus) {
        APIResponse apiResponse = APIResponse.builder().statusCode(StatusCode.SUCCESS.valueOf())
                .origin(servletRequest.getRequestURI()).statusMessage(StatusMessage.SUCCESS.valueOf()).responseTime(DateTimeUtils.format(new Date())).result(value).build();
        return ResponseEntity.status(httpStatus).body(apiResponse);

    }

    public ResponseEntity<APIResponse> wrapError(Object value, String errorType, HttpStatus httpStatus) {
        APIResponse apiResponse = APIResponse.builder().statusCode(StatusCode.FAILURE.valueOf())
                .statusMessage(StatusMessage.FAILURE.valueOf()).errorType(errorType).origin(servletRequest.getRequestURI()).responseTime(DateTimeUtils.format(new Date())).result(Collections.singletonMap(Constant.ERROR, value)).build();

        return ResponseEntity.status(httpStatus).body(apiResponse);
    }
}
