package com.securesoft.pay;

/**
 * Holds the necessary configuration for the Secure Soft Pay SDK.
 */
public final class SecureSoftPayConfig {
    public final String apiKey;
    public final String baseUrl;
    public final String checkoutInitiateUrl;
    public final String verifyUrl;
    public SecureSoftPayConfig(String apiKey, String baseUrl, String checkoutInitiateUrl, String verifyUrl) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.checkoutInitiateUrl = checkoutInitiateUrl;
        this.verifyUrl = verifyUrl;
    }
}