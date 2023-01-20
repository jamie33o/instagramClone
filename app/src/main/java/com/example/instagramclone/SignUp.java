package com.example.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUserNameSignUp,edtEmail,edtPasswordSignUp;
    private Button btnSignUp, btnLogin;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        edtUserNameSignUp = findViewById(R.id.edtUserNameSignUp);
        edtEmail = findViewById(R.id.loginedtEmail);
        edtPasswordSignUp = findViewById(R.id.loginedtPassword);

        edtPasswordSignUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
//login when you press enter on keyboard
               if(keyCode == KeyEvent.KEYCODE_ENTER &&
                       event.getAction() == KeyEvent.ACTION_DOWN){
//todo not working
                   onClick(btnSignUp);
               }

                return false;
            }
        });

        btnSignUp = findViewById(R.id.loginbtnSignUp);
        btnLogin = findViewById(R.id.loginbtnLogin);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


        if(ParseUser.getCurrentUser() != null){
          //  ParseUser.getCurrentUser().logOut();
            transitionSocialMediaActivity();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginbtnSignUp:

                if(edtEmail.getText().toString().equals("") ||
                        edtUserNameSignUp.getText().toString().equals("")||
                        edtPasswordSignUp.getText().toString().equals("")) {

                    FancyToast.makeText(SignUp.this,
                            "Email, Username,Password is required!",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO,
                            true).show();

                }else {

                    final ParseUser appUser = new ParseUser();//uploads info to data base
                    appUser.setUsername(edtUserNameSignUp.getText().toString());
                    appUser.setPassword(edtPasswordSignUp.getText().toString());
                    appUser.setEmail(edtEmail.getText().toString());

                    ProgressDialog progressDialog = new ProgressDialog((this));
                    progressDialog.setMessage("Signing up " + edtUserNameSignUp.getText().toString());
                    progressDialog.show();


                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + " is signed up successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true).show();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                                transitionSocialMediaActivity();

                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.loginbtnLogin:

                Intent intent = new Intent(SignUp.this,Login_Activity.class);
                startActivity(intent);
                break;
        }

    }

    public void rootLayoutTapped(View view){
          //when u click the background makes keyboard disapear
      try {
          InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
          inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
      }catch (Exception e){
          e.printStackTrace();
      }

      }

      private void transitionSocialMediaActivity(){

        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);
      }
}


