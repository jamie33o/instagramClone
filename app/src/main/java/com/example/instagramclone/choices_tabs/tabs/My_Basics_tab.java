package com.example.instagramclone.choices_tabs.tabs;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager2.widget.ViewPager2;

import com.example.instagramclone.ButtonTxtArraysSingleton;
import com.example.instagramclone.choices_tabs.HeightAdapter;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.AddBtnTxtToArray;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_code.LightUpPreSelectedbtn;
import com.example.instagramclone.reusable_code.Dialogs;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class My_Basics_tab extends Fragment implements View.OnClickListener {

    private List<Button> genderbuttonList, pronounsbuttonList, religionsbuttonList, starSignsbuttonList, relationshipGoalsbuttonList;
    private ViewPager2 viewPager;
    private boolean choiceIsChanged = false;
    private View view;
    private List<String> pronounsList;
    private LightUpPreSelectedbtn lightUpPreSelectedbtn;
    private AddBtnTxtToArray addBtnTxtToArray, addBtnTxtToArray1, addBtnTxtToArray2, addBtnTxtToArray3, addBtnTxtToArray4;
    private String religionString, genderString, starSignsString, relationshipGoalsString, heightString;
    ButtonTxtArraysSingleton singletonInstance;
    public My_Basics_tab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_choices_tab,//used to find view inside fragment
                container, false);


        viewPager = requireActivity().findViewById(R.id.viewPager_choices_tab);

         singletonInstance = ButtonTxtArraysSingleton.getInstance();



        view.findViewById(R.id.savebtn).setOnClickListener(this);

        // Get a reference to the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.heights_recyclerview);


        // Create a new instance of the adapter and set it on the RecyclerView
        HeightAdapter adapter = new HeightAdapter(getContext(), singletonInstance.heightsList);
        recyclerView.setAdapter(adapter);

        // Set the layout manager on the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        //snaphelper get view to snap to view
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(60);
        gd.setSize(recyclerView.getWidth(), recyclerView.getHeight());
        gd.setStroke(3, Color.BLACK, 6, 6);
        gd.setColor(getResources().getColor(R.color.large_buttons));

        recyclerView.setBackground(gd);

        recyclerView.setVisibility(View.VISIBLE);

        // Assuming you have a RecyclerView named recyclerView

     // Set the OnScrollListener for the RecyclerView to get txt height
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Get the first visible item position
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Get the text from the TextView of the first visible item
                View firstVisibleItem = layoutManager.findViewByPosition(firstVisibleItemPosition+1);
                if (firstVisibleItem != null) {
                    TextView textView = firstVisibleItem.findViewById(R.id.item_height); // Replace with the actual id of your TextView
                    heightString = textView.getText().toString();
                    if(!heightString.equals("121cm"))
                     choiceIsChanged = true;
                }
            }
        });


        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());

        addBtnTxtToArray1 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray2 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray3 = new AddBtnTxtToArray(view.getContext());
        addBtnTxtToArray4 = new AddBtnTxtToArray(view.getContext());


        //image views ruler etc....
        ImageView img1 = view.findViewById(R.id.img1);
        img1.setVisibility(View.VISIBLE);
        img1.setRotation(0f);

        //initialize table layouts
        TableLayout tableLayout = view.findViewById(R.id.table_layout_1);
        view.findViewById(R.id.heightlayout).setVisibility(View.VISIBLE);
        TableLayout table_layout_2 = view.findViewById(R.id.table_layout_2);
        TableLayout table_layout_3 = view.findViewById(R.id.table_layout_3);
        TableLayout table_layout_4 = view.findViewById(R.id.table_layout_4);
        TableLayout table_layout_5 = view.findViewById(R.id.table_layout_5);
        TableLayout table_layout_6 = view.findViewById(R.id.table_layout_6);
        TableLayout table_layout_7 = view.findViewById(R.id.table_layout_7);


        //set the text views
        TextView txtview = view.findViewById(R.id.txtview_title);
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
        ButtonCreator buttonCreatorGender = new ButtonCreator(view.getContext(), genderbuttonList);
        buttonCreatorGender.buttonCreator(table_layout_2, this, singletonInstance.gender);
        ButtonCreator buttonCreatorPronouns = new ButtonCreator(view.getContext(), pronounsbuttonList);
        buttonCreatorPronouns.buttonCreator(table_layout_3, this, singletonInstance.pronouns);
        ButtonCreator buttonCreatorStarsigns = new ButtonCreator(view.getContext(), starSignsbuttonList);
        buttonCreatorStarsigns.buttonCreator(table_layout_4, this, singletonInstance.starSigns);
        ButtonCreator buttonCreatorReligions = new ButtonCreator(view.getContext(), religionsbuttonList);
        buttonCreatorReligions.buttonCreator(table_layout_5, this, singletonInstance.religions);
        ButtonCreator buttonCreatorRelationshipgoals = new ButtonCreator(view.getContext(), relationshipGoalsbuttonList);
        buttonCreatorRelationshipgoals.buttonCreator(table_layout_7, this, singletonInstance.relationshipGoals);

        pronounsList = new ArrayList<>();

        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    if(parseModel.getPronouns()!=null)
                     pronounsList = new ArrayList<>(parseModel.getPronouns());

                    genderString = parseModel.getGender();
                    starSignsString = parseModel.getStarSign();
                    religionString = parseModel.getReligion();
                    relationshipGoalsString = parseModel.getRelationshipGoals();

                    //checks the choices thar are stored and lights up the buttons accordingly and
                    // adds the to the selected button array
                    lightUpPreSelectedbtn = new LightUpPreSelectedbtn(view.getContext());
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(pronounsList, pronounsbuttonList);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(starSignsString, starSignsbuttonList);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(genderString, genderbuttonList);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(religionString, religionsbuttonList);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(relationshipGoalsString, relationshipGoalsbuttonList);

                }
            }
        });
        view.findViewById(R.id.line1).setVisibility(View.VISIBLE);
        view.findViewById(R.id.line2).setVisibility(View.VISIBLE);

        return view;//must return type view


    }

    @Override
    public void onClick(View v) {

        int buttonId = v.getId();
        if (buttonId == R.id.savebtn) {
            if(choiceIsChanged)
                saveToParse();
            viewPager.setCurrentItem(5);
            return;
        }
        if (!(v instanceof ImageButton)) {

            choiceIsChanged = true;
            Button btn = view.findViewById(buttonId);
            String btnTxt = btn.getText().toString();

            if (Arrays.asList(singletonInstance.gender).contains(btnTxt))
                genderString = addBtnTxtToArray1.addBtnClickedTxttoString(genderString, genderbuttonList, v);
            if (Arrays.asList(singletonInstance.religions).contains(btnTxt))
                religionString = addBtnTxtToArray2.addBtnClickedTxttoString(religionString, religionsbuttonList, v);
            if (Arrays.asList(singletonInstance.relationshipGoals).contains(btnTxt))
                relationshipGoalsString = addBtnTxtToArray3.addBtnClickedTxttoString(relationshipGoalsString, relationshipGoalsbuttonList, v);
            if (Arrays.asList(singletonInstance.starSigns).contains(btnTxt))
                starSignsString = addBtnTxtToArray4.addBtnClickedTxttoString(starSignsString, starSignsbuttonList, v);
            if (Arrays.asList(singletonInstance.pronouns).contains(btnTxt))
                 addBtnTxtToArray.addBtnClickedTxtToArray(pronounsList, v, 2);

        }




    }






    public void saveToParse() {
// Set the value of a dynamic property using reflection
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {

                    if(genderString!=null)
                     parseModel.setGender(genderString);
                    if(heightString!=null)
                        parseModel.setHeight(heightString);
                    if(religionString!=null)
                        parseModel.setReligion(religionString);
                    if(heightString!=null)
                        parseModel.setHeight(heightString);
                    if(pronounsList!=null)
                        parseModel.setPronouns(pronounsList);
                    if(starSignsString!=null)
                        parseModel.setStarSign(starSignsString);
                    if(religionString!=null)
                        parseModel.setReligion(religionString);
                    if(relationshipGoalsString!=null)
                        parseModel.setRelationshipGoals(relationshipGoalsString);

                    // Save the object locally
                    parseModel.pinInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Display a toast message to indicate that the object has been saved locally
                                Dialogs.showSnackbar(view.getContext(), "Success!!!\n Selection's saved", 2000);
                            } else {
                                Dialogs.showSnackbar(view.getContext(), "Error!!!\n Selection's not saved", 2000);


                            }
                        }
                    });
                } else {
                    Dialogs.showSnackbar(view.getContext(), "Error!!!\n Selection's not saved", 2000);
                }
            }
        });


    }
}