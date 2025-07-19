package com.business.book.presentation.controller;

import com.business.book.domain.service.CommunicationWithOrganizationAPI;
import com.business.book.infrastructure.entity.*;
import com.business.book.infrastructure.repository.*;
import com.business.book.utils.GeneratorsUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    private final EnterpriseRepository enterpriseRepository;
    private final ActivityRepository activityRepository;
    private final AgencyRepository agencyRepository;

    private final CertificationRepository certificationRepository;
    private final ImageNameRepository imageNameRepository;
    private final PracticalInformationRepository practicalInformationRepository;
    private final ProspectRepository prospectRepository;
    private final TokenRepository tokenRepository;

    private final CommunicationWithOrganizationAPI communicationAPI;

    @GetMapping
    public Flux<?> test() {
        List<String> images = List.of("https://cdn.vectorstock.com/i/1000v/08/24/world-globe-earth-vector-1800824.jpg", "https://power-egy.com/wp-content/uploads/2021/08/power-new-logo-01.png",
                "https://fr.egopowerplus.be/themes/egopowerplus/images/avf-background-card-sm.png", "https://static.vecteezy.com/system/resources/previews/026/294/694/large_2x/architecture-modern-building-photo.jpg",
                "https://www.sustainableplaces.eu/wp-content/uploads/2017/02/SmartBuilding.jpg");
        return enterpriseRepository.findAll()
                .flatMap(enterprise -> {
                    Certification certification = Certification.builder()
                            .certificationId(GeneratorsUtils.GenerateUUID())
                            .organizationId(enterprise.getOrganizationId())
                            .type("ISO")
                            .name("ISO 9001")
                            .description("Quality management certification")
                            .obtainementDate(new Date())
                            .createdAt(new Date())
                            .updatedAt(new Date())
                            .createdBy("admin")
                            .deletedAt(null)
                            .updatedBy("admin")
                            .build();

                    Random random = new Random();
                    String randomImageUrl = images.get(random.nextInt(images.size()));

                    ImageName imageName = ImageName.builder()
                            .imageId(GeneratorsUtils.GenerateUUID())
                            .organizationId(enterprise.getOrganizationId())
                            .name(randomImageUrl)
                            .build();

                    PracticalInformation info = PracticalInformation.builder()
                            .informationId(GeneratorsUtils.GenerateUUID())
                            .organizationId(enterprise.getOrganizationId())
                            .type("address")
                            .value("123 Main St")
                            .notes("Head office")
                            .createdAt(new Date())
                            .updatedAt(new Date())
                            .createdBy("admin")
                            .deletedAt(null)
                            .updatedBy("admin")
                            .build();

                    Prospect prospect = Prospect.builder()
                            .prospectId(GeneratorsUtils.GenerateUUID())
                            .firstName("John")
                            .lastName("Doe")
                            .email("john.doe@example.com")
                            .phoneNumber("+123456789")
                            .organizationId(enterprise.getOrganizationId())
                            .createdAt(new Date())
                            .updatedAt(new Date())
                            .status("NEW")
                            .build();

                    return Flux.concat(
                            certificationRepository.save(certification),
                            imageNameRepository.save(imageName),
                            practicalInformationRepository.save(info),
                            practicalInformationRepository.save(info),
                            prospectRepository.save(prospect)

                    );
                });
    }

    @GetMapping("/token/{token}")
    public Mono<?> validateToken(@PathVariable String token) {
        Token newToken = Token.builder()
                .id(GeneratorsUtils.GenerateUUID())
                .userId(GeneratorsUtils.GenerateUUID())
                .roles("USER")
                .content(token)
                .saveAt(Instant.now())
                .expireAt(Instant.now().plusSeconds(3600))
                .build();
        tokenRepository.deleteAll();
        return tokenRepository.save(newToken);
    }

    @GetMapping("/update-enterprise")
    public Flux<?> updateEnterprise() {
        communicationAPI.updateAllEnterprisesInDatabase();
        return enterpriseRepository.findAll();
    }

    @GetMapping("delete-all")
    public Mono<Void> deleteAll() {
        return Flux.concat(
                activityRepository.deleteAll(),
                agencyRepository.deleteAll(),
                certificationRepository.deleteAll(),
                imageNameRepository.deleteAll(),
                practicalInformationRepository.deleteAll(),
                prospectRepository.deleteAll()
        ).then();
    }


}