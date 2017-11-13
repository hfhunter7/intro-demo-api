package com.example.demo.exception;

public class ConstantExeption extends Exception{
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public ConstantExeption(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public ConstantExeption() {
        super();
    }
}
