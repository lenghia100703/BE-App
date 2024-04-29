package com.mobileapp.backend.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("SUCCESS"),
    ERROR("ERROR"),
    NOT_FOUND("NOT_FOUND");
    private final String code;

    private ResponseCode(String code) {
        this.code = code;
    }

}
