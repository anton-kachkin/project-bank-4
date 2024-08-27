package com.bank.antifraud.controllers.external;

import com.bank.antifraud.dto.external.CardClientTransferDto;
import com.bank.antifraud.service.external.CardClientTransferService;
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

import static com.bank.utils.external.TestFactoryClientTransfer.createCardClientTransferDto;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardClientTransferControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CardClientTransferService cardClientService;

    static CardClientTransferDto cardClientTransferDto;

    @BeforeEach
    public void setUp() {
        cardClientTransferDto = createCardClientTransferDto();
    }

    @Test
    @DisplayName("Тест на успешное получение CardClientTransfer с ID 1")
    public void testPositiveGetCardClientTransfer() throws Exception {
        CardClientTransferDto cardClientTransferDto = new CardClientTransferDto(
                1L,
                3254765436546785L,
                BigDecimal.valueOf(350.20),
                "Оплата за услуги",
                321L
        );

        when(cardClientService.getCardClientTransfer(1L))
                .thenReturn(cardClientTransferDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/card/read/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardNumber")
                        .value(3254765436546785L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(350.20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.purpose")
                        .value("Оплата за услуги"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountDetailsId").value(321));
    }

    @Test
    @DisplayName("Тест на случай отсутствия параметра ID для CardClientTransfer")
    public void testNegativeMissingCardClientTransferId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/card/read/"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
