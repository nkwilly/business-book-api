package ink.yowyob.business.book.presentation.controller;

import ink.yowyob.business.book.domain.mapper.EnterpriseMapper;
import ink.yowyob.business.book.infrastructure.entity.constants.Type;
import ink.yowyob.business.book.domain.service.ResearchService;
import ink.yowyob.business.book.presentation.dto.enterprise.EnterpriseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/research")
@RequiredArgsConstructor
public class ResearchController {

    private final ResearchService researchService;
    private final EnterpriseMapper enterpriseMapper;

    @GetMapping("/all")
    public Flux<EnterpriseDto> getAllResearch() {
        return researchService.findAll()
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/all-size")
    public Flux<EnterpriseDto> findAll(@RequestParam int size) {
        return researchService.findAll(size)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/type")
    public Flux<EnterpriseDto> findByType(
            @RequestParam Type type) {
        return researchService.findByType(type)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/short-name")
    public Flux<EnterpriseDto> findByShortName(
            @RequestParam String shortName) {
        return researchService.findByShortName(shortName)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/long-name")
    public Flux<EnterpriseDto> findByLongName(
            @RequestParam String longName) {
        return researchService.findByLongName(longName)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/keyword")
    public Flux<EnterpriseDto> findByKeyword(
            @RequestParam String keyword) {
        return researchService.findByKeyword(keyword)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/capital-share")
    public Flux<EnterpriseDto> findByCapitalShare(
            @RequestParam double capitalShare) {
        return researchService.findByCapitalShare(capitalShare)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/year-founded-min")
    public Flux<EnterpriseDto> findByYearFoundedMin(
            @RequestParam int yearFoundedMin) {
        return researchService.findByYearFoundedMin(yearFoundedMin)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/year-founded-max")
    public Flux<EnterpriseDto> findByYearFoundedMax(
            @RequestParam int yearFoundedMax) {
        return researchService.findByYearFoundedMax(yearFoundedMax)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/employees-min")
    public Flux<EnterpriseDto> findByNumberOfEmployeesMin(
            @RequestParam int numberOfEmployeesMin) {
        return researchService.findByNumberOfEmployeesMin(numberOfEmployeesMin)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/employees-max")
    public Flux<EnterpriseDto> findByNumberOfEmployeesMax(
            @RequestParam int numberOfEmployeesMax) {
        return researchService.findByNumberOfEmployeesMax(numberOfEmployeesMax)
                .map(enterpriseMapper::toEnterpriseDto);
    }

    @GetMapping("/business-registration-number")
    public Flux<EnterpriseDto> findByBusinessRegistrationNumber(
            @RequestParam String registrationNumber) {
        return researchService.findByBusinessRegistrationNumber(registrationNumber)
                .map(enterpriseMapper::toEnterpriseDto);
    }
}