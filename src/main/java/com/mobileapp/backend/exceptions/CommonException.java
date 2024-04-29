package com.mobileapp.backend.exceptions;

import com.mobileapp.backend.enums.ResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CommonException extends RuntimeException {
    ResponseCode code;

    public CommonException(ResponseCode code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(ResponseCode code) {
        this.code = code;
    }
}
