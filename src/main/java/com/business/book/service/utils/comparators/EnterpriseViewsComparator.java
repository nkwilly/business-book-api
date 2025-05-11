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
        String str = enterprises.stream().filter(et-> et.getOrganizationId() !=null).map(et -> et.getOrganizationId().toString()).collect(Collectors.joining(" "));
        preloadViewsData(enterprises, str);
    }

    private void preloadViewsData(List<Enterprise> enterprises, String str) {

        //log.error("Preloading views data : {}", str);
        System.out.println("\n\n\nPreloading views data : " + str);
        //log.error("Enterprise data count: {}", enterprises.size());
        //log.error("Enterprise data: {}", str);
        List<UUID> ids = enterprises.stream().map(Enterprise::getOrganizationId).toList();
        //List<EnterpriseData> allData = dataRepository.findAllByEnterpriseIdIn(ids);
        List<EnterpriseData> allData = new ArrayList<>();
        for (UUID id : ids)
            if (id != null)
                allData.add(
                        dataRepository.findById(id)
                                .orElse(EnterpriseData.builder().enterpriseId(id).viewsNumbers(0L).build()));

        allData.forEach(data -> viewsCache.put(data.getEnterpriseId(), data.getViewsNumbers()));

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
        return dataRepository.save(data);
    }
}