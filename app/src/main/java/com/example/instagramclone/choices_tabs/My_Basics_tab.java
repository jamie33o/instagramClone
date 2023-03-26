package com.example.instagramclone.choices_tabs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_database_queries.DataBaseUtils;
import com.example.instagramclone.reusable_database_queries.ReusableQueries;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManager;
import com.example.instagramclone.sharedpreferences.SharedPreferencesManagerImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class My_Basics_tab extends Fragment implements View.OnClickListener {

    TableLayout tableLayout,table_layout_2,table_layout_3,table_layout_4,table_layout_5,table_layout_6,table_layout_7;
    ButtonCreator buttonCreatorGender,buttonCreatorPronouns,buttonCreatorStarsigns,buttonCreatorReligions,buttonCreatorRelationshipgoals;
    String[] gender, pronouns,starSigns,religions,relationshipGoals;
    int pos;
    List<Button> genderbuttonList,pronounsbuttonList,religionsbuttonList,starSignsbuttonList,relationshipGoalsbuttonList;

    AutoCompleteTextView edtTxt;
    View view;
    SharedPreferencesManager sharedPreferencesManager;
    TextView txtview, txtV_choice0;
    private ArrayList<String>  genderList, pronounsList,starSignsList,religionsList,relationshipGoalsList;
    DataBaseUtils queryDatabase;
    LightUpPreSelectedbtn lightUpPreSelectedbtnpronouns,lightUpPreSelectedbtngender,lightUpPreSelectedbtnreligion,lightUpPreSelectedbtnrelationshipgoals,lightUpPreSelectedbtnstarsigns;
    private AddBtnTxtToArray addBtnTxtToArray,addBtnTxtToArray1,addBtnTxtToArray2,addBtnTxtToArray3,addBtnTxtToArray4;
    String religionString,genderString,starSignsString,relationshipGoalsString,hometownString,heightString;
    private RecyclerView recyclerView;
    private HeightAdapter adapter;
    View txtv;
    public My_Basics_tab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_xml_for_choices_tab,//used to find view inside fragment
                container, false);


        // Inflate the layout for this fragment
        gender = new String[]{"Male", "Female"};
        pronouns = new String[]{"he/him", "she/her", "they/them", "ze/hir", "xe/xem", "ey/em", "ve/ver", "she/they", "he/they"};
        starSigns = new String[] {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
        religions = new String[]{"Christianity", "Islam", "Hinduism", "Buddhism", "Judaism", "Sikhism", "Confucianism", "Taoism", "Shintoism", "Zoroastrianism", "Jainism", "Bahá'í Faith", "Daoism", "Rastafarianism", "Cao Dai", "Catholicism"};

        relationshipGoals = new String[]{"Long-term partner","Short-term fun","Create a family","New friends","Still figuring it out"};

        queryDatabase = new ReusableQueries(view.getContext());

        genderList= new ArrayList<>();
        pronounsList= new ArrayList<>();
        starSignsList = new ArrayList<>();
        religionsList = new ArrayList<>();
        relationshipGoalsList= new ArrayList<>();

        txtv = view.findViewById(R.id.item_height);

        view.findViewById(R.id.savebtn).setOnClickListener(this);

        // Get a reference to the RecyclerView
        recyclerView = view.findViewById(R.id.heights_recyclerview);

        // Create a new instance of the adapter and set it on the RecyclerView
        adapter = new HeightAdapter(getContext(), getHeightList());
        recyclerView.setAdapter(adapter);

        // Set the layout manager on the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //snaphelper get view to snap to view
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(60);
        gd.setSize(recyclerView.getWidth(),recyclerView.getHeight());
        gd.setStroke(3, Color.BLACK,6,6);
        gd.setColor(getResources().getColor(R.color.large_buttons));

        recyclerView.setBackground(gd);

        recyclerView.setVisibility(View.VISIBLE);
        sharedPreferencesManager = new SharedPreferencesManagerImpl(view.getContext(), "Profile", Context.MODE_PRIVATE);



        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray1 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray2 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray3 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray4 = new AddBtnTxtToArray(view.getContext());




        //image views
        ImageView img1 = view.findViewById(R.id.img1);
        img1.setVisibility(View.VISIBLE);
        img1.setRotation(0f);
        view.findViewById(R.id.imgplus).setVisibility(View.VISIBLE);


        //initialize table layouts
        tableLayout = view.findViewById(R.id.table_layout_1);
        table_layout_2 = view.findViewById(R.id.table_layout_2);
        table_layout_3 = view.findViewById(R.id.table_layout_3);
        table_layout_4 = view.findViewById(R.id.table_layout_4);
        table_layout_5 = view.findViewById(R.id.table_layout_5);
        table_layout_6 = view.findViewById(R.id.table_layout_6);
        table_layout_7 = view.findViewById(R.id.table_layout_7);



        //set the text views
        txtview = view.findViewById(R.id.txtview_title);
        txtview.setText("My Basics");

        TextView textViewHeight = view.findViewById(R.id.txtview_choice0);
        view.findViewById(R.id.line4).setVisibility(View.VISIBLE);
        textViewHeight.setVisibility(View.VISIBLE);
        textViewHeight.setText("Height");

        TextView txtV2 = view.findViewById(R.id.txtview_choice2);
        txtV2.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line5).setVisibility(View.VISIBLE);
        txtV2.setText("Gender");

        TextView txtV3 = view.findViewById(R.id.txtview_choice3);
        txtV3.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line6).setVisibility(View.VISIBLE);
        txtV3.setText("Pronounes");

        TextView txtV4 = view.findViewById(R.id.txtview_choice4);
        txtV4.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line7).setVisibility(View.VISIBLE);
        txtV4.setText("Star sign");

        TextView txtV5 = view.findViewById(R.id.txtview_choice5);
        txtV5.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line8).setVisibility(View.VISIBLE);
        txtV5.setText("Religion");

        TextView txtV6 = view.findViewById(R.id.txtview_choice6);
        txtV6.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line9).setVisibility(View.VISIBLE);
        txtV6.setText("Hometown");




