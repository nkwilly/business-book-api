package com.business.book.entity;

import com.business.book.entity.constants.Type;
import com.business.book.entity.constants.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data @ToString @EqualsAndHashCode
@Builder
public class Enterprise {
    @JsonProperty("organization_id")
    private UUID organizationId;

    @JsonProperty("long_name")
    private String longName;

    @JsonProperty("short_name")
    private String shortName;

    @JsonProperty("logo_url")
    private String logoUrl;

    @JsonProperty("is_individual_business")
    private boolean isIndividualBusiness;

    @JsonProperty("description")
    private String description;

    @JsonProperty("type")
    private Type type;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("website_url")
    private String websiteUrl;

    @JsonProperty("business_registration_number")
    private String businessRegistrationNumber;

    @JsonProperty("tax_number")
    private String taxNumber;

    @JsonProperty("capital_share")
    private double capitalShare;

    @JsonProperty("registration_date")
    private Date registrationDate;

    @JsonProperty("ceo_name")
    private String ceoName;

    @JsonProperty("year_founded")
    private LocalDateTime yearFounded;

    @JsonProperty("keywords")
    private List<String> keywords;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("business_domains")
    private List<UUID> businessDomains;

    @JsonProperty("org_contact")
    private String orgContact;

    @JsonProperty("email")
    private String email;

    @JsonProperty("social_network")
    private String socialNetwork;

    @JsonProperty("number_of_employees")
    private int numberOfEmployees;

    @JsonProperty("business_actor_id")
    private UUID businessActorId;

    @JsonProperty("created_at")
    private LocalDateTime createAt;

    @JsonProperty("updated_at")
    private LocalDateTime updateAt;

    @JsonProperty("deleted_at")
    private LocalDateTime deleteAt;

    @JsonProperty("created_by")
    private UUID createBy;

    @JsonProperty("updated_by")
    private UUID updateBy;
}