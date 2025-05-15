package com.business.book.controller;

import com.business.book.entity.constants.Type;
import com.business.book.service.ResearchService;
import com.business.book.service.payload.response.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/research")
public class ResearchController {

    private final ResearchService researchService;

    public ResearchController(ResearchService researchService) {
        this.researchService = researchService;
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse> getAllResearch() {
        return ResponseEntity.ok(researchService.findAll());
    }
    @GetMapping("/all-size")
    public ResponseEntity<PageResponse> findAll(@RequestParam int size) {
        return ResponseEntity.ok(researchService.findAll(size));
    }

    @GetMapping("/type")
    public ResponseEntity<PageResponse> findByType(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam Type type) {
        return ResponseEntity.ok(researchService.findByType(type, fromIndex, toIndex));
    }

    @GetMapping("/short-name")
    public ResponseEntity<PageResponse> findByShortName(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam String shortName) {
        return ResponseEntity.ok(researchService.findByShortName(shortName, fromIndex, toIndex));
    }

    @GetMapping("/long-name")
    public ResponseEntity<PageResponse> findByLongName(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam String longName) {
        return ResponseEntity.ok(researchService.findByLongName(longName, fromIndex, toIndex));
    }

    @GetMapping("/keyword")
    public ResponseEntity<PageResponse> findByKeyword(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam String keyword) {
        return ResponseEntity.ok(researchService.findByKeyword(keyword, fromIndex, toIndex));
    }

    @GetMapping("/capital-share")
    public ResponseEntity<PageResponse> findByCapitalShare(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam double capitalShare) {
        return ResponseEntity.ok(researchService.findByCapitalShare(capitalShare, fromIndex, toIndex));
    }

    @GetMapping("/year-founded-min")
    public ResponseEntity<PageResponse> findByYearFoundedMin(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam int yearFoundedMin) {
        return ResponseEntity.ok(researchService.findByYearFoundedMin(yearFoundedMin, fromIndex, toIndex));
    }

    @GetMapping("/year-founded-max")
    public ResponseEntity<PageResponse> findByYearFoundedMax(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam int yearFoundedMax) {
        return ResponseEntity.ok(researchService.findByYearFoundedMax(yearFoundedMax, fromIndex, toIndex));
    }

    @GetMapping("/employees-min")
    public ResponseEntity<PageResponse> findByNumberOfEmployeesMin(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam int numberOfEmployeesMin) {
        return ResponseEntity.ok(researchService.findByNumberOfEmployeesMin(numberOfEmployeesMin, fromIndex, toIndex));
    }

    @GetMapping("/employees-max")
    public ResponseEntity<PageResponse> findByNumberOfEmployeesMax(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam int numberOfEmployeesMax) {
        return ResponseEntity.ok(researchService.findByNumberOfEmployeesMax(numberOfEmployeesMax, fromIndex, toIndex));
    }

    @GetMapping("/business-registration-number")
    public ResponseEntity<PageResponse> findByBusinessRegistrationNumber(
            @RequestParam int fromIndex,
            @RequestParam int toIndex,
            @RequestParam String registrationNumber) {
        return ResponseEntity.ok(researchService.findByBusinessRegistrationNumber(registrationNumber, fromIndex, toIndex));
    }
}
