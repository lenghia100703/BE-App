package com.mobileapp.backend.dtos;

import com.mobileapp.backend.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponseDto<T> {
    private ResponseCode code;
    private T data;

    public CommonResponseDto(ResponseCode code) {
        this(code, null);
    }

    public CommonResponseDto(T data) {
        this(ResponseCode.SUCCESS, data);
    }
}
