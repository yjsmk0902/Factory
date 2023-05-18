package com.example.nextboard.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(value = Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonPropertyOrder({"success", "code", "message", "result"})
@Getter
public class Response {

    private Boolean success;
    private int code;
    private String message;
    private Object result;

    public static Response success(String message) {
        return new Response(true, HttpStatus.OK.value(), message, null);
    }

    public static Response success(String message, Object data) {
        return new Response(true, HttpStatus.OK.value(), message, data);
    }

    public static Response failure(HttpStatus status, String message) {
        return new Response(false, status.value(), message, null);
    }
}
