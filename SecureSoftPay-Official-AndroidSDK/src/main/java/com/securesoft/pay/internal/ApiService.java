package com.securesoft.pay.internal;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {
    @POST
    Call<InitiatePaymentResponse> initiatePayment(
            @Url String url,
            @Header("Authorization") String authHeader,
            @Body InitiatePaymentRequestBody requestBody
    );
}