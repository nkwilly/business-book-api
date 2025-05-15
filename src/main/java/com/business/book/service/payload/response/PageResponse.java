package com.business.book.service.payload.response;

import com.business.book.entity.Enterprise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse {
    int totalResult;
    List<Enterprise> enterprises;
}