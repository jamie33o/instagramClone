package com.example.instagramclone.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.instagramclone.R;
import com.example.instagramclone.login_signup.SignUp;
import com.example.instagramclone.reusable_code.Dialogs;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class Settings extends AppCompatActivity implements View.OnClickListener {
    Button sign_up,delete_Account,profile_creation,privacy_policy,terms_of_service,community_guidlines,safety_tips, faq,contact_us,billing_and_payment,refund_policy,logout;
    String viewTxt,header;
    String lastUpdated,email;

    ImageButton backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle(null);
        Toolbar toolbar = findViewById(R.id.include4);
        setSupportActionBar(toolbar);
        TextView toolbarTxt = toolbar.findViewById(R.id.toolbar_txt);
        toolbarTxt.setText("Setting's");

        backbtn = toolbar.findViewById(R.id.btnBackToolbar);
        backbtn.setVisibility(View.VISIBLE);
        backbtn.setOnClickListener(this);


        email = getString(R.string.email);
        lastUpdated = getResources().getString(R.string.last_updated);
        sign_up = findViewById(R.id.Sign_up_Registration);
        privacy_policy = findViewById(R.id.privacy_policy);
        profile_creation = findViewById(R.id.profile_creation);
        terms_of_service = findViewById(R.id.terms_of_service);
        community_guidlines = findViewById(R.id.community_guidelines);
        contact_us = findViewById(R.id.contact_Us);
        safety_tips = findViewById(R.id.safetytips);
        faq = findViewById(R.id.FAQ_Help_center);
        refund_policy = findViewById(R.id.refund_policy);
        billing_and_payment = findViewById(R.id.billing_and_payment);

        logout = findViewById(R.id.logout);
        delete_Account = findViewById(R.id.delete_account);
        delete_Account.setOnClickListener(this);
        logout.setOnClickListener(this);
        faq.setOnClickListener(this);
        billing_and_payment.setOnClickListener(this);
        safety_tips.setOnClickListener(this);
        community_guidlines.setOnClickListener(this);
        terms_of_service.setOnClickListener(this);
        profile_creation.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        contact_us.setOnClickListener(this);
        privacy_policy.setOnClickListener(this);
        refund_policy.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {

        if(v.getId() == backbtn.getId()){
            finish();
            return;
        }

        if(v.getId() == delete_Account.getId()){
            String title = "!!!--DELETE ACCOUNT--!!!";
            String message = "This can't be undone!\nAre you sure u want to delete your account?";

            Dialogs.showAlertDialog(Settings.this, title, message, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ParseUser.getCurrentUser().deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
                                @Override
                                public void done(ParseModel object, ParseException e) {
                                    object.deleteInBackground(new DeleteCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            ParseUser.logOut();
                                            Intent intent = new Intent(Settings.this, SignUp.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    });

                                }
                            });

                        }
                    });

                    dialog.dismiss();
                }
            });
            return;
        }

        if(v.getId()==R.id.logout){
            ParseUser.logOut();
            Intent intent = new Intent(Settings.this, SignUp.class);
            startActivity(intent);
            return;
        }
        if (v.getId() == sign_up.getId()) {
            viewTxt = getString(R.string.signUpInstruction);
            header = getString(R.string.signUpInstructionHeader);
        } else if (v.getId() == privacy_policy.getId()) {
             viewTxt = getString(R.string.privacyPolicy).replace("%1s", lastUpdated).replace("%2s", email);
            header = getString(R.string.privacyPolicyHeader);
        }else if (v.getId() == refund_policy.getId()) {
            viewTxt = getString(R.string.refundPolicy).replace("%1s", lastUpdated).replace("%2s", email);
            header = getString(R.string.refundPolicyHeader);
        }else if (v.getId() == terms_of_service.getId()) {
            viewTxt = getString(R.string.termsOfService).replace("%2s", email);
            header = getString(R.string.termsOfServiceHeader);
        }else if (v.getId() == faq.getId()) {
            viewTxt = getString(R.string.faq);
            header = getString(R.string.faqHeader);
        }else if (v.getId() == community_guidlines.getId()) {
            viewTxt = getString(R.string.communityGuidelines).replace("%2s", email);
            header = getString(R.string.communityGuidelinesHeader);
        }else if (v.getId() == billing_and_payment.getId()) {
            viewTxt = getString(R.string.billingPayment);
            header = getString(R.string.billingPaymentHeader);
        }else if (v.getId() == safety_tips.getId()) {
            viewTxt = getString(R.string.safetyTips);
            header = getString(R.string.safetyTipsHeader);
        }else if (v.getId() == contact_us.getId()) {
            viewTxt = getString(R.string.contact_form);
            header = "Contact";
        }

        // Create an instance of your bottom fragment
        BottomPageFragment bottomFragment = new BottomPageFragment();
// Create a bundle to hold the privacy policy
        Bundle args = new Bundle();
        args.putString("privacyPolicy", viewTxt); // Your privacy policy text
        args.putString("header",header);
// Set the arguments to the fragment
        bottomFragment.setArguments(args);

        // Show the bottom fragment using the FragmentManager
        bottomFragment.show(getSupportFragmentManager(), "bottomFragmentTag");


    }



}