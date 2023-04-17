package com.example.instagramclone.choices_tabs.tabs;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.instagramclone.choices_tabs.Choices_tabs_MainPage;
import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
import com.example.instagramclone.R;
import com.example.instagramclone.reusable_code.AddBtnTxtToArray;
import com.example.instagramclone.reusable_code.ButtonCreator;
import com.example.instagramclone.reusable_code.LightUpPreSelectedbtn;
import com.example.instagramclone.reusable_code.Snackbar_Dialog;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class languages_tab extends Fragment implements View.OnClickListener {

    private ButtonCreator buttonCreator;
    ViewPager2 viewPager;
    TextView search;
    List<Button> btnList;
    TextView txtview;
    private List<String>  languagesList;
    private TableLayout tableLayout;
    private View view;

    private AddBtnTxtToArray addBtnTxtToArray;

    private String[] languages;
    private LightUpPreSelectedbtn lightUpPreSelectedbtn;

    public languages_tab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.main_xml_for_choices_tab,//used to find view inside fragment
                container, false);

        viewPager = requireActivity().findViewById(R.id.viewPager_choices_tab);


        languages = new String[]{"English", "Spanish", "Chinese", "Arabic", "French", "Russian", "German", "Portuguese", "Italian", "Dutch", "Japanese", "Korean",
                "Swedish", "Danish", "Norwegian", "Finnish", "Polish", "Greek", "Turkish", "Hebrew", "Hindi", "Bengali", "Urdu", "Punjabi", "Marathi", "Telugu", "Tamil", "Gujarati", "Kannada", "Malayalam", "Persian", "Indonesian", "Malay", "Thai", "Vietnamese", "Filipino", "Swahili", "Czech", "Slovak", "Hungarian", "Romanian", "Bulgarian", "Ukrainian", "Croatian", "Serbian", "Slovenian", "Lithuanian", "Latvian", "Estonian", "Icelandic", "Greenlandic", "Faroese", "Irish", "Scottish Gaelic", "Welsh", "Cornish", "Breton", "Luxembourgish", "Catalan", "Galician",
                "Basque", "Asturian", "Occitan", "Friulian", "Ladin", "Sicilian", "Neapolitan", "Sardinian", "Romansh", "Lombard", "Venetian", "Emilian-Romagnol", "Corsican", "Arpitan", "Walloon", "Limburgish", "Low German", "Upper Sorbian", "Lower Sorbian", "Aromanian", "Megleno-Romanian", "Dalmatian", "Resian", "Istriot", "Skolt Sami", "Inari Sami", "Kildin Sami", "Lule Sami", "North Sami", "Pite Sami", "South Sami", "Ume Sami", "Eastern Mari", "Meadow Mari", "Hill Mari", "Komi-Permyak", "Komi-Zyrian", "Erzya", "Moksha", "Udmurt", "Tatar", "Bashkir", "Chuvash",
                "Yakut", "Chechen", "Ingush", "Kabardian", "Adyghe", "Abkhaz", "Ossetic", "Talysh", "Lezgian", "Tabasaran", "Lak", "Rutul", "Avar", "Andi", "Dargin", "Kumyk", "Nogai", "Karachay-Balkar", "Kalmyk", "Khakas", "Tuva", "Altai", "Shor", "Tofa", "Evenki", "Even", "Negidal", "Nivkh", "Orok", "Ulch", "Nanai", "Udege", "Nivkh", "Oroch", "Itelmen", "Koryak", "Chukchi", "Aleut", "Inupiaq", "Greenlandic", "Yupik", "Haida", "Tlingit", "Eyak", "Navajo", "Ojibwe", "Cree", "Innu", "Mi'kmaq", "Ahtna", "Dena'ina", "Hän", "Koyukon", "Gwich’in", "Tlingit"};



        txtview = view.findViewById(R.id.txtview_title);
        txtview.setText("Languages i know");
        tableLayout = view.findViewById(R.id.table_layout_1);
        addBtnTxtToArray = new AddBtnTxtToArray(view.getContext());

        btnList = new ArrayList<>();

        view.findViewById(R.id.savebtn).setOnClickListener(this);
        //initialize and create the buttons
        buttonCreator = new ButtonCreator(getContext(),btnList);
        buttonCreator.buttonCreator(tableLayout, this, "", languages);


        //create search for languages
        search = view.findViewById(R.id.search);
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
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {
                    if (parseModel.getLanguages() != null) {
                        languagesList = new ArrayList<>(parseModel.getLanguages());
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

            uploadToRealm();

            viewPager.setCurrentItem(4);

        }
    }
    public void uploadToRealm() {
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
                                Snackbar_Dialog.showSnackbar(view.getContext(), "Success!!!\n Selection's saved", 2000);
                            } else {
                                Snackbar_Dialog.showSnackbar(view.getContext(), "Error!!!\n Selection's not saved", 2000);


                            }
                        }
                    });
                } else {
                    Snackbar_Dialog.showSnackbar(view.getContext(), "Error!!!\n Selection's not saved", 2000);
                }
            }
        });

    }

}