package com.business.book.service;

import com.business.book.entity.constants.Type;
import com.business.book.service.payload.request.PageResponse;


public interface ResearchService {
    PageResponse findAll(int page, int size);

    PageResponse findByType(int page, int size, Type type);

    PageResponse findByShortName(int page, int size, String name);

    PageResponse findByLongName(int page, int size, String longName);

    PageResponse findByKeyword(int page, int size, String keyword);

    PageResponse findByCapitalShare(int page, int size, double capitalShare);

    PageResponse findByYearFoundedMin(int page, int size, int yearFoundedMin);

    PageResponse findByYearFoundedMax(int page, int size, int yearFoundedMax);

    PageResponse findByBusinessDomains(int page, int size, String businessDomain);

    PageResponse findByNumberOfEmployeesMin(int page, int size, int numberOfEmployeesMin);

    PageResponse findByNumberOfEmployeesMax(int page, int size, int numberOfEmployeesMax);

    PageResponse findByBusinessRegistrationNumber(int page, int size, String registrationNumber);
}