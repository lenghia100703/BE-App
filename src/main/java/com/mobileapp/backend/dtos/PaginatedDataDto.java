package com.mobileapp.backend.dtos;

import com.mobileapp.backend.dtos.user.UserDto;
import lombok.Data;

import java.util.List;
import java.util.stream.Stream;

@Data
public class PaginatedDataDto<T> {
    private List<T> data;
    private int page;
    private int totalData;

    public PaginatedDataDto(List<T> data, int page, int totalData) {
        this.data = data;
        this.page = page;
        this.totalData = totalData;
    }
}
