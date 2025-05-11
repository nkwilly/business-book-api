package com.business.book.service.impl;

import com.business.book.entity.Enterprise;
import com.business.book.entity.constants.Type;
import com.business.book.repository.EnterpriseDataRepository;
import com.business.book.service.CommunicationWithOrganizationAPI;
import com.business.book.service.ResearchService;
import com.business.book.service.payload.request.PageResponse;
import com.business.book.service.utils.comparators.CapitalShareComparator;
import com.business.book.service.utils.comparators.EnterpriseViewsComparator;
import com.business.book.service.utils.comparators.NumberOfEmployeesComparator;
import com.business.book.service.utils.comparators.YearFoundedComparator;
import org.apache.commons.text.similarity.LevenshteinDistance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    private List<Enterprise> enterprises;

    public ResearchServiceImpl(CommunicationWithOrganizationAPI organisationService, EnterpriseDataRepository dataRepository) {
        this.organisationService = organisationService;

        // Tri selon la visibilité, selon l'implémentation actuelle il y a uniquement le nombre de click qui est pris en jeu. D'autres paramètres seront pris
        // en compte plus tard.
        enterprises = organisationService.getAllEnterprise();

        String str = enterprises.stream().filter(et-> et.getOrganizationId() != null).map(et -> et.getOrganizationId().toString()).collect(Collectors.joining(", "));

        log.info("Enterprise list: {}", str);

        EnterpriseViewsComparator comparator = new EnterpriseViewsComparator(dataRepository, enterprises);
        enterprises.sort(comparator);
        this.dataRepository = dataRepository;
    }

    @Override
    public PageResponse findAll(int page, int size) {
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> dataRepository.findByEnterpriseId(enterprise.getOrganizationId()).isPresent()).toList();
        return this.constructPageResponse(page, size, sortedEnterprises);
    }

    @Override
    public PageResponse findByType(int page, int size, Type type) {
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> enterprise.getType().equals(type)).toList();
        return this.constructPageResponse(page, size, sortedEnterprises);
    }

    @Override
    public PageResponse findByShortName(int page, int size, String shortName) {
        LevenshteinDistance distance = new LevenshteinDistance();
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> {
                    int result = distance.apply(shortName, enterprise.getShortName());
                    return shortName.length() * 0.4 >= result;
                }).toList();
        return this.constructPageResponse(page, size, sortedEnterprises);
    }

    @Override
    public PageResponse findByLongName(int page, int size, String longName) {
        LevenshteinDistance distance = new LevenshteinDistance();
        List<Enterprise> sortedEnterprises = enterprises.stream()
                .filter(enterprise -> {
                    int result = distance.apply(longName, enterprise.getLongName());
                    return longName.length() * 0.4 >= result;
                }).toList();
        return this.constructPageResponse(page, size, sortedEnterprises);
    }

    @Override
    public PageResponse findByKeyword(int page, int size, String keyword) {
        List<Enterprise> sortedEnterprise = enterprises.stream()
                .filter(enterprise -> enterprise.getKeywords().contains(keyword)).toList();
        return this.constructPageResponse(page, size, sortedEnterprise);
    }

    @Override
    public PageResponse findByCapitalShare(int page, int size, double capitalShare) {
        List<Enterprise> sortedEnterprise = enterprises.stream()
                .map(CapitalShareComparator::new).sorted().map(CapitalShareComparator::getEnterprise).toList();
        return this.constructPageResponse(page, size, sortedEnterprise);
    }

    @Override
    public PageResponse findByYearFoundedMin(int page, int size, int yearFoundedMin) {
        List<Enterprise> sortedEnterprise = enterprises.stream()
                .filter(enterprise -> enterprise.getYearFounded().getYear() >= yearFoundedMin)
                .map(YearFoundedComparator::new).sorted()
                .map(YearFoundedComparator::getEnterprise).toList();
        return this.constructPageResponse(page, size, sortedEnterprise);
    }

    @Override
    public PageResponse findByYearFoundedMax(int page, int size, int yearFoundedMax) {
        List<Enterprise> sortedEnterprise = enterprises.stream()
                .filter(enterprise -> enterprise.getYearFounded().getYear() <= yearFoundedMax)
                .map(YearFoundedComparator::new).sorted()
                .map(YearFoundedComparator::getEnterprise)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.reverse(list);
                    return list;
                }));
        return this.constructPageResponse(page, size, sortedEnterprise);
    }

    /*
    @Override
    public PageResponse findByBusinessDomains(int page, int size, String businessDomain) {
        List<Enterprise> sortedEnterprise = enterprises.stream()
                .filter(enterprise ->
                        enterprise.getBusinessDomains().stream()
                                .map(dm -> organisationService.get)
                                .anyMatch(domain -> domain.getDomainName().name().equals(businessDomain)))
                .toList();
        return this.constructPageResponse(page, size, sortedEnterprise);
    }*/

    @Override
    public PageResponse findByNumberOfEmployeesMin(int page, int size, int numberOfEmployeesMin) {
        List<Enterprise> sortedEnterprise = enterprises.stream()
                .filter(enterprise -> enterprise.getNumberOfEmployees() >= numberOfEmployeesMin)
                .map(NumberOfEmployeesComparator::new).sorted()
                .map(NumberOfEmployeesComparator::getEnterprise).toList();
        return this.constructPageResponse(page, size, sortedEnterprise);
    }

    @Override
    public PageResponse findByNumberOfEmployeesMax(int page, int size, int numberOfEmployeesMax) {
        List<Enterprise> sortedEnterprise = enterprises.stream()
                .filter(enterprise -> enterprise.getNumberOfEmployees() <= numberOfEmployeesMax)
                .map(NumberOfEmployeesComparator::new).sorted()
                .map(NumberOfEmployeesComparator::getEnterprise)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.reverse(list);
                    return list;
                }));
        return this.constructPageResponse(page, size, sortedEnterprise);
    }

    @Override
    public PageResponse findByBusinessRegistrationNumber(int page, int size, String registrationNumber) {
        List<Enterprise> sortedEnterprise = enterprises.stream()
                .filter(enterprise -> enterprise.getBusinessRegistrationNumber().equals(registrationNumber))
                .toList();
        return this.constructPageResponse(page, size, sortedEnterprise);
    }

    @Scheduled(fixedRate = 3600000)
    private void refreshEnterprisesValue() {
        enterprises = organisationService.getAllEnterprise();
        EnterpriseViewsComparator comparator = new EnterpriseViewsComparator(dataRepository, enterprises);
        enterprises.sort(comparator);
    }

    private PageResponse constructPageResponse(int page, int size, List<Enterprise> sortedEnterprises) {
        PageResponse response = new PageResponse();
        response.setPage(page);
        response.setSize(size);
        response.setTotalResult(sortedEnterprises.size());
        int fromIndex = (page - 1) * size;
        int toIndex = fromIndex * size;
        response.setEnterprises(sortedEnterprises.subList(fromIndex, toIndex));
        return response;
    }
}