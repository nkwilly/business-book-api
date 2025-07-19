package com.business.book.presentation.dto;

import lombok.Data;

@Data
public class ActivityDto {
    private String type;

    private String name;

    private int rate;

    private String description;
}
