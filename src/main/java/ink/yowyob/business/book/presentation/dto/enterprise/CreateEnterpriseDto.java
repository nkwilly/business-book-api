package ink.yowyob.business.book.presentation.dto.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ink.yowyob.business.book.infrastructure.entity.constants.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateEnterpriseDto {

    private String longName;

    private String shortName;

    private String logoUrl;

    private boolean isIndividualBusiness;

    private String description;

    private String type;

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

    private List<UUID> businessDomains;

    private String orgContact;

    private String email;

    private String socialNetwork;

    private int numberOfEmployees;

    private UUID businessActorId;
}
