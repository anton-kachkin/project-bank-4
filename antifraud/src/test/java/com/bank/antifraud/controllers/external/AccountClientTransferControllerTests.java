package com.bank.antifraud.controllers.external;

import com.bank.antifraud.dto.external.AccountClientTransferDto;
import com.bank.antifraud.service.external.AccountClientTransferService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static com.bank.utils.external.TestFactoryClientTransfer.createAccountClientTransferDto;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountClientTransferControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountClientTransferService accountClientService;

    static AccountClientTransferDto accountClientTransferDto;

    @BeforeEach
    public void setUp() {
        accountClientTransferDto = createAccountClientTransferDto();
    }

    @Test
    @DisplayName("Тест на успешное получение AccountClientTransfer с ID 1")
    public void testPositiveGetAccountTransfer() throws Exception {
        AccountClientTransferDto accountClientTransferDto = new AccountClientTransferDto(
                1L,
                4243242L,
                BigDecimal.valueOf(250.75),
                "Ежемесячный платеж",
                456L
        );

        when(accountClientService.getAccountClientTransfer(1L))
                .thenReturn(accountClientTransferDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/account/read/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(4243242))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(250.75))
                .andExpect(MockMvcResultMatchers.jsonPath("$.purpose")
                        .value("Ежемесячный платеж"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountDetailsId").value(456));
    }

    @Test
    @DisplayName("Тест на случай некорректного формата ID для AccountClientTransfer")
    public void testNegativeInvalidAccountClientTransferId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account/read/invalid"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
