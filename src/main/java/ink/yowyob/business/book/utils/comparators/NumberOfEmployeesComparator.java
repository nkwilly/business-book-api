package ink.yowyob.business.book.utils.comparators;

import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class NumberOfEmployeesComparator implements Comparable<Enterprise> {
    private final Enterprise enterprise;

    @Override
    public int compareTo(Enterprise o) {
        return this.enterprise.getNumberOfEmployees() - o.getNumberOfEmployees();
    }
}