package com.business.book.service;

import com.business.book.entity.constants.Type;
import com.business.book.service.payload.response.PageResponse;


public interface ResearchService {
    PageResponse findAll();
    
    PageResponse findAll(int size);

    PageResponse findByType(Type type, int fromIndex, int toIndex);

    PageResponse findByShortName(  String name, int fromIndex, int toIndex);

    PageResponse findByLongName(  String longName, int fromIndex, int toIndex);

    PageResponse findByKeyword(  String keyword, int fromIndex, int toIndex);

    PageResponse findByCapitalShare(  double capitalShare, int fromIndex, int toIndex);

    PageResponse findByYearFoundedMin(  int yearFoundedMin, int fromIndex, int toIndex);

    PageResponse findByYearFoundedMax(  int yearFoundedMax, int fromIndex, int toIndex);

    //PageResponse findByBusinessDomains(  String businessDomain, int fromIndex, int toIndex);

    PageResponse findByNumberOfEmployeesMin(  int numberOfEmployeesMin, int fromIndex, int toIndex);

    PageResponse findByNumberOfEmployeesMax(  int numberOfEmployeesMax, int fromIndex, int toIndex);

    PageResponse findByBusinessRegistrationNumber(  String registrationNumber, int fromIndex, int toIndex);
}