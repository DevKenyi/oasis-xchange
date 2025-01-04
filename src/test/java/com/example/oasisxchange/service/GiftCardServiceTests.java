package com.example.oasisxchange.service;

import com.example.oasisxchange.enums.GiftCardStatus;
import com.example.oasisxchange.model.GiftCard;
import com.example.oasisxchange.model.GiftCardDTO;
import com.example.oasisxchange.model.PaymentInfo;
import com.example.oasisxchange.model.SellGiftCardResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GiftCardServiceTests {

    @SpyBean
    private GiftCardService giftCardService;

    @Test
    @WithMockUser(username = "testuser", roles = {"admin"})
    public void testGiftCardIsProcessedAsSoldWhenValidCardIsProvided() {
        // Arrange: Setup input data for the test
        GiftCardDTO dto = new GiftCardDTO();
        dto.setBrand("Amazon");
        dto.setValue(100);
        dto.setCardNumber("123456789");
        dto.setPin("1234");

        // Create PaymentInfo and set it in the DTO
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentMethod("Bank Transfer");
        paymentInfo.setAccountNumber("12345678");
        paymentInfo.setBankName("UBA");
        paymentInfo.setAccountHolderName("Ocheja Sunday");
        dto.setPaymentInfo(paymentInfo);

        // Spy the verifyGiftCard method and force it to return true for the test
        doReturn(true).when(giftCardService).verifyGiftCard(any(GiftCard.class));

        // Act: Call the method to test
        SellGiftCardResponse response = giftCardService.processGiftCardSale(dto);

        // Assert: Validate the response
        assertNotNull(response, "The response should not be null");
        assertEquals(GiftCardStatus.SOLD, response.getStatus(), "The status should be SOLD");
        assertEquals(70, response.getPayout(), "The payout should be 70");
        assertNotNull(response.getCardId(), "The cardId should not be null");
    }
}