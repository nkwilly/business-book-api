package com.business.book.service;

import com.business.book.entity.Enterprise;
import com.business.book.entity.Token;
import com.business.book.service.payload.request.CreateEnterpriseRequest;
import com.business.book.service.payload.request.LoginRequest;
import com.business.book.service.payload.request.RegisterRequest;

import java.util.List;
import java.util.UUID;

public interface CommunicationWithOrganizationAPI {
    Token login(LoginRequest loginRequest);

    Token register(RegisterRequest registerRequest);

    Enterprise createEnterprise(CreateEnterpriseRequest enterprise);

    Enterprise updateEnterprise(Enterprise enterprise);

    boolean deleteEnterprise(Enterprise enterprise);

    Enterprise getEnterpriseById(UUID id);

    List<Enterprise> getAllEnterprise();
}
