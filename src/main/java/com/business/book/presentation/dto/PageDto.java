package com.business.book.presentation.dto;

import com.business.book.infrastructure.entity.Enterprise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    int totalResult;
    List<Enterprise> enterprises;
}