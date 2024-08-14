package com.bank.history;

import com.bank.common.handler.GlobalRestExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class HistoryApplicationTest {

    @InjectMocks
    private GlobalRestExceptionHandler globalRestExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGlobalRestExceptionHandlerBeanIsNotNull() {
        assertNotNull(globalRestExceptionHandler, "globalRestExceptionHandler bean should not be null");
    }
}