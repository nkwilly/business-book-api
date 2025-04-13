package com.business.book;

import com.business.book.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class CustomerTest {
    @Test
    public void testCustomer() {
        String customerJson =
                "{\n" +
                        "  \"id\": \"123e4567-e89b-12d3-a456-426614174000\",\n" +
                        "  \"email\": \"user@example.com\",\n" +
                        "  \"username\": \"johndoe\",\n" +
                        "  \"tel\": \"+33612345678\",\n" +
                        "  \"password\": \"mySecretPassword123\",\n" +
                        "  \"createdAt\": \"2024-05-20T10:30:00.000+00:00\",\n" +
                        "  \"updatedAt\": \"2024-05-21T15:45:22.123+00:00\",\n" +
                        "  \"createdBy\": \"987e6543-e21b-12d3-a456-426614174999\",\n" +
                        "  \"lastModifiedBy\": \"987e6543-e21b-12d3-a456-426614174999\"\n" +
                        "}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            User customer = mapper.readerFor(User.class)
                    .readValue(customerJson);
            System.out.println(customer);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
