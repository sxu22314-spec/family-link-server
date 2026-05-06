package com.example.linkfamily.Exception;

import com.example.linkfamily.Response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle FileException
     */
    @ExceptionHandler(FileException.class)
    public Response<Object> handleFileException(FileException e) {
        log.error("FileException: {}", e.getMessage());
        return new Response<>(e.getCode(), e.getMessage(), null);
    }

    /**
     * Handle PhotoException
     */
    @ExceptionHandler(PhotoException.class)
    public Response<Object> handlePhotoException(PhotoException e) {
        log.error("PhotoException: {}", e.getMessage());
        return new Response<>(e.getCode(), e.getMessage(), null);
    }

    /**
     * Handle file size exceeded
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Response<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("File size exceeded: {}", e.getMessage());
        return new Response<>(413, "File size exceeds the allowed limit", null);
    }

    /**
     * Handle invalid request parameters
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException: {}", e.getMessage());
        return new Response<>(400, e.getMessage(), null);
    }

    /**
     * Handle general exceptions
     */
    @ExceptionHandler(Exception.class)
    public Response<Object> handleException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage(), e);
        return new Response<>(500, "An unexpected error occurred: " + e.getMessage(), null);
    }
}
