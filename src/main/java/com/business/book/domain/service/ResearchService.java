package com.business.book.domain.service;

import com.business.book.infrastructure.entity.Enterprise;
import com.business.book.infrastructure.entity.constants.Type;
import reactor.core.publisher.Flux;


public interface ResearchService {
    Flux<Enterprise> findAll();
    
    Flux<Enterprise> findAll(int size);

    Flux<Enterprise> findByType(Type type, int fromIndex, int toIndex);

    Flux<Enterprise> findByShortName(  String name, int fromIndex, int toIndex);

    Flux<Enterprise> findByLongName(  String longName, int fromIndex, int toIndex);

    Flux<Enterprise> findByKeyword(  String keyword, int fromIndex, int toIndex);

    Flux<Enterprise> findByCapitalShare(  double capitalShare, int fromIndex, int toIndex);

    Flux<Enterprise> findByYearFoundedMin(  int yearFoundedMin, int fromIndex, int toIndex);

    Flux<Enterprise> findByYearFoundedMax(  int yearFoundedMax, int fromIndex, int toIndex);

    //Flux<Enterprise> findByBusinessDomains(  String businessDomain, int fromIndex, int toIndex);

    Flux<Enterprise> findByNumberOfEmployeesMin(  int numberOfEmployeesMin, int fromIndex, int toIndex);

    Flux<Enterprise> findByNumberOfEmployeesMax(  int numberOfEmployeesMax, int fromIndex, int toIndex);

    Flux<Enterprise> findByBusinessRegistrationNumber(  String registrationNumber, int fromIndex, int toIndex);
}