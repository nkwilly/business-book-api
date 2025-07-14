package com.business.book.entity;

import com.business.book.entity.constants.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data @ToString @EqualsAndHashCode
@Builder
@Table("enterprise")
public class Enterprise {
    @JsonProperty("organization_id")
    @PrimaryKey("organization_id")
    private UUID organizationId;

    @JsonProperty("long_name")
    @Column("long_name")
    private String longName;

    @JsonProperty("short_name")
    @Column("short_name")
    private String shortName;

    @JsonProperty("logo_url")
    @Column("logo_url")
    private String logoUrl;

    @JsonProperty("is_individual_business")
    @Column("is_individual_business")
    private boolean isIndividualBusiness;

    @JsonProperty("description")
    @Column("description")
    private String description;

    @JsonProperty("type")
    @Column("type")
    private String type;

    @JsonProperty("is_active")
    @Column("is_active")
    private boolean isActive;

    @JsonProperty("website_url")
    @Column("website_url")
    private String websiteUrl;

    @JsonProperty("business_registration_number")
    @Column("business_registration_number")
    private String businessRegistrationNumber;

    @JsonProperty("tax_number")
    @Column("tax_number")
    private String taxNumber;

    @JsonProperty("capital_share")
    @Column("capital_share")
    private double capitalShare;

    @JsonProperty("registration_date")
    @Column("registration_date")
    private Date registrationDate;

    @JsonProperty("ceo_name")
    @Column("ceo_name")
    private String ceoName;

    @JsonProperty("year_founded")
    @Column("year_founded")
    private Date yearFounded;

    @JsonProperty("keywords")
    @Column("keywords")
    private List<String> keywords;

    @JsonProperty("status")
    @Column("status")
    private Status status;

    @JsonProperty("business_domains")
    @Column("business_domains")
    private List<UUID> businessDomains;

    @JsonProperty("org_contact")
    @Column("org_contact")
    private String orgContact;

    @JsonProperty("email")
    @Column("email")
    private String email;

    @JsonProperty("social_network")
    @Column("social_network")
    private String socialNetwork;

    @JsonProperty("number_of_employees")
    @Column("number_of_employees")
    private int numberOfEmployees;

    @JsonProperty("business_actor_id")
    @Column("business_actor_id")
    private UUID businessActorId;

    @JsonProperty("created_at")
    @Column("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    @Column("updated_at")
    private Date updatedAt;

    @JsonProperty("deleted_at")
    @Column("deleted_at")
    private Date deletedAt;

    @JsonProperty("legal_form")
    @Column("legal_form")
    private int legalForm;

    @JsonProperty("created_by")
    @Column("created_by")
    private String createdBy;

    @JsonProperty("updated_by")
    @Column("updated_by")
    private String updatedBy;
}