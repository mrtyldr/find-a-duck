package com.kodizim.kodforum.api.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Response<T> {
    private T result;

    public static <T> Response<T> of(T result) {
        var response = new Response<T>();
        response.result = result;
        return response;
    }
}
