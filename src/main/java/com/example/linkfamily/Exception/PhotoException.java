package com.example.linkfamily.Exception;

public class PhotoException extends RuntimeException {
    private int code;

    public PhotoException(String message) {
        super(message);
        this.code = 400;
    }

    public PhotoException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
