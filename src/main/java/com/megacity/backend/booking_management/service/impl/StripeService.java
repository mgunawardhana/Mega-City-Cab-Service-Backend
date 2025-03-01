package com.megacity.backend.booking_management.service.impl;

import com.megacity.backend.domain.request.ProductRequest;
import com.megacity.backend.domain.response.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.Secretary}")
    private String secretKey;

    public StripeResponse checkProduct(ProductRequest productRequest) {
        Stripe.apiKey = secretKey;

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(productRequest.getName())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "LKR")
                        .setUnitAmount(productRequest.getAmount())
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(productRequest.getQuantity())
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:5173/bill")
                        .setCancelUrl("http://localhost:8080/cancel")
                        .addLineItem(lineItem)
                        .build();

        try {
            Session session = Session.create(params);
            return new StripeResponse(
                    "SUCCESS",
                    "Payment session created successfully.",
                    session.getId(),
                    session.getUrl()
            );
        } catch (StripeException e) {
            e.printStackTrace();
            return new StripeResponse(
                    "ERROR",
                    "Failed to create payment session: " + e.getMessage(),
                    null,
                    null
            );
        }
    }
}
