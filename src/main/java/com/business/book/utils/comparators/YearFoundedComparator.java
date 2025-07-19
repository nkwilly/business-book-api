package com.business.book.utils.comparators;

import com.business.book.infrastructure.entity.Enterprise;


public record YearFoundedComparator(Enterprise enterprise) implements Comparable<Enterprise> {
    @Override
    public int compareTo(Enterprise o) {
        return enterprise.getYearFounded().compareTo(o.getYearFounded());
    }
}
