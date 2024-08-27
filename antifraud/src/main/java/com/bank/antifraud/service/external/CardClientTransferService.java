package com.bank.antifraud.service.external;

import com.bank.antifraud.dto.external.CardClientTransferDto;

public interface CardClientTransferService {

    CardClientTransferDto getCardClientTransfer(Long cardClientTransferId);
}
