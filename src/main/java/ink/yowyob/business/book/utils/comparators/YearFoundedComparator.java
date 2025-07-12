package ink.yowyob.business.book.utils.comparators;

import ink.yowyob.business.book.infrastructure.entity.Enterprise;


public record YearFoundedComparator(Enterprise enterprise) implements Comparable<Enterprise> {
    @Override
    public int compareTo(Enterprise o) {
        return enterprise.getYearFounded().compareTo(o.getYearFounded());
    }
}
