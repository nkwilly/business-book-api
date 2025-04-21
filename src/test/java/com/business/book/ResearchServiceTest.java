package com.business.book;

import com.business.book.entity.Domain;
import com.business.book.entity.Enterprise;
import com.business.book.entity.EnterpriseData;
import com.business.book.entity.constants.DomainName;
import com.business.book.entity.constants.Status;
import com.business.book.entity.constants.Type;
import com.business.book.repository.EnterpriseDataRepository;
import com.business.book.service.ResearchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class ResearchServiceTest {
    @Autowired
    ResearchService researchService;

    @Autowired
    EnterpriseDataRepository dataRepository;

    private List<Enterprise> enterprises;

    @BeforeEach
    void setUp() {

        IntStream.range(1, 21).forEach(i -> {
            Enterprise enterprise = new Enterprise();

            enterprise.setId(UUID.randomUUID());
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
            enterprise.setYearFounded(new GregorianCalendar(2000 + (i % 20), 0, 1).getTime());
            enterprise.setKeywords(List.of("innovation", "tech", "service" + i));
            enterprise.setStatus(Status.SOCIETE_ANONYME); // À adapter selon ton enum/class
            enterprise.setBusinessDomains(List.of(new Domain(UUID.randomUUID(), DomainName.LLC, "desc")));
            enterprise.setOrgContact("contact" + i + "@entreprise.com");
            enterprise.setEmail("info" + i + "@entreprise.com");
            enterprise.setNumberOfEmployees(10 + i * 5);
            enterprise.setBusinessActorId(UUID.randomUUID());

            this.enterprises.add(enterprise);

            EnterpriseData data = new EnterpriseData();
            data.setEnterpriseId(enterprise.getId());
            data.setViewsNumbers((long) i);
            data.setId(UUID.randomUUID());

            dataRepository.save(data);
        });


        System.out.println( "Enterprise : " + enterprises.get(0).toString());

        List<EnterpriseData> datas = dataRepository.findAll();

        System.out.println("Enterprise Data : " + datas.get(0).toString());
    }

    @Test
    void findAll() {
        // Testez réellement quelque chose
        //List<Enterprise> result = researchService.findAll();
        //assertNotNull(result);
        //assertEquals(20, result.size());
    }

    @AfterEach
    void tearDown() {
        // Nettoyez la base de données après chaque test
        dataRepository.deleteAll();
    }
}
