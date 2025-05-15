package com.business.book;

import com.business.book.entity.Domain;
import com.business.book.entity.Enterprise;
import com.business.book.entity.EnterpriseData;
import com.business.book.entity.Token;
import com.business.book.entity.constants.DomainName;
import com.business.book.entity.constants.Status;
import com.business.book.entity.constants.Type;
import com.business.book.service.payload.request.*;
import com.business.book.repository.*;
import com.business.book.service.CommunicationWithOrganizationAPI;
import com.business.book.service.ResearchService;
import com.business.book.service.impl.CommunicationWithOrganizationAPIImpl;
import com.business.book.service.impl.ResearchServiceImpl;
import com.business.book.service.utils.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.*;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResearchServiceTest {

    private ResearchService researchService;

    private CommunicationWithOrganizationAPI organizationService;

    @InjectMocks
    private SecurityUtils securityUtils;

    @Mock
    private WebClient webClient;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EnterpriseDataRepository dataRepository;

    private List<Enterprise> enterprises = new ArrayList<>();

    @BeforeEach
    void setUp() {

        organizationService = new CommunicationWithOrganizationAPIImpl(
                webClient, securityUtils, userRepository, tokenRepository, dataRepository
        );

        researchService = new ResearchServiceImpl(organizationService, dataRepository);

        IntStream.range(1, 21).forEach(i -> {
            Enterprise enterprise = new Enterprise();

            enterprise.setOrganizationId(UUID.randomUUID());
            enterprise.setLongName("Entreprise LongName " + i);
            enterprise.setShortName("Short" + i);
            enterprise.setLogoUrl("https://example.com/logo" + i + ".png");
            enterprise.setIndividualBusiness(i % 2 == 0);
            enterprise.setDescription("Description de l'entreprise " + i);
            enterprise.setType(Type.CREDITS); // À adapter selon ton enum/class
            enterprise.setActive(true);
            enterprise.setWebsiteUrl("https://entreprise" + i + ".com");
            enterprise.setBusinessRegistrationNumber("REG" + 1000 + i);
            enterprise.setTaxNumber("TAX" + 2000 + i);
            enterprise.setCapitalShare(10000.0 + (i * 1000));
            enterprise.setRegistrationDate(new Date());
            enterprise.setCeoName("CEO " + i);
            //enterprise.setYearFounded(new GregorianCalendar(2000 + (i % 20), 0, 1).getTime());
            enterprise.setKeywords(List.of("innovation", "tech", "service" + i));
            enterprise.setStatus(Status.SOCIETE_ANONYME); // À adapter selon ton enum/class
            //enterprise.setBusinessDomains(List.of(new Domain(UUID.randomUUID(), DomainName.LLC, "desc")));
            enterprise.setOrgContact("contact" + i + "@entreprise.com");
            enterprise.setEmail("info" + i + "@entreprise.com");
            enterprise.setNumberOfEmployees(10 + i * 5);
            enterprise.setBusinessActorId(UUID.randomUUID());

            this.enterprises.add(enterprise);

            EnterpriseData data = new EnterpriseData();
            data.setEnterpriseId(enterprise.getOrganizationId());
            data.setViewsNumbers((long) i);
            data.setEnterpriseId(UUID.randomUUID());

            dataRepository.save(data);
        });

        Enterprise enterprise = new Enterprise();
        enterprise.setOrganizationId(UUID.randomUUID());
        enterprise.setLongName("Entreprise LongName " + 1);
        enterprise.setShortName("Short" + 1);
        enterprise.setLogoUrl("https://example.com/logo" + 1 + ".png");
        enterprise.setIndividualBusiness(false);
        enterprise.setDescription("Description de l'entreprise ");
        enterprise.setType(Type.CREDITS);
        enterprise.setActive(true);
        enterprise.setWebsiteUrl("https://entreprise" + 1 + ".com");
        enterprise.setBusinessRegistrationNumber("REG" + 1000 + 1);
        enterprise.setTaxNumber("TAX" + 2000 + 1);
        enterprise.setCapitalShare(100000.0);
        enterprise.setRegistrationDate(new Date());
        enterprise.setCeoName("CEO " + 1);
        //enterprise.setYearFounded(new GregorianCalendar(2000 + 1, 1, 1).getTime());
        enterprise.setBusinessActorId(UUID.randomUUID());
        enterprise.setStatus(Status.SOCIETE_ANONYME);
        //enterprise.setBusinessDomains(List.of(new Domain(UUID.randomUUID(), DomainName.LLC, "desc")));
        enterprise.setOrgContact("contact" + 1 + "@entreprise.com");
        enterprise.setEmail("info" + 1 + "@entreprise.com");
        enterprise.setNumberOfEmployees(10 + 5);

        Enterprise updatedEnterprise = enterprise;
        updatedEnterprise.setDescription("UpdatedDescription");

        Token mockedToken = new Token();
        mockedToken.setId(UUID.randomUUID());
        mockedToken.setToken(UUID.randomUUID().toString());
        mockedToken.setUserId(UUID.randomUUID());
        mockedToken.setRoles("ROLE_ADMIN");
        mockedToken.setSaveAt(Instant.now());

        WebClient.RequestBodyUriSpec webClient1 = Mockito.mock(WebClient.RequestBodyUriSpec.class);


        //when(organizationService.createEnterprise(Mockito.any(Enterprise.class)))
          //      .thenReturn(enterprise);

        when(organizationService.register(Mockito.any(RegisterRequest.class)))
                .thenReturn(mockedToken);

        when(organizationService.login(Mockito.any(LoginRequest.class)))
                .thenReturn(mockedToken);

        when(organizationService.updateEnterprise(Mockito.any(Enterprise.class)))
                .thenReturn(updatedEnterprise);

        when(organizationService.deleteEnterprise(Mockito.any(Enterprise.class)))
                .thenReturn(true);

        when(organizationService.getEnterpriseById(Mockito.any(UUID.class)))
                .thenReturn(enterprise);

        when(organizationService.getAllEnterprise())
                .thenReturn(enterprises);

        when(webClient.post().retrieve().bodyToMono(Enterprise.class).block())
                .thenReturn(enterprise);

        System.out.println( "Enterprise : " + enterprises.get(0).toString());

        List<EnterpriseData> datas = dataRepository.findAll();

        System.out.println("Enterprise Data : " + datas.get(0).toString());
    }

    @Test
    void findAll() {
        // Testez réellement quelque chose
        //List<Enterprise> result = researchService.findAll();
        //assertNotNull(result);
        //assertEquals(20, result.size());*
        System.out.println("bonjour le monde");
    }

    @AfterEach
    void tearDown() {
        // Nettoyez la base de données après chaque test
        dataRepository.deleteAll();
    }
}
