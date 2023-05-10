package com.example.instagramclone.settings;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.Dialogs;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.HashMap;

public class BottomPageFragment extends DialogFragment {
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etMessage;
    private Button btnSubmit;

    String firstName,lastName,email, phone,message;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create a custom dialog without a title
        Dialog dialog = new Dialog(requireContext(), R.style.BottomPageDialogStyle);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Set window background to transparent
        dialog.requestWindowFeature(DialogFragment.STYLE_NO_TITLE); // Remove title
        return dialog;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate your custom layout for the dialog
        View view = inflater.inflate(R.layout.fragment_bottom_page, container, false);

        String txtView = getArguments().getString("privacyPolicy");
        String headerTxt = getArguments().getString("header");




        if(headerTxt.equals("Contact")) {
            View contactLayout = view.findViewById(R.id.contactLayout);
            contactLayout.setVisibility(View.VISIBLE);
            etFirstName = view.findViewById(R.id.etFirstName);
            etLastName = view.findViewById(R.id.etLastName);
            etEmail = view.findViewById(R.id.etEmail);
            etPhone = view.findViewById(R.id.etPhone);
            etMessage = view.findViewById(R.id.etMessage);
            btnSubmit = view.findViewById(R.id.btnSubmit);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get form data
                    firstName = etFirstName.getText().toString();
                    lastName = etLastName.getText().toString();
                    email = etEmail.getText().toString();
                    phone = etPhone.getText().toString();
                    message = etMessage.getText().toString();

                    // Perform form submission, e.g., send data to a server
                    // You can implement your own logic here for form submission

                    // Example: Display a Toast message with form data
                    String formData = "First Name: " + firstName +
                            "\nLast Name: " + lastName +
                            "\nEmail: " + email +
                            "\nPhone: " + phone +
                            "\nMessageModel: " + message;
                    Toast.makeText(getContext(), formData, Toast.LENGTH_LONG).show();

                    // Create a new Contact object
                    ParseObject contact = new ParseObject("Email");

// Set the contact form data
                    contact.put("firstName", firstName);
                    contact.put("lastName", lastName);
                    contact.put("email", email);
                    contact.put("phone", phone);
                    contact.put("message", message);

// Save the Contact object to Back4App
                    contact.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Contact data saved successfully, proceed to send email
                                sendEmail(firstName, lastName, email, phone, message);
                            } else {

                                Dialogs.showSnackbar(getContext(),"Error",2000);
                            }
                        }
                    });

                }
            });




        }
        // Set the formatted strings as text in your TextView or any other UI element
        TextView header = view.findViewById(R.id.header);
        header.setText(headerTxt);



        TextView tv1 =view.findViewById(R.id.txtV1_settings);
        tv1.setText(txtView);






        ScrollView scrollView = view.findViewById(R.id.scrollv);
                // Set up scroll listener on the scroll view
                scrollView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            // Dismiss the dialog when scroll reaches the bottom
                            if (!scrollView.canScrollVertically(-1)) {
                                dismiss();
                            }
                        }
                        return false;
                    }
                });


        // Set up any views or listeners in your layout here

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Set the height of the dialog to match parent to make it slide up from the bottom
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }


    }

    private void sendEmail(String firstName, String lastName, String email, String phone, String message) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("email", email);
        params.put("phone", phone);
        params.put("message", message);

        ParseCloud.callFunctionInBackground("sendEmail", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object response, ParseException e) {
                if (e == null) {
                    // Email sent successfully
                    Dialogs.showSnackbar(getContext(),"Email sent!!!",2000);
                } else {
                    // Failed to send email
                    Dialogs.showSnackbar(getContext(),"Error email not sent!",2000);

                }
            }
        });

}

}
