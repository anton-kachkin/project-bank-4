package com.bank.history.dto;

import com.bank.history.entity.HistoryEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;


/**
 * Dto для {@link HistoryEntity}.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryDto {
    @NotNull(message = "ID cannot be null")
    Long id;
    Long transferAuditId;
    Long profileAuditId;
    Long accountAuditId;
    Long antiFraudAuditId;
    Long publicBankInfoAuditId;
    Long authorizationAuditId;
}
