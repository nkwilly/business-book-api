package com.business.book.presentation.dto;

import com.business.book.infrastructure.entity.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateEnterpriseDto {

    @JsonProperty("long_name")
    //@JsonAlias("longName")
    private String longName;

    @JsonProperty("short_name")
    //@JsonAlias("shortName")
    private String shortName;

    @JsonProperty("logo_url")
    //@JsonAlias("logoUrl")
    private String logoUrl;

    @JsonProperty("is_individual_business")
    //@JsonAlias("isIndividualBusiness")
    private boolean isIndividualBusiness;

    @JsonProperty("description")
    private String description;

    @JsonProperty("type")
    private String type;

    @JsonProperty("is_active")
    //@JsonAlias("isActive")
    private boolean isActive;

    @JsonProperty("website_url")
    //@JsonAlias("websiteUrl")
    private String websiteUrl;

    @JsonProperty("business_registration_number")
    //@JsonAlias("businessRegistrationNumber")
    private String businessRegistrationNumber;

    @JsonProperty("tax_number")
    //@JsonAlias("taxNumber")
    private String taxNumber;

    @JsonProperty("capital_share")
    //@JsonAlias("capitalShare")
    private double capitalShare;

    @JsonProperty("registration_date")
    //@JsonAlias("registrationDate")
    private Date registrationDate;

    @JsonProperty("ceo_name")
    //@JsonAlias("ceoName")
    private String ceoName;

    @JsonProperty("year_founded")
    //@JsonAlias("yearFounded")
    private Date yearFounded;

    @JsonProperty("keywords")
    private List<String> keywords;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("business_domains")
    //@JsonAlias("businessDomains")
    private List<UUID> businessDomains;

    @JsonProperty("org_contact")
    //@JsonAlias("orgContact")
    private String orgContact;

    @JsonProperty("email")
    private String email;

    @JsonProperty("social_network")
    //@JsonAlias("socialNetwork")
    private String socialNetwork;

    @JsonProperty("number_of_employees")
    //@JsonAlias("numberOfEmployees")
    private int numberOfEmployees;

    @JsonProperty("business_actor_id")
    private UUID businessActorId;
}
