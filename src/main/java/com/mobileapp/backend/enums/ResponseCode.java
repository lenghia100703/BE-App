package com.mobileapp.backend.enums;

public enum ResponseCode {
    SUCCESS("SUCCESS"),
    ERROR("ERROR"),
    NOT_FOUND("NOT_FOUND");
    private String code;

    private ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
