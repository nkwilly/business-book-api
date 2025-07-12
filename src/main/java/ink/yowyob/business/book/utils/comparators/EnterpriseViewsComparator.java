package ink.yowyob.business.book.utils.comparators;

import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import ink.yowyob.business.book.infrastructure.entity.EnterpriseData;
import ink.yowyob.business.book.infrastructure.repository.EnterpriseDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

public class EnterpriseViewsComparator implements Comparator<Enterprise> {
    private static final Logger log = LoggerFactory.getLogger(EnterpriseViewsComparator.class);
    private final Map<UUID, Long> viewsCache = new HashMap<>();
    private final EnterpriseDataRepository dataRepository;

    public EnterpriseViewsComparator(EnterpriseDataRepository dataRepository, Flux<Enterprise> enterprises) {
        this.dataRepository = dataRepository;

        preloadViewsData(enterprises);
    }

    private void preloadViewsData(Flux<Enterprise> enterprises) {

        Flux<UUID> ids = enterprises
                .map(Enterprise::getOrganizationId)
                .filter(Objects::nonNull);
        List<EnterpriseData> allData = new ArrayList<>();
        assert ids != null;

       // for (UUID id : ids)
         //   allData.add(
           //         dataRepository.findByEnterpriseId(id).block());

        System.out.println("\n\n\n");
        allData.forEach(data -> viewsCache.put(data.getEnterpriseId(), data.getViewsNumbers()));

        /*
        enterprises
            .doOnNext(e -> {
                if (!viewsCache.containsKey(e.getOrganizationId())) {
                    Mono<EnterpriseData> newData = createNewEnterpriseData(e.getOrganizationId());
                    viewsCache.put(e.getOrganizationId(), newData.getViewsNumbers());
                }
            })
            .subscribe();
           */
    }

    @Override
    public int compare(Enterprise e1, Enterprise e2) {
        // Récupérer depuis le cache
        long views1 = viewsCache.getOrDefault(e1.getOrganizationId(), 0L);
        long views2 = viewsCache.getOrDefault(e2.getOrganizationId(), 0L);

        // Éviter les problèmes de dépassement avec Long.compare
        return Long.compare(views2, views1); // Ordre décroissant
    }

    private Mono<EnterpriseData> createNewEnterpriseData(UUID enterpriseId) {
        EnterpriseData data = EnterpriseData.builder()
                .enterpriseId(enterpriseId)
                .viewsNumbers(1L)
                .build();
        log.info("Creating new enterprise data for enterprise id: {}", data);
        return dataRepository.save(data);
    }
}