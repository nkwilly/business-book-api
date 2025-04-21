package com.business.book.service.payload.request;

import com.business.book.entity.Enterprise;
import lombok.Data;

import java.util.List;

@Data
public class PageResponse {
    int page;
    int size;
    int totalResult;
    List<Enterprise> enterprises;
}