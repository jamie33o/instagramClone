package com.example.instagramclone.braintree_payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.braintreepayments.api.DropInClient;
import com.braintreepayments.api.DropInListener;
import com.braintreepayments.api.DropInPaymentMethod;
import com.braintreepayments.api.DropInRequest;
import com.braintreepayments.api.DropInResult;
import com.braintreepayments.api.PaymentMethodNonce;
import com.braintreepayments.api.UserCanceledException;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.ParseUtils.UtilsClass;
import com.example.instagramclone.reusable_code.Snackbar_Dialog;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, DropInListener {
    private DropInClient dropInClient;
    RecyclerView pricesRecyleView;
    PricesAdapter pricesAdapter;
    Button payBtn;
    DropInRequest dropInRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        // DropInClient can also be instantiated with a tokenization key
         dropInClient = new DropInClient(this, "sandbox_cs5rdzwp_bcpng56c5yzsxjjs");
         dropInRequest = new DropInRequest();
        dropInClient.setListener(this);



        List<PricesModel> pricesModelList = new ArrayList<>();

        pricesModelList.add(new PricesModel("Popular","1 week","€5.99","10% off"));
        pricesModelList.add(new PricesModel("Best value","1 Month","€15.99","20% off"));
        pricesModelList.add(new PricesModel("Bargain","1 Year","€130","50% off"));


        payBtn = findViewById(R.id.paypal_button);
        payBtn.setOnClickListener(this);
        // Initialize RecyclerView
        pricesRecyleView = findViewById(R.id.payment_recyclerview);
        pricesRecyleView.setLayoutManager(new LinearLayoutManager(this));
        pricesAdapter = new PricesAdapter(this, pricesModelList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        pricesRecyleView.setLayoutManager(layoutManager);
        pricesRecyleView.setAdapter(pricesAdapter);
        SnapHelper snapHelperLikedYouBack = new LinearSnapHelper();
        snapHelperLikedYouBack.attachToRecyclerView(pricesRecyleView);


    }
    void postNonceToServer(String nonce) {
        ParseCloud.callFunctionInBackground("processPayment", new HashMap<String, Object>() {{
            put("payment_method_nonce", nonce);
            put("ID", UtilsClass.getCurrentUsername());
            put("amount", "10.00");
        }}, new FunctionCallback<HashMap<String, Object>>() {
            @Override
            public void done(HashMap<String, Object> result, ParseException e) {
                if (e == null) {
                    // Access the result values and convert them to strings
                    String message = Objects.requireNonNull(result.get("message")).toString();
                    Snackbar_Dialog.showSnackbar(PaymentActivity.this,message,2000);
                } else {
                    Snackbar_Dialog.showSnackbar(PaymentActivity.this,"Error!!!\n"+e.getMessage(),2000);
                }
            }
        });


    }
    private void launchDropIn() {
        dropInClient.launchDropIn(dropInRequest);
        userPayMethodPrefs();
    }

    public void userPayMethodPrefs() {

        dropInClient.fetchMostRecentPaymentMethod(this, (dropInResult, error) -> {
            if (error != null) {
                // an error occurred
            } else if (dropInResult != null) {
                if (dropInResult.getPaymentMethodType() != null) {
                    DropInPaymentMethod paymentMethodType = dropInResult.getPaymentMethodType();

                    // use the icon and name to show in your UI
                    int icon = paymentMethodType.getDrawable();
                    int name = paymentMethodType.getLocalizedName();

                    if (paymentMethodType == DropInPaymentMethod.GOOGLE_PAY) {
                        // The last payment method the user used was Google Pay.
                        // The Google Pay flow will need to be performed by the
                        // user again at the time of checkout.
                    } else {
                        // Use the payment method show in your UI and charge the user
                        // at the time of checkout.
                        PaymentMethodNonce paymentMethod = dropInResult.getPaymentMethodNonce();
                    }
                } else {
                    // there was no existing payment method
                }
            }
        });
    }


    @Override
    public void onDropInSuccess(@NonNull DropInResult dropInResult) {
        String paymentMethodNonce = Objects.requireNonNull(dropInResult.getPaymentMethodNonce()).getString();
        // use the result to update your UI and send the payment method nonce to your server
        System.out.println(paymentMethodNonce);
        postNonceToServer(paymentMethodNonce);

    }



    @Override
    public void onDropInFailure(@NonNull Exception error) {
        if (error instanceof UserCanceledException) {
            // the user canceled
        } else {
            // handle error
        }
    }

    @Override
    public void onClick(View v) {
        launchDropIn();
    }
}