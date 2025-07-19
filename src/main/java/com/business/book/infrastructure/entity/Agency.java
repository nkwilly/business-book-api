package com.business.book.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data @Builder
@Table("agency")
@NoArgsConstructor
@AllArgsConstructor
public class Agency {
    @Id
    @Column("agency_id")
    @JsonProperty("agency_id")
    private UUID agencyId;

    @Column("organization_id")
    @JsonProperty("organization_id")
    private UUID organizationId;

    @Column("transferable")
    @JsonProperty("transferable")
    private boolean transferable;

    @Column("business_domains")
    @JsonProperty("business_domains")
    private List<UUID> businessDomains;

    @Column("is_active")
    @JsonProperty("is_active")
    private boolean isActive;

    @Column("logo")
    @JsonProperty("logo")
    private String logo;

    @Column("short_name")
    @JsonProperty("short_name")
    private String shortName;

    @Column("long_name")
    @JsonProperty("long_name")
    private String longName;

    @Column("is_individual_business")
    @JsonProperty("is_individual_business")
    private boolean isIndividualBusiness;

    @Column("is_headquater")
    @JsonProperty("is_headquater")
    private boolean isHeadQuater;

    @Column("images")
    @JsonProperty("images")
    private List<String> images;

    @Column("greeting_message")
    @JsonProperty("greeting_message")
    private String greetingMessage;

    @Column("year_created")
    @JsonProperty("year_created")
    private Date yearCreated;

    @Column("average_revenue")
    @JsonProperty("average_revenue")
    private double averageRevenue;

    @Column("capital_share")
    @JsonProperty("capital_share")
    private double capitalShare;

    @Column("registration_number")
    @JsonProperty("registration_number")
    private String registrationNumber;

    @Column("social_network")
    @JsonProperty("social_network")
    private String socialNetwork;

    @Column("tax_number")
    @JsonProperty("tax_number")
    private String taxNumber;

    @Column("keywords")
    @JsonProperty("keywords")
    private List<String> keywords;

    @Column("is_public")
    @JsonProperty("is_public")
    private boolean isPublic;

    @Column("is_business")
    @JsonProperty("is_business")
    private boolean isBusiness;

    @Column("operation_time_plan")
    @JsonProperty("operation_time_plan")
    private Object operationTimePlan;

    @Column("total_affiliated_customers")
    @JsonProperty("total_affiliated_customers")
    private int totalAffiliatedCustomers;
}