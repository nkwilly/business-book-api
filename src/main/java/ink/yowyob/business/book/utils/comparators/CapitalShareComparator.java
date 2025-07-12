package ink.yowyob.business.book.utils.comparators;

import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import lombok.Data;
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