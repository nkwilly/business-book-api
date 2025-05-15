package com.business.book.service.utils.comparators;

import com.business.book.entity.Enterprise;
import lombok.Data;
import lombok.RequiredArgsConstructor;


public record YearFoundedComparator(Enterprise enterprise) implements Comparable<Enterprise> {
    @Override
    public int compareTo(Enterprise o) {
        return enterprise.getYearFounded().compareTo(o.getYearFounded());
    }
}
