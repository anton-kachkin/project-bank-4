package com.bank.profile.util;

import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
public class EntityFactoryImpl implements EntityFactory {

    private static final String DEFAULT_STRING = "String";
    private static final long DEFAULT_LONG = 1L;
    private final EntityFactory entityFactory;

    @Override
    public AccountDetailsIdEntity createAccountDetailsIdEntity() {
        final AccountDetailsIdEntity entity = new AccountDetailsIdEntity();
        entity.setId(DEFAULT_LONG);
        entity.setProfile(entityFactory.createProfileEntity());
        entity.setAccountId(DEFAULT_LONG);
        return entity;
    }

    @Override
    public ActualRegistrationEntity createActualRegistrationEntity() {
        final ActualRegistrationEntity entity = new ActualRegistrationEntity();
        entity.setId(DEFAULT_LONG);
        entity.setCountry(DEFAULT_STRING);
        entity.setRegion(DEFAULT_STRING);
        entity.setCity(DEFAULT_STRING);
        entity.setDistrict(DEFAULT_STRING);
        entity.setLocality(DEFAULT_STRING);
        entity.setStreet(DEFAULT_STRING);
        entity.setHouseNumber(DEFAULT_STRING);
        entity.setHouseBlock(DEFAULT_STRING);
        entity.setFlatNumber(DEFAULT_STRING);
        entity.setIndex(DEFAULT_LONG);
        return entity;
    }

    @Override
    public AuditEntity createAuditEntity() {
        final AuditEntity entity = new AuditEntity();
        entity.setId(DEFAULT_LONG);
        entity.setEntityType(DEFAULT_STRING);
        entity.setOperationType(DEFAULT_STRING);
        entity.setCreatedBy(DEFAULT_STRING);
        entity.setModifiedBy(DEFAULT_STRING);
        entity.setCreatedAt(Timestamp.valueOf(LocalDateTime.MAX));
        entity.setModifiedAt(Timestamp.valueOf(LocalDateTime.MAX));
        entity.setNewEntityJson(DEFAULT_STRING);
        entity.setEntityJson(DEFAULT_STRING);
        return entity;
    }

    @Override
    public PassportEntity createPassportEntity() {
        final PassportEntity entity = new PassportEntity();
        entity.setId(DEFAULT_LONG);
        entity.setSeries(1);
        entity.setNumber(DEFAULT_LONG);
        entity.setLastName(DEFAULT_STRING);
        entity.setFirstName(DEFAULT_STRING);
        entity.setMiddleName(DEFAULT_STRING);
        entity.setGender(DEFAULT_STRING);
        entity.setBirthDate(entity.getBirthDate());
        entity.setBirthPlace(DEFAULT_STRING);
        entity.setIssuedBy(DEFAULT_STRING);
        entity.setDateOfIssue(entity.getDateOfIssue());
        entity.setDivisionCode(1);
        entity.setExpirationDate(entity.getExpirationDate());
        entity.setRegistration(entityFactory.createRegistrationEntity());
        return entity;
    }

    @Override
    public ProfileEntity createProfileEntity() {
        final ProfileEntity entity = new ProfileEntity();
        entity.setId(DEFAULT_LONG);
        entity.setPhoneNumber(DEFAULT_LONG);
        entity.setEmail(DEFAULT_STRING);
        entity.setNameOnCard(DEFAULT_STRING);
        entity.setInn(DEFAULT_LONG);
        entity.setSnils(DEFAULT_LONG);
        entity.setPassport(entityFactory.createPassportEntity());
        entity.setActualRegistration(entityFactory.createActualRegistrationEntity());
        return entity;
    }

    @Override
    public RegistrationEntity createRegistrationEntity() {
        final RegistrationEntity entity = new RegistrationEntity();
        entity.setId(DEFAULT_LONG);
        entity.setCountry(DEFAULT_STRING);
        entity.setRegion(DEFAULT_STRING);
        entity.setCity(DEFAULT_STRING);
        entity.setDistrict(DEFAULT_STRING);
        entity.setLocality(DEFAULT_STRING);
        entity.setStreet(DEFAULT_STRING);
        entity.setHouseNumber(DEFAULT_STRING);
        entity.setHouseBlock(DEFAULT_STRING);
        entity.setFlatNumber(DEFAULT_STRING);
        entity.setIndex(DEFAULT_LONG);
        return entity;
    }
}
