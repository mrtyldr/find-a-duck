package com.kodizim.kodforum.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NonFinal
@Value
@AllArgsConstructor
public class ApiError {
    String code;
    String message;
    Object detail;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
        detail = null;
    }
}

