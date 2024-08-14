package com.bank.profile.util;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.AuditDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.RegistrationDto;

/**
 * Интерфейс для создания объектов DTO с предустановленными тестовыми значениями.
 * Используется для генерации тестовых данных в тестах и примерах.
 */
public interface DtoFactory {

    AccountDetailsIdDto createAccountDetailsIdDto();

    ActualRegistrationDto createActualRegistrationDto();

    AuditDto createAuditDto();

    PassportDto createPassportDto();

    ProfileDto createProfileDto();

    RegistrationDto createRegistrationDto();
}
