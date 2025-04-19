package com.business.book.entity;

import com.business.book.entity.constants.Type;
import com.business.book.entity.constants.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data @ToString @EqualsAndHashCode
public class Enterprise {
    private UUID id;

    private String longName;

    private String shortName;

    private String logoUrl;

    private boolean isIndividualBusiness;

    private String description;

    private Type type;

    private boolean isActive;

    private String websiteUrl;

    private String businessRegistrationNumber;

    private String taxNumber;

    private double capitalShare;

    private Date registrationDate;

    private String ceoName;

    private Date yearFounded;

    private List<String> keywords;

    private Status status;

    private List<Domain> businessDomains;

    private String orgContact;

    private String email;

    private int numberOfEmployees;

    private UUID businessActorId;

}