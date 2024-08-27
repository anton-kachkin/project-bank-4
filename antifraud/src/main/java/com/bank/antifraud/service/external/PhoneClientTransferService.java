package com.bank.antifraud.service.external;

import com.bank.antifraud.dto.external.PhoneClientTransferDto;

public interface PhoneClientTransferService {

    PhoneClientTransferDto getPhoneClientTransfer(Long phoneClientTransferId);
}
