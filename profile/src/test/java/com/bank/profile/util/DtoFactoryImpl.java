package com.bank.profile.util;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.AuditDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.RegistrationDto;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
public class DtoFactoryImpl implements DtoFactory {

    private static final String DEFAULT_STRING = "String";
    private static final long DEFAULT_LONG = 1L;
    private final DtoFactory dtoFactory;

    @Override
    public AccountDetailsIdDto createAccountDetailsIdDto() {
        final AccountDetailsIdDto dto = new AccountDetailsIdDto();
        dto.setId(DEFAULT_LONG);
        dto.setAccountId(DEFAULT_LONG);
        dto.setProfile(dtoFactory.createProfileDto());
        return dto;
    }

    @Override
    public ActualRegistrationDto createActualRegistrationDto() {
        final ActualRegistrationDto dto = new ActualRegistrationDto();
        dto.setId(DEFAULT_LONG);
        dto.setCountry(DEFAULT_STRING);
        dto.setRegion(DEFAULT_STRING);
        dto.setCity(DEFAULT_STRING);
        dto.setDistrict(DEFAULT_STRING);
        dto.setLocality(DEFAULT_STRING);
        dto.setStreet(DEFAULT_STRING);
        dto.setHouseNumber(DEFAULT_STRING);
        dto.setHouseBlock(DEFAULT_STRING);
        dto.setFlatNumber(DEFAULT_STRING);
        dto.setIndex(DEFAULT_LONG);
        return dto;
    }

    @Override
    public AuditDto createAuditDto() {
        final AuditDto dto = new AuditDto();
        dto.setId(DEFAULT_LONG);
        dto.setEntityType(DEFAULT_STRING);
        dto.setOperationType(DEFAULT_STRING);
        dto.setCreatedBy(DEFAULT_STRING);
        dto.setModifiedBy(DEFAULT_STRING);
        dto.setCreatedAt(Timestamp.valueOf(LocalDateTime.MAX));
        dto.setModifiedAt(Timestamp.valueOf(LocalDateTime.MAX));
        dto.setNewEntityJson(DEFAULT_STRING);
        dto.setEntityJson(DEFAULT_STRING);
        return dto;
    }

    @Override
    public PassportDto createPassportDto() {
        final PassportDto dto = new PassportDto();
        dto.setId(DEFAULT_LONG);
        dto.setSeries(1);
        dto.setNumber(DEFAULT_LONG);
        dto.setLastName(DEFAULT_STRING);
        dto.setFirstName(DEFAULT_STRING);
        dto.setMiddleName(DEFAULT_STRING);
        dto.setGender(DEFAULT_STRING);
        dto.setBirthDate(dto.getBirthDate());
        dto.setBirthPlace(DEFAULT_STRING);
        dto.setIssuedBy(DEFAULT_STRING);
        dto.setDateOfIssue(dto.getDateOfIssue());
        dto.setDivisionCode(1);
        dto.setExpirationDate(dto.getExpirationDate());
        dto.setRegistration(dtoFactory.createRegistrationDto());
        return dto;
    }

    @Override
    public ProfileDto createProfileDto() {
        final ProfileDto dto = new ProfileDto();
        dto.setId(DEFAULT_LONG);
        dto.setPhoneNumber(DEFAULT_LONG);
        dto.setEmail(DEFAULT_STRING);
        dto.setNameOnCard(DEFAULT_STRING);
        dto.setInn(DEFAULT_LONG);
        dto.setSnils(DEFAULT_LONG);
        dto.setPassport(dtoFactory.createPassportDto());
        dto.setActualRegistration(dtoFactory.createActualRegistrationDto());
        return dto;
    }

    @Override
    public RegistrationDto createRegistrationDto() {
        final RegistrationDto dto = new RegistrationDto();
        dto.setId(DEFAULT_LONG);
        dto.setCountry(DEFAULT_STRING);
        dto.setRegion(DEFAULT_STRING);
        dto.setCity(DEFAULT_STRING);
        dto.setDistrict(DEFAULT_STRING);
        dto.setLocality(DEFAULT_STRING);
        dto.setStreet(DEFAULT_STRING);
        dto.setHouseNumber(DEFAULT_STRING);
        dto.setHouseBlock(DEFAULT_STRING);
        dto.setFlatNumber(DEFAULT_STRING);
        dto.setIndex(DEFAULT_LONG);
        return dto;
    }
}