// Step 1: Create a list of country names
        String[] countries = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola"};


// Step 3: Create an ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, countries);
// Step 2: Create an AutoCompleteTextView widget
        edtTxt = view.findViewById(R.id.hometown);
        edtTxt.setVisibility(View.VISIBLE);
// Step 4: Set the adapter for the AutoCompleteTextView
        edtTxt.setAdapter(adapter);


        TextView txtV7 = view.findViewById(R.id.txtview_choice7);
        txtV7.setVisibility(View.VISIBLE);
        view.findViewById(R.id.line10).setVisibility(View.VISIBLE);
        txtV7.setText("Relationship goals");

        view.findViewById(R.id.savebtn).setOnClickListener(this);

        //array of buttons created by button creator
     genderbuttonList = new ArrayList<>();
     pronounsbuttonList = new ArrayList<>();
     religionsbuttonList = new ArrayList<>();
     starSignsbuttonList = new ArrayList<>();
     relationshipGoalsbuttonList = new ArrayList<>();

        //initialize and create the buttons
        buttonCreatorGender = new ButtonCreator(view.getContext(),genderbuttonList);
        buttonCreatorGender.buttonCreator(table_layout_2, this,"", gender);
        buttonCreatorPronouns = new ButtonCreator(view.getContext(),pronounsbuttonList);
        buttonCreatorPronouns.buttonCreator(table_layout_3, this,"", pronouns);
        buttonCreatorStarsigns = new ButtonCreator(view.getContext(),starSignsbuttonList);
        buttonCreatorStarsigns.buttonCreator(table_layout_4, this,"", starSigns);
        buttonCreatorReligions = new ButtonCreator(view.getContext(),religionsbuttonList);
        buttonCreatorReligions.buttonCreator(table_layout_5, this,"", religions);
        buttonCreatorRelationshipgoals = new ButtonCreator(view.getContext(),relationshipGoalsbuttonList);
        buttonCreatorRelationshipgoals.buttonCreator(table_layout_7, this,"", relationshipGoals);






        String savedJsonPronouns = sharedPreferencesManager.getString("pronouns", ""); // Retrieve the JSON string from shared preferences for the "choseninterests" key
        Gson gson2 = new Gson(); // Create a Gson instance
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> pronounsPrefs = gson2.fromJson(savedJsonPronouns, type);

        String gender = sharedPreferencesManager.getString("gender", "");
        genderList.add(gender);
        System.out.println(gender);
        String starsign = sharedPreferencesManager.getString("starsign", "");

        starSignsList.add(starsign);
        religionsList.add(sharedPreferencesManager.getString("religion", ""));
        relationshipGoalsList.add(sharedPreferencesManager.getString("relationshipgoals", ""));





        //checks the choices thar are stored and lights up the buttons accordingly and
        // adds the to the selected button array
        lightUpPreSelectedbtnpronouns = new LightUpPreSelectedbtn(view.getContext());
        lightUpPreSelectedbtnpronouns.lightUpPreSelectBtn(pronounsPrefs, pronounsbuttonList);
        lightUpPreSelectedbtnstarsigns = new LightUpPreSelectedbtn(view.getContext());
        lightUpPreSelectedbtnstarsigns.lightUpPreSelectBtn(starSignsList, starSignsbuttonList);
        lightUpPreSelectedbtngender = new LightUpPreSelectedbtn(view.getContext());
        lightUpPreSelectedbtngender.lightUpPreSelectBtn(genderList, genderbuttonList);
        lightUpPreSelectedbtnreligion = new LightUpPreSelectedbtn(view.getContext());
        lightUpPreSelectedbtnreligion.lightUpPreSelectBtn(religionsList, religionsbuttonList);
        lightUpPreSelectedbtnrelationshipgoals = new LightUpPreSelectedbtn(view.getContext());
        lightUpPreSelectedbtnrelationshipgoals.lightUpPreSelectBtn(relationshipGoalsList, relationshipGoalsbuttonList);

        view.findViewById(R.id.line1).setVisibility(View.VISIBLE);
        view.findViewById(R.id.line2).setVisibility(View.VISIBLE);

        return view;//must return type view


    }

    @Override
    public void onClick(View v) {

        int buttonId = v.getId();


        if (!(v instanceof ImageButton)) {

            Button btn = view.findViewById(buttonId);
            String btnTxt = btn.getText().toString();

            if(Arrays.asList(gender).contains(btnTxt))
                addBtnTxtToArray.addBtnClickedTxtToArray(genderList,genderbuttonList, v, 1);
            if(Arrays.asList(religions).contains(btnTxt))
                addBtnTxtToArray1.addBtnClickedTxtToArray(religionsList,religionsbuttonList, v, 1);
            if(Arrays.asList(relationshipGoals).contains(btnTxt))
                addBtnTxtToArray2.addBtnClickedTxtToArray(relationshipGoalsList,relationshipGoalsbuttonList, v, 1);
            if(Arrays.asList(starSigns).contains(btnTxt))
                addBtnTxtToArray3.addBtnClickedTxtToArray(starSignsList,starSignsbuttonList, v, 1);
            if(Arrays.asList(pronouns).contains(btnTxt))
                addBtnTxtToArray4.addBtnClickedTxtToArray(pronounsList,pronounsbuttonList, v, 2);

        }

        if (buttonId == R.id.savebtn) {
            if(religionsList!=null) {
                religionString = religionsList.get(0);
                queryDatabase.uploadToDataBase("religion", religionString, view.getContext());
                sharedPreferencesManager.saveString("religion", religionString); // Store the JSON string in shared preferences for the "choseninterests" key
            }


            if(genderList!=null) {
                genderString = genderList.get(0);
                System.out.println(genderString);
                queryDatabase.uploadToDataBase("gender", genderString , view.getContext());
                sharedPreferencesManager.saveString("gender", genderString); // Store the JSON string in shared preferences for the "choseninterests" key
            }

            if(relationshipGoalsList!=null) {
                relationshipGoalsString = relationshipGoalsList.get(0);
                queryDatabase.uploadToDataBase("relationshipgoals", relationshipGoalsString , view.getContext());
                sharedPreferencesManager.saveString("relationshipgoals", relationshipGoalsString); // Store the JSON string in shared preferences for the "choseninterests" key

            }
            if(starSignsList!=null) {
                starSignsString = starSignsList.get(0);
                queryDatabase.uploadToDataBase("starsign", starSignsString , view.getContext());
                sharedPreferencesManager.saveString("starsign", starSignsString); // Store the JSON string in shared preferences for the "choseninterests" key

            }
            if(pronounsList!=null) {
                queryDatabase.uploadToDataBase("pronouns", pronounsList, view.getContext());
                Gson gson1 = new Gson(); // Create a Gson instance
                String jsonPronouns = gson1.toJson(pronounsList); // Convert the array of strings to a JSON string using Gson
                if (pronounsList.size() > 0) { // Check if the array contains any elements
                    sharedPreferencesManager.saveString("pronouns", jsonPronouns); // Store the JSON string in shared preferences for the "choseninterests" key
                }
            }
            if(!edtTxt.getText().toString().equals("")) {
                hometownString = edtTxt.getText().toString();
                queryDatabase.uploadToDataBase("hometown", hometownString, view.getContext());
                sharedPreferencesManager.saveString("hometown", hometownString); // Store the JSON string in shared preferences for the "choseninterests" key

            }

            if(adapter.getSelectedText()!=null) {
                heightString = adapter.getSelectedText();
                queryDatabase.uploadToDataBase("height", heightString, view.getContext());
                sharedPreferencesManager.saveString("height", heightString); // Store the JSON string in shared preferences for the "choseninterests" key
            }








        }


    }

    private List<String> getHeightList() {
        List<String> heightsList = new ArrayList<>();
        for (int i = 120; i < 235; i++) {

            heightsList.add(i + "cm");
        }

        return heightsList;
    }
}


