package com.example.instagramclone.choices_tabs.tabs;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.instagramclone.ButtonTxtArraysSingleton;
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
import java.util.List;

public class languages_tab extends Fragment implements View.OnClickListener {

    private ViewPager2 viewPager;
    private List<Button> btnList;
    private List<String>  languagesList,oldLanguagesList;
    private View view;

    private AddBtnTxtToArray addBtnTxtToArray;

    private LightUpPreSelectedbtn lightUpPreSelectedbtn;

    public languages_tab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_choices_tab,//used to find view inside fragment
                container, false);

        viewPager = requireActivity().findViewById(R.id.viewPager_choices_tab);


        TextView txtview = view.findViewById(R.id.txtview_title);
        txtview.setText("Languages i know");
        TableLayout tableLayout = view.findViewById(R.id.table_layout_1);
        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());

        btnList = new ArrayList<>();

        ButtonTxtArraysSingleton singletonInstance = ButtonTxtArraysSingleton.getInstance();

        view.findViewById(R.id.savebtn).setOnClickListener(this);
        //initialize and create the buttons
        ButtonCreator buttonCreator = new ButtonCreator(getContext(), btnList);
        buttonCreator.buttonCreator(tableLayout, this, singletonInstance.languages);


        //create search for languages
        TextView search = view.findViewById(R.id.search);
        search.setVisibility(View.VISIBLE);



        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString().toLowerCase();
                for (Button button : btnList) {
                    String buttonText = button.getText().toString().toLowerCase();
                    if (buttonText.contains(searchText)) {
                        button.setVisibility(View.VISIBLE);
                    } else {
                        button.setVisibility(View.GONE);
                    }
                }
            }
        });
        languagesList = new ArrayList<>();
        oldLanguagesList = new ArrayList<>();
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    if (parseModel.getLanguages() != null) {
                        languagesList = new ArrayList<>(parseModel.getLanguages());
                        oldLanguagesList = new ArrayList<>(languagesList);
                    }

                    //checks the choices thar are stored and lights up the buttons accordingly and
                    // adds the to the selected button array
                    lightUpPreSelectedbtn = new LightUpPreSelectedbtn(view.getContext());
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(languagesList, btnList);

                }
            }
        });
        return view;//must return type view


    }


    @Override
    public void onClick(View v) {//for uploading image
        int buttonId = v.getId();

        if (!(v instanceof ImageButton)) {


            addBtnTxtToArray.addBtnClickedTxtToArray(languagesList, v, 4);

        }


        if (buttonId == R.id.savebtn) {

// Get the InputMethodManager instance
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

// Hide the keyboard from the currently focused view
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            if(languagesList != null && !languagesList.isEmpty() && !oldLanguagesList.equals(languagesList) ){
                saveToParse();
                //used to change fragments when button clicked
                viewPager.setCurrentItem(4);
            }else if (languagesList != null && !languagesList.isEmpty()){
                viewPager.setCurrentItem(4);

            }
        }
    }
    public void saveToParse() {
// Set the value of a dynamic property using reflection

        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    parseModel.setLanguages(languagesList);
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