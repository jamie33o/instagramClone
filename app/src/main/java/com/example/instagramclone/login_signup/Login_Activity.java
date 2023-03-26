package com.example.instagramclone.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.instagramclone.R;
import com.example.instagramclone.main_tabs.SocialMediaActivity;
import com.parse.ParseUser;
//import com.shashank.sony.fancytoastlib.FancyToast;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {
    private EditText loginedtPassword,loginedtEmail;
    private Button loginbtnSignUp, loginbtnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        setTitle("Log In");

        loginedtEmail = findViewById(R.id.loginedtEmail);
        loginedtPassword = findViewById(R.id.loginedtPassword);


        loginbtnSignUp = findViewById(R.id.loginbtnSignUp);
        loginbtnLogin= findViewById(R.id.loginbtnLogin);

        loginbtnSignUp.setOnClickListener(this);
        loginbtnLogin.setOnClickListener(this);


        if(ParseUser.getCurrentUser() != null){//checks if user already logged in

            transitionSocialMediaActivity();
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginbtnLogin:
//login user in background
                ParseUser.logInInBackground(loginedtEmail.getText().toString(),
                    loginedtPassword.getText().toString(),
                        (user, e) -> {
                        if (user != null && e == null) {
                           /* FancyToast.makeText(Login_Activity.this,
                                    user.getUsername() + " is login in successfully",
                                    FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                         */   transitionSocialMediaActivity();

                        }

                    });
                break;


            case R.id.loginbtnSignUp:

                Intent intent = new Intent(Login_Activity.this, SignUp.class);
                startActivity(intent);



                break;
        }
    }
    private void transitionSocialMediaActivity(){

        Intent intent = new Intent(Login_Activity.this, SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }


}
