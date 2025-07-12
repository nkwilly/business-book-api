package ink.yowyob.business.book.domain.service;

import ink.yowyob.business.book.infrastructure.entity.Enterprise;
import ink.yowyob.business.book.infrastructure.entity.constants.Type;
import ink.yowyob.business.book.presentation.dto.PageDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResearchService {
    Flux<Enterprise> findAll();
    
    Flux<Enterprise> findAll(int size);

    Flux<Enterprise> findByType(Type type);

    Flux<Enterprise> findByShortName(String name);

    Flux<Enterprise> findByLongName(String longName);

    Flux<Enterprise> findByKeyword(String keyword);

    Flux<Enterprise> findByCapitalShare(double capitalShare);

    Flux<Enterprise> findByYearFoundedMin(int yearFoundedMin);

    Flux<Enterprise> findByYearFoundedMax(int yearFoundedMax);

    //Flux<Enterprise> findByBusinessDomains(String businessDomain);

    Flux<Enterprise> findByNumberOfEmployeesMin(int numberOfEmployeesMin);

    Flux<Enterprise> findByNumberOfEmployeesMax(int numberOfEmployeesMax);

    Flux<Enterprise> findByBusinessRegistrationNumber(String registrationNumber);
}
