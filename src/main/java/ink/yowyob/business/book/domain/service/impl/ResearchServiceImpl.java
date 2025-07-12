package ink.yowyob.business.book.domain.service.impl;

import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import ink.yowyob.business.book.infrastructure.entity.constants.Type;
import ink.yowyob.business.book.infrastructure.repository.EnterpriseDataRepository;
import ink.yowyob.business.book.domain.service.CommunicationWithOrganizationAPI;
import ink.yowyob.business.book.domain.service.ResearchService;
import ink.yowyob.business.book.presentation.dto.PageDto;
import ink.yowyob.business.book.utils.comparators.CapitalShareComparator;
import ink.yowyob.business.book.utils.comparators.EnterpriseViewsComparator;
import ink.yowyob.business.book.utils.comparators.NumberOfEmployeesComparator;
import org.apache.commons.text.similarity.LevenshteinDistance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Pour l'instant chaque méthode réalise un seul tri celui selon son critère. Il faudra vérifier si le tri initial assure
 * l'ordre selon la visibilité.
 */
@Slf4j
@Service
public class ResearchServiceImpl implements ResearchService {
    private final CommunicationWithOrganizationAPI organisationService;

    private final EnterpriseDataRepository dataRepository;

    private Flux<Enterprise> enterprises;

    public ResearchServiceImpl(CommunicationWithOrganizationAPI organisationService, EnterpriseDataRepository dataRepository) {
        this.organisationService = organisationService;

        enterprises = organisationService.getAllEnterprise();

        //EnterpriseViewsComparator comparator = new EnterpriseViewsComparator(dataRepository, enterprises);
        //enterprises.sort(comparator);

        this.dataRepository = dataRepository;
    }

    @Override
    public Flux<Enterprise> findAll(int size) {
        return enterprises;
    }

    @Override
    public Flux<Enterprise> findAll() {
        return enterprises;
    }

    @Override
    public Flux<Enterprise> findByType(Type type) {
        return enterprises.filter(enterprise -> enterprise.getType().equals(type));
    }

    @Override
    public Flux<Enterprise> findByShortName(String shortName) {
        LevenshteinDistance distance = new LevenshteinDistance();
        return enterprises.filter(enterprise -> {
            int result = distance.apply(shortName, enterprise.getShortName());
            return shortName.length() * 0.4 >= result;
        });
    }

    @Override
    public Flux<Enterprise> findByLongName(String longName) {
        LevenshteinDistance distance = new LevenshteinDistance();
        return enterprises.filter(enterprise -> {
            int result = distance.apply(longName, enterprise.getLongName());
            return longName.length() * 0.4 >= result;
        });
    }

    @Override
    public Flux<Enterprise> findByKeyword(String keyword) {
        return enterprises.filter(enterprise -> enterprise.getKeywords().contains(keyword));
    }

    @Override
    public Flux<Enterprise> findByCapitalShare(double capitalShare) {
        // Note: This requires changing to reactive sorting approach
        return enterprises.collectList()
                .flatMapMany(list -> Flux.fromIterable(list.stream()
                        .map(CapitalShareComparator::new)
                        .sorted()
                        .map(CapitalShareComparator::getEnterprise)
                        .collect(Collectors.toList())));

    }

    @Override
    public Flux<Enterprise> findByYearFoundedMin(int yearFoundedMin) {
        return enterprises.filter(enterprise -> enterprise.getYearFounded().getYear() >= yearFoundedMin)
                .sort(Comparator.comparing(Enterprise::getYearFounded));
    }

    @Override
    public Flux<Enterprise> findByYearFoundedMax(int yearFoundedMax) {
        return enterprises.collectList()
                .flatMapMany(list -> Flux.fromIterable(list.stream()
                        .filter(enterprise -> enterprise.getYearFounded().getYear() <= yearFoundedMax)
                        .sorted(Comparator.comparing(Enterprise::getYearFounded).reversed())
                        .collect(Collectors.toList())));
    }

    @Override
    public Flux<Enterprise> findByNumberOfEmployeesMin(int numberOfEmployeesMin) {
        return enterprises.collectList()
                .flatMapMany(list -> Flux.fromIterable(list.stream()
                        .filter(enterprise -> enterprise.getNumberOfEmployees() >= numberOfEmployeesMin)
                        .map(NumberOfEmployeesComparator::new)
                        .sorted()
                        .map(NumberOfEmployeesComparator::getEnterprise)
                        .collect(Collectors.toList())));
    }

    @Override
    public Flux<Enterprise> findByNumberOfEmployeesMax(int numberOfEmployeesMax) {
        return enterprises.collectList()
                .flatMapMany(list -> Flux.fromIterable(list.stream()
                        .filter(enterprise -> enterprise.getNumberOfEmployees() <= numberOfEmployeesMax)
                        .map(NumberOfEmployeesComparator::new)
                        .sorted(Collections.reverseOrder())
                        .map(NumberOfEmployeesComparator::getEnterprise)
                        .collect(Collectors.toList())));
    }

    @Override
    public Flux<Enterprise> findByBusinessRegistrationNumber(String registrationNumber) {
        return enterprises.filter(enterprise -> enterprise.getBusinessRegistrationNumber().equals(registrationNumber));
    }
}