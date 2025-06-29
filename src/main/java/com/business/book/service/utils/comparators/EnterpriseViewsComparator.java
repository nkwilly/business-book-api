package com.business.book.service.utils.comparators;

import com.business.book.entity.Enterprise;
import com.business.book.entity.EnterpriseData;
import com.business.book.repository.EnterpriseDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class EnterpriseViewsComparator implements Comparator<Enterprise> {
    private static final Logger log = LoggerFactory.getLogger(EnterpriseViewsComparator.class);
    private final Map<UUID, Long> viewsCache = new HashMap<>();
    private final EnterpriseDataRepository dataRepository;

    public EnterpriseViewsComparator(EnterpriseDataRepository dataRepository, List<Enterprise> enterprises) {
        this.dataRepository = dataRepository;
        // Pré-charger toutes les données en une seule requête
        String str = enterprises.stream().filter(et -> et.getOrganizationId() != null).map(et -> et.getOrganizationId().toString()).collect(Collectors.joining(" T"));
        if (enterprises.size() != 0)
            preloadViewsData(enterprises, str);
    }

    private void preloadViewsData(List<Enterprise> enterprises, String str) {
        //log.error("Preloading views data : {}", str);
        System.out.println("\n\n\nPreloading views data : " + str);
        //log.error("Enterprise data: {}", str);
        List<UUID> ids = enterprises.stream()
                .map(Enterprise::getOrganizationId)
                .filter(Objects::nonNull).toList();
        //List<EnterpriseData> allData = dataRepository.findAllByEnterpriseIdIn(ids);
        List<EnterpriseData> allData = new ArrayList<>();
        if (!ids.isEmpty())
            System.out.println("\nAll views data : " + ids.size() + "\t" + ids.get(0));
        for (UUID id : ids)
            allData.add(
                    dataRepository.findByEnterpriseId(id)
                            .orElse(EnterpriseData.builder().enterpriseId(id).viewsNumbers(0L).build()));

        System.out.println("\n\n\n");
        allData.forEach(data -> viewsCache.put(data.getEnterpriseId(), data.getViewsNumbers()));

        if (!enterprises.isEmpty())
            enterprises.forEach(e -> {
                if (!viewsCache.containsKey(e.getOrganizationId())) {
                    EnterpriseData newData = createNewEnterpriseData(e.getOrganizationId());
                    viewsCache.put(e.getOrganizationId(), newData.getViewsNumbers());
                }
            });
    }

    @Override
    public int compare(Enterprise e1, Enterprise e2) {
        // Récupérer depuis le cache
        long views1 = viewsCache.getOrDefault(e1.getOrganizationId(), 0L);
        long views2 = viewsCache.getOrDefault(e2.getOrganizationId(), 0L);

        // Éviter les problèmes de dépassement avec Long.compare
        return Long.compare(views2, views1); // Ordre décroissant
    }

    private EnterpriseData createNewEnterpriseData(UUID enterpriseId) {
        EnterpriseData data = EnterpriseData.builder()
                .enterpriseId(enterpriseId)
                .viewsNumbers(1L)
                .build();
        log.info("Creating new enterprise data for enterprise id: {}", data);
        return dataRepository.save(data);
    }
}