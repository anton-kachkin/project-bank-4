package com.bank.antifraud.controllers.external;

import com.bank.antifraud.dto.external.PhoneClientTransferDto;
import com.bank.antifraud.service.external.PhoneClientTransferService;
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

import static com.bank.utils.external.TestFactoryClientTransfer.createPhoneClientTransferDto;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneClientTransferControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PhoneClientTransferService phoneClientService;

    static PhoneClientTransferDto phoneClientTransferDto;

    @BeforeEach
    public void setUp() {
        phoneClientTransferDto = createPhoneClientTransferDto();
    }

    @Test
    @DisplayName("Тест на успешное получение PhoneTransfer с ID 1")
    public void testPositiveGetPhoneTransfer() throws Exception {
        PhoneClientTransferDto phoneTransferDto = new PhoneClientTransferDto(
                1L,
                79996544332L,
                BigDecimal.valueOf(200.32),
                "Возврат денежных средств",
                220L
        );

        when(phoneClientService.getPhoneClientTransfer(1L))
                .thenReturn(phoneTransferDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/phone/read/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value("79996544332"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(200.32))
                .andExpect(MockMvcResultMatchers.jsonPath("$.purpose").value("Возврат денежных средств"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountDetailsId").value(220));
    }

    @Test
    @DisplayName("Тест на случай запроса несуществующего URL")
    public void testNegativeNonExistentUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/nonexistent/url"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
