package com.bank.profile.util;

import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;

/**
 * Интерфейс для создания объектов сущностей с предустановленными тестовыми значениями.
 * Используется для генерации тестовых данных в тестах и примерах.
 */
public interface EntityFactory {

    AccountDetailsIdEntity createAccountDetailsIdEntity();

    ActualRegistrationEntity createActualRegistrationEntity();

    AuditEntity createAuditEntity();

    PassportEntity createPassportEntity();

    ProfileEntity createProfileEntity();

    RegistrationEntity createRegistrationEntity();
}
