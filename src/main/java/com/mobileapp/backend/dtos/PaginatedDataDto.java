package com.mobileapp.backend.dtos;

import lombok.Data;

import java.util.List;

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
