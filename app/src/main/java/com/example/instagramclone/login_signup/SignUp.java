package com.example.instagramclone.login_signup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramclone.realm.RealmManager;
import com.example.instagramclone.realm.RealmModel;
import com.example.instagramclone.reusable_database_queries.ReusableQueries;
import com.example.instagramclone.R;
import com.example.instagramclone.main_tabs.SocialMediaActivity;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;

import io.realm.Realm;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUserNameSignUp,edtEmail,edtPasswordSignUp;
    private Button btnSignUp, btnLogin;
    ReusableQueries queryDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        queryDatabase = new ReusableQueries(this);
 String realmPath = Realm.getDefaultConfiguration().getPath();
        File realmFile = new File(realmPath);
        if (realmFile.exists()) {
            realmFile.delete();
        }
        edtUserNameSignUp = findViewById(R.id.edtUserNameSignUp);
        edtEmail = findViewById(R.id.loginedtEmail);
        edtPasswordSignUp = findViewById(R.id.loginedtPassword);

        edtEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
//login when you press enter on keyboard
               if(keyCode == KeyEvent.KEYCODE_ENTER &&
                       event.getAction() == KeyEvent.ACTION_DOWN){
                   onClick(btnSignUp);
                   btnSignUp.animate().rotation(360).setDuration(10);
               }

                return false;
            }
        });

        btnSignUp = findViewById(R.id.loginbtnSignUp);
        btnLogin = findViewById(R.id.loginbtnLogin);

        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


        if(ParseUser.getCurrentUser() != null){//gets token logs u straight in

            transitionSocialMediaActivity();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginbtnSignUp:

               //todo  edtUserNameSignUp.animate().rotation(360).setDuration(5000);
                if(edtEmail.getText().toString().equals("") ||
                edtUserNameSignUp.getText().toString().equals("")||
                edtPasswordSignUp.getText().toString().equals("")) {

                    Toast.makeText(SignUp.this,
                            "Email, Username,Password is required!",
                            Toast.LENGTH_SHORT).show();

                }else {

                 String username = edtUserNameSignUp.getText().toString();
// Get the default Realm instance
                    // Get a reference to the default Realm instance
                    Realm realm = RealmManager.getRealmInstance();
// Begin a new write transactio
                    realm.beginTransaction();
// Create a new RealmModel object with a primary key value of "my_username"
                    realm.createObject(RealmModel.class, username);
// Commit the write transaction
                    realm.commitTransaction();


                       ParseObject object = new ParseObject("Profile");
                    object.put("username", username);


                    object.saveInBackground(e -> {
                        if (e == null) {
                            Toast.makeText(getApplication(), "profile created", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Error creating profile", Toast.LENGTH_SHORT).show();


                        }
                    });
                    final ParseUser appUser = new ParseUser();//uploads info to data base
                    appUser.setUsername(username);
                    appUser.setPassword(edtPasswordSignUp.getText().toString());
                    appUser.setEmail(edtEmail.getText().toString());





                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + edtUserNameSignUp.getText().toString());
                    progressDialog.show();


                    appUser.signUpInBackground(e -> {
                        if (e == null) {
                            Toast.makeText(SignUp.this, appUser.getUsername() + " is signed up successfully", Toast.LENGTH_SHORT).show();


                            transitionSocialMediaActivity();
                        } else {
                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();



                        }
                        progressDialog.dismiss();
                    });
                }
                break;
            case R.id.loginbtnLogin:

                Intent intent = new Intent(SignUp.this, Login_Activity.class);
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
        finish();
      }
}


