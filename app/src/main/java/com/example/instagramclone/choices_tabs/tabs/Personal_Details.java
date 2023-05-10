package com.example.instagramclone.choices_tabs.tabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.instagramclone.R;
import com.example.instagramclone.choices_tabs.Choices_tabs_Activity;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.reusable_code.Dialogs;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Date;

public class Personal_Details extends AppCompatActivity implements View.OnClickListener {

    String name,hometownString,dateString;
    int value;
    ImageButton savebtn;
    AutoCompleteTextView edtTxtHomeCountry;
    EditText ageEdt, nameEdt, d_o_bEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        //for hiding the keyboard

        savebtn = findViewById(R.id.savebtnPersonalDetails);
        savebtn.setOnClickListener(this);
        ageEdt = findViewById(R.id.edtAge);
        d_o_bEdt = findViewById(R.id.editD_O_B);
        nameEdt = findViewById(R.id.edtName);
        edtTxtHomeCountry = findViewById(R.id.home_country);


        d_o_bEdt.addTextChangedListener(new TextWatcher() {
            private boolean mFormatting; // flag to prevent recursive formatting

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                //so u can delete the seperater aswell
                if (count == 1 && after == 0 && (start == 2 || start == 5)) {
                    // User is trying to delete a separator, so delete it and move the cursor
                    s = s.subSequence(0, start-1);
                    d_o_bEdt.setText(s);
                    d_o_bEdt.setSelection(start-1);
                }
                int maxLength = 8;
                int inputLength = s.length() + after - count;
                if (inputLength > maxLength) {
                    String truncated = s.subSequence(0, maxLength).toString();
                    d_o_bEdt.setText(truncated);
                    d_o_bEdt.setSelection(maxLength);

                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // no-op
            }



            @Override
            public void afterTextChanged(Editable s) {
                if (mFormatting) {
                    return; // prevent recursive formatting
                }

                mFormatting = true;

                // Add separators after the second and fourth characters
                if (s.length() == 2 || s.length() == 5) {
                    s.append('/');
                }

                mFormatting = false;
            }

        });

        //hometown
        // Step 1: Create a list of country names
        String[] countries = {"United States", "Canada", "Mexico", "Brazil", "Argentina", "Chile", "United Kingdom", "France", "Germany", "Italy", "Spain", "Portugal", "Russia", "China", "Japan", "South Korea", "India", "Australia", "New Zealand", "South Africa", "Egypt", "Nigeria", "Kenya", "Morocco", "Algeria", "Tunisia", "Libya", "Mali", "Senegal", "Ghana", "Ivory Coast", "Cameroon", "Ethiopia", "Uganda", "Tanzania", "Democratic Republic of the Congo", "Kenya", "Somalia", "Sudan", "Saudi Arabia", "Iran", "Iraq", "Israel", "Turkey", "Syria", "Lebanon", "Jordan", "Yemen", "Oman", "United Arab Emirates", "Afghanistan", "Pakistan", "Bangladesh", "Myanmar", "Thailand", "Indonesia", "Philippines", "Vietnam", "South Korea", "North Korea", "Mongolia", "Kazakhstan", "Kyrgyzstan", "Tajikistan", "Uzbekistan", "Turkmenistan", "Spain", "Italy", "France", "Germany", "Netherlands", "Belgium", "Switzerland", "Austria", "Czech Republic", "Slovakia", "Poland", "Hungary", "Romania", "Bulgaria", "Greece", "Norway", "Sweden", "Denmark", "Finland", "Ireland", "Iceland", "Canada", "Mexico", "Colombia", "Peru", "Venezuela", "Ecuador", "Guatemala", "Cuba", "Haiti"};



        // Step 3: Create an ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, countries);
// Step 2: Create an AutoCompleteTextView widget

// Step 4: Set the adapter for the AutoCompleteTextView
        edtTxtHomeCountry.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {


        if (!ageEdt.getText().toString().equals("") && !d_o_bEdt.getText().toString().equals("") && !nameEdt.getText().toString().equals("") && !edtTxtHomeCountry.getText().toString().equals("")) {
            value = Integer.parseInt(ageEdt.getText().toString());
            name = nameEdt.getText().toString();
            hometownString = edtTxtHomeCountry.getText().toString();
            dateString = d_o_bEdt.getText().toString();

// Get the InputMethodManager instance
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

// Hide the keyboard from the currently focused view
            View view = getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            String dialogTitle = "!!!--These can't be changed--!!!" + "\nAre your details correct?";
            String dialogMessage = "Name: " + name + "\nAge: " + value + "\nD.O.B: " + dateString + "\nHomeCountry: " + hometownString;

            Dialogs.showAlertDialog(Personal_Details.this, dialogTitle, dialogMessage, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveToParse();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(Personal_Details.this, Choices_tabs_Activity.class));
                            finish();
                        }
                    }, 1000);


                }
            });
        }
    }
    public void saveToParse(){

        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel object, ParseException e) {
                object.setName(name);

                object.setAge(value);
                 dateString = d_o_bEdt.getText().toString().replace("/", "");

                int dob = Integer.parseInt(dateString);
                Date date = new Date(dob);
                object.setDateOfBirth(date);
                object.setHometown(hometownString);
                object.pinInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Dialogs.showSnackbar(Personal_Details.this, "Success!!!\n saved", 2000);

                            ParseUser currentUser = ParseUser.getCurrentUser();
                            if (currentUser != null) {
                                currentUser.put("name", name);
                                currentUser.saveInBackground();
                            }
                        }
                    }
                });

            }
        });

    }



}