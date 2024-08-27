package com.bank.antifraud.service.external;

import com.bank.antifraud.dto.external.PhoneClientTransferDto;
import com.bank.antifraud.feign.TransferClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PhoneClientTransferServiceImpl implements PhoneClientTransferService {

    TransferClient transferClient;

    @Override
    public PhoneClientTransferDto getPhoneClientTransfer(Long phoneClientTransferId) {
        return transferClient.getPhoneClientTransfer(phoneClientTransferId);
    }
}
