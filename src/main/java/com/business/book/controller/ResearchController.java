package com.business.book.controller;

import com.business.book.entity.Enterprise;
import com.business.book.entity.constants.Type;
import com.business.book.service.ResearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/research")
public class ResearchController {

    private final ResearchService researchService;

    public ResearchController(ResearchService researchService) {
        this.researchService = researchService;
    }

    @GetMapping("/all")
    public Flux<Enterprise> getAllResearch() {
        return researchService.findAll();
    }
    @GetMapping("/all-size")
    public Flux<Enterprise> findAll(@RequestParam int size) {
        return researchService.findAll(size);
    }

    @GetMapping("/type")
    public Flux<Enterprise> findByType(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam Type type) {
        return researchService.findByType(type, fromIndex, toIndex);
    }

    @GetMapping("/short-name")
    public Flux<Enterprise> findByShortName(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam String shortName) {
        return researchService.findByShortName(shortName, fromIndex, toIndex);
    }

    @GetMapping("/long-name")
    public Flux<Enterprise> findByLongName(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam String longName) {
        return researchService.findByLongName(longName, fromIndex, toIndex);
    }

    @GetMapping("/keyword")
    public Flux<Enterprise> findByKeyword(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam String keyword) {
        return researchService.findByKeyword(keyword, fromIndex, toIndex);
    }

    @GetMapping("/capital-share")
    public Flux<Enterprise> findByCapitalShare(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam double capitalShare) {
        return researchService.findByCapitalShare(capitalShare, fromIndex, toIndex);
    }

    @GetMapping("/year-founded-min")
    public Flux<Enterprise> findByYearFoundedMin(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam int yearFoundedMin) {
        return researchService.findByYearFoundedMin(yearFoundedMin, fromIndex, toIndex);
    }

    @GetMapping("/year-founded-max")
    public Flux<Enterprise> findByYearFoundedMax(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam int yearFoundedMax) {
        return researchService.findByYearFoundedMax(yearFoundedMax, fromIndex, toIndex);
    }

    @GetMapping("/employees-min")
    public Flux<Enterprise> findByNumberOfEmployeesMin(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam int numberOfEmployeesMin) {
        return researchService.findByNumberOfEmployeesMin(numberOfEmployeesMin, fromIndex, toIndex);
    }

    @GetMapping("/employees-max")
    public Flux<Enterprise> findByNumberOfEmployeesMax(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam int numberOfEmployeesMax) {
        return researchService.findByNumberOfEmployeesMax(numberOfEmployeesMax, fromIndex, toIndex);
    }

    @GetMapping("/business-registration-number")
    public Flux<Enterprise> findByBusinessRegistrationNumber(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam String registrationNumber) {
        return researchService.findByBusinessRegistrationNumber(registrationNumber, fromIndex, toIndex);
    }
}
