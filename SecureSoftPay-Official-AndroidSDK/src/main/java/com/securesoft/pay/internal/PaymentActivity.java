package com.securesoft.pay.internal;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.securesoft.pay.R;
import com.securesoft.pay.SecureSoftPay;

public final class PaymentActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "payment_url";
    private WebView webView;
    private ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        webView = findViewById(R.id.paymentWebView);
        progressBar = findViewById(R.id.progressBar);

        String url = getIntent().getStringExtra(EXTRA_URL);

        if (url == null || url.isEmpty()) {
            SecureSoftPay.onPaymentFailure("Payment URL was not provided.");
            finish();
            return;
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new PaymentWebViewClient());
        webView.loadUrl(url);
    }

    private class PaymentWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();

            if ("com.securesoft.pay.callback".equals(scheme) && "payment-result".equals(host)) {
                if ("/success".equals(path)) {
                    String transactionId = uri.getQueryParameter("transaction_id");
                    if (transactionId != null && !transactionId.isEmpty()) {
                        SecureSoftPay.onPaymentSuccess(transactionId);
                    } else {
                        SecureSoftPay.onPaymentFailure("Payment successful, but transaction ID was missing.");
                    }
                } else {
                    SecureSoftPay.onPaymentFailure("Payment was cancelled or failed.");
                }
                finish();
                return true;
            }

            return false;
        }
    }

    @Override
    public void onBackPressed() {
        SecureSoftPay.onPaymentFailure("Payment was cancelled by the user.");
        super.onBackPressed();
    }
}