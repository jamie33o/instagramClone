package com.example.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ProfileTab extends Fragment {

    private EditText edtProfile,edtBio,edtProfession,edtHobbies,edtSport;
    private Button btnUpdateInfo;




    public ProfileTab() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            View view = inflater.inflate(R.layout.fragment_profile_tab,//used to find view inside fragment
                    container, false);

            edtProfile = view.findViewById(R.id.edtProfileName);
            edtBio = view.findViewById(R.id.edtBio);
            edtHobbies = view.findViewById(R.id.edtHobbies);
            edtProfession = view.findViewById(R.id.edtProfession);
            edtSport = view.findViewById(R.id.edtSport);

            btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

            ParseUser parseUser = ParseUser.getCurrentUser();

            if (parseUser.get("profileName") == null) {
                edtProfile.setText("No details");

            } else if (parseUser.get("profileBio") == null) {
                edtBio.setText("No details");

            } else if (parseUser.get("profileProfession") == null) {
                edtProfession.setText("No details");

            } else if (parseUser.get("profileHobbies") == null) {
                edtHobbies.setText("No details");
            } else if (parseUser.get("profileSport") == null) {
                edtSport.setText("No details");

            } else {
                edtProfile.setText(parseUser.get("profileName") + "");
                edtSport.setText(parseUser.get("profileSport") + "");
                edtHobbies.setText(parseUser.get("profileHobbies") + "");
                edtProfession.setText(parseUser.get("profileProfession") + "");
                edtBio.setText(parseUser.get("profileBio") + "");

            }
            btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parseUser.put("profileName", edtProfile.getText().toString());
                    parseUser.put("profileBio", edtBio.getText().toString());
                    parseUser.put("profileProfession", edtProfession.getText().toString());
                    parseUser.put("profileHobbies", edtHobbies.getText().toString());
                    parseUser.put("profileSport", edtSport.getText().toString());

                    ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Updating Info");
                    progressDialog.show();

                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(getContext(),
                                        "Info Updated",
                                        FancyToast.LENGTH_SHORT, FancyToast.INFO,
                                        true).show();

                            } else {
                                FancyToast.makeText(getContext(),
                                        e.getMessage(),
                                        FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR, true).show();


                            }
                            progressDialog.dismiss();

                        }


                    });


                }

            });
            return view;//must return type view


          } catch (Exception e) {
        e.printStackTrace();
    }
       return null;
        }


}