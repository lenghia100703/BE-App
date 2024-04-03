package com.mobileapp.backend.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PaginatedDataDto<T> {
    public static final String TOTAL_META = "total";
    private Map<String, String> meta = new HashMap<>(2);
    private List<T> data;

    public PaginatedDataDto() {
    }

    public PaginatedDataDto(long total, List<T> data) {
        meta.put(TOTAL_META, String.valueOf(total));
        this.data = data;
    }

    public <R> PaginatedDataDto(long total, List<R> data, Function<? super R, ? extends T> mapper) {
        meta.put(TOTAL_META, String.valueOf(total));
        this.data = data.stream().map(mapper).collect(Collectors.toList());
    }

    public <R> PaginatedDataDto(PaginateData<R> paginateData,
                                Function<? super R, ? extends T> mapper) {
        meta.put(TOTAL_META, String.valueOf(paginateData.getTotal()));
        data = paginateData.getData().stream().map(mapper).collect(Collectors.toList());
    }
}
