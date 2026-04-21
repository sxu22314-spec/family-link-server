package com.example.linkfamily.Exception;

public class FileException extends RuntimeException {
    private int code;

    public FileException(String message) {
        super(message);
        this.code = 400;
    }

    public FileException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
