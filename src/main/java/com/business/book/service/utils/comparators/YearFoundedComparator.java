package com.business.book.service.utils.comparators;

import com.business.book.entity.Enterprise;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class YearFoundedComparator implements Comparable<Enterprise> {
    private final Enterprise enterprise;

    @Override
    public int compareTo(Enterprise o) {
        return enterprise.getYearFounded().compareTo(o.getYearFounded());
    }
}
