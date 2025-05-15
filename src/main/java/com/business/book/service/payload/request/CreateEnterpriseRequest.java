package com.business.book.service.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEnterpriseRequest {

    @JsonProperty("long_name")
    private String longName;

    @JsonProperty("short_name")
    private String shortName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("description")
    private String description;

    @JsonProperty("business_domains")
    private List<UUID> businessDomains;

    @JsonProperty("logo_url")
    private String logoUrl;

    @JsonProperty("type")
    private String type;

    @JsonProperty("web_site_url")
    private String webSiteUrl;

    @JsonProperty("social_network")
    private String socialNetwork;

    @JsonProperty("business_registration_number")
    private String businessRegistrationNumber;

    @JsonProperty("tax_number")
    private String taxNumber;

    @JsonProperty("capital_share")
    private int capitalShare;

    @JsonProperty("registration_date")
    private LocalDateTime registrationDate;

    @JsonProperty("ceo_name")
    private String ceoName;

    @JsonProperty("year_founded")
    private LocalDateTime yearFounded;

    @JsonProperty("keywords")
    private List<String> keywords;

    @JsonProperty("number_of_employees")
    private int numberOfEmployees;
}