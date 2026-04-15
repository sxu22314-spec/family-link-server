package com.example.linkfamily.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private int code;
    private String message;
    private T data;

    //return default success message without data
    public static Response success() {
        return new Response(0, "OPERATION SUCCESS", null);
    }

    //return custom success message without data
    public static Response success(String message) {
        return new Response(0, message, null);
    }

    //return default success message with data
    public static <T> Response<T> success(T data) {
        return new Response<>(0, "OPERATION SUCCESS", data);
    }

    //return custom success message with data
    public static <T> Response<T> success(String message, T data){
            return new Response<>(0, message, data);
        }

    //return default error message without data
    public static Response error(){
        return new Response(1, "OPERATION FAILED", null);
    }

    //return custom error message without data
    public static Response error(String message){
        return new Response(1, message, null);
    }

    

}

