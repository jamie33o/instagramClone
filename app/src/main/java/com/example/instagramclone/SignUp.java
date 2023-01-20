package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave,btnGetAllData,btnTransition;
    private EditText edtName, edtPunchSpeed, edtKickPower, edtPunchPower;
    private TextView txtGetData;
    private String allKickBockers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btnsave);
        btnSave.setOnClickListener(SignUp.this);

        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        edtPunchPower = findViewById(R.id.edtPunchPower);

        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);

        btnTransition = findViewById(R.id.btnNextActivity);


        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoker");//gets the kickboxer class from data base
                parseQuery.getInBackground("Oeg5NOoDWh", new GetCallback<ParseObject>() {// gets the object in kickboxer with this id
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null) {//checks the object exists
                            txtGetData.setText(object.get("name") + "-" + "Punch Power: " + object.get("punchPower"));//object.get gets the value of the key punchpower
                        }
                    }
                });


            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBockers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoker");

               // queryAll.whereGreaterThan("punchPower",100);//returns punchpower greater than 100
               queryAll.whereEqualTo("punchPower", 56);//returns punch power equal to 56

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null) {//checks if no exception
                            if (objects.size() > 0) {
                                for(ParseObject kickBoxer : objects) {
                                    allKickBockers = allKickBockers + kickBoxer.get("name") + "PunchPwer is:" + kickBoxer.get("punchPower") + "\n";
                                }
                                FancyToast.makeText(SignUp.this, allKickBockers, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();


                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                            }
                        }

                    }
                });
            }
        });


        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp.this,SignUpLoginActivity.class);
                startActivity(intent);
            }
        });


    }



    @Override
    public void onClick(View v) {

        try {
            final ParseObject kickBoxer = new ParseObject("KickBoker");
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));//adds key and value
            kickBoxer.put("punchPower", Integer.parseInt(edtKickPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.saveInBackground(e -> {//saves key and value to kickboker class in database
                if (e == null) {
                    FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " is saved to server", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                } else {
                    FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                }
            });

        }catch(Exception e) {
            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();//added library FancyToast in build.gradle

        }
    }

}
