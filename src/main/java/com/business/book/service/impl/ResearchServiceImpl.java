package com.business.book.service.impl;

import com.business.book.entity.Enterprise;
import com.business.book.entity.constants.Type;
import com.business.book.repository.EnterpriseDataRepository;
import com.business.book.service.CommunicationWithOrganizationAPI;
import com.business.book.service.ResearchService;
import com.business.book.service.utils.comparators.CapitalShareComparator;
import com.business.book.service.utils.comparators.EnterpriseViewsComparator;
import com.business.book.service.utils.comparators.NumberOfEmployeesComparator;
import com.business.book.service.utils.comparators.YearFoundedComparator;
import org.apache.commons.text.similarity.LevenshteinDistance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

        // Tri selon la visibilité, selon l'implémentation actuelle il y a uniquement le nombre de click qui est pris en jeu. D'autres paramètres seront pris
        // en compte plus tard.
        //enterprises = organisationService.getAllEnterprise();
        //String str = enterprises.stream().filter(et -> et.getOrganizationId() != null).map(et -> et.getOrganizationId().toString()).collect(Collectors.joining(", "));

        //log.info("Enterprise list: {}", str);
        
        //EnterpriseViewsComparator comparator = new EnterpriseViewsComparator(dataRepository, enterprises);
        //enterprises.sort(comparator);

        this.dataRepository = dataRepository;
    }

    @Override
    public Flux<Enterprise> findAll(int size) {
        /*
        return this.constructFlux<Enterprise>(enterprises, 0, size);

         */
         return null;
    }

    @Override
    public Flux<Enterprise> findAll() {
        /*
        return this.constructFlux<Enterprise>(enterprises, 0, enterprises.size());

         */
        return null;
    }

    @Override
    public Flux<Enterprise> findByType(Type type, int fromIndex, int toIndex) {
        /*
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> enterprise.getType().equals(type)).toList();
        return this.constructFlux<Enterprise>(sortedEnterprises, fromIndex, toIndex);

         */
        return null;
    }

    @Override
    public Flux<Enterprise> findByShortName(String shortName, int fromIndex, int toIndex) {
        /*
        LevenshteinDistance distance = new LevenshteinDistance();
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> {
                    int result = distance.apply(shortName, enterprise.getShortName());
                    return shortName.length() * 0.4 >= result;
                }).toList();
        return this.constructFlux<Enterprise>(sortedEnterprises, fromIndex, toIndex);

         */
        return null;
    }

    @Override
    public Flux<Enterprise> findByLongName(String longName, int fromIndex, int toIndex) {
        /*
        LevenshteinDistance distance = new LevenshteinDistance();
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> {
                    int result = distance.apply(longName, enterprise.getLongName());
                    return longName.length() * 0.4 >= result;
                }).toList();
        return this.constructFlux<Enterprise>(sortedEnterprises, fromIndex, toIndex);

         */
        return null;
    }

    @Override
    public Flux<Enterprise> findByKeyword(String keyword, int fromIndex, int toIndex) {
        /*
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> enterprise.getKeywords().contains(keyword)).toList();
        return this.constructFlux<Enterprise>(sortedEnterprises, fromIndex, toIndex);

         */
        return null;
    }

    @Override
    public Flux<Enterprise> findByCapitalShare(double capitalShare, int fromIndex, int toIndex) {
        /*
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .map(CapitalShareComparator::new).sorted().map(CapitalShareComparator::getEnterprise).toList();
        return this.constructFlux<Enterprise>(sortedEnterprises, fromIndex, toIndex);

         */
        return null;
    }

    @Override
    public Flux<Enterprise> findByYearFoundedMin(int yearFoundedMin, int fromIndex, int toIndex) {
        /*
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> enterprise.getYearFounded().getYear() >= yearFoundedMin)
                .sorted(Comparator.comparing(Enterprise::getYearFounded))
                .toList();
        return this.constructFlux<Enterprise>(sortedEnterprises, fromIndex, toIndex);

         */
        return null;
    }

    @Override
    public Flux<Enterprise> findByYearFoundedMax(int yearFoundedMax, int fromIndex, int toIndex) {
        /*
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> enterprise.getYearFounded().getYear() <= yearFoundedMax)
                .sorted(Comparator.comparing(Enterprise::getYearFounded))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.reverse(list);
                    return list;
                }));
        return this.constructFlux<Enterprise>(sortedEnterprises, fromIndex, toIndex);

         */
        return null;
    }

    /*
    @Override
    public Flux<Enterprise> findByBusinessDomains(  String businessDomain, int fromIndex, int toIndex) {
        List<Enterprise> sortedEnterprise = enterprises.stream()
                .filter(enterprise ->
                        enterprise.getBusinessDomains().stream()
                                .map(dm -> organisationService.get)
                                .anyMatch(domain -> domain.getDomainName().name().equals(businessDomain)))
                .toList();
        return this.constructFlux<Enterprise>( sortedEnterprises, fromIndex, toIndex);;
    }*/

    @Override
    public Flux<Enterprise> findByNumberOfEmployeesMin(int numberOfEmployeesMin, int fromIndex, int toIndex) {
        /*
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> enterprise.getNumberOfEmployees() >= numberOfEmployeesMin)
                .map(NumberOfEmployeesComparator::new).sorted()
                .map(NumberOfEmployeesComparator::getEnterprise).toList();
        return this.constructFlux<Enterprise>(sortedEnterprises, fromIndex, toIndex);

         */
        return null;
    }

    @Override
    public Flux<Enterprise> findByNumberOfEmployeesMax(int numberOfEmployeesMax, int fromIndex, int toIndex) {
        /*
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> enterprise.getNumberOfEmployees() <= numberOfEmployeesMax)
                .map(NumberOfEmployeesComparator::new).sorted()
                .map(NumberOfEmployeesComparator::getEnterprise)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.reverse(list);
                    return list;
                }));
        return this.constructFlux<Enterprise>(sortedEnterprises, fromIndex, toIndex);

         */
        return null;
    }

    @Override
    public Flux<Enterprise> findByBusinessRegistrationNumber(String registrationNumber, int fromIndex, int toIndex) {
        /*
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> enterprise.getBusinessRegistrationNumber().equals(registrationNumber))
                .toList();
        return this.constructFlux<Enterprise>(sortedEnterprises, fromIndex, toIndex);

         */
        return null;
    }

    //@Scheduled(fixedRate = 360000)
    private void refreshEnterprisesValue() {
        /*
        enterprises = organisationService.getAllEnterprise();
        EnterpriseViewsComparator comparator = new EnterpriseViewsComparator(dataRepository, enterprises);
        enterprises.sort(comparator);

         */
        //return null;
    }

    private Flux<Enterprise> constructPage(List<Enterprise> sortedEnterprises, int fromIndex, int toIndex) {
        /*
        if (fromIndex > toIndex || toIndex < 0 || fromIndex < 0)
            throw new IllegalArgumentException("fromIndex or toIndex out of bounds");
        if (toIndex > sortedEnterprises.size()) {
            return Flux<Enterprise>.builder()
                    .enterprises(sortedEnterprises)
                    .totalResult(sortedEnterprises.size())
                    .build();
        }
        return Flux<Enterprise>.builder()
                .enterprises(sortedEnterprises.subList(fromIndex, toIndex))
                .totalResult(toIndex - fromIndex)
                .build();

         */
        //Object o = null;
        return null;
    }
}