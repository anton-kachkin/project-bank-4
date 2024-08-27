package com.bank.antifraud.service.external;

import com.bank.antifraud.dto.external.CardClientTransferDto;
import com.bank.antifraud.feign.TransferClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardClientTransferServiceImpl implements CardClientTransferService {

    TransferClient transferClient;

    @Override
    public CardClientTransferDto getCardClientTransfer(Long cardClientTransferId) {
        return transferClient.getCardClientTransfer(cardClientTransferId);
    }
}
