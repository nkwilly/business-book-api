package com.business.book.service.utils.comparators;

import com.business.book.entity.Enterprise;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CapitalShareComparator implements Comparable<Enterprise> {
    private final Enterprise enterprise;

    @Override
    public int compareTo(Enterprise o) {
        return (int) (this.enterprise.getCapitalShare() - o.getCapitalShare());
    }
}
