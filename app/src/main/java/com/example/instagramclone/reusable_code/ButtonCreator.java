package com.example.instagramclone.reusable_code;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import androidx.fragment.app.Fragment;

import com.example.instagramclone.R;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonCreator implements View.OnClickListener{
    int counter;
    List<Button> btnList;
    Drawable drawable;
    Context context;

   String[] smokingArray = new String[]{"Don't smoke", "When Stressed", "Sometimes", "Only when drinking"};
   String[] petsArray = new String[]{"Like them have none", "Have a dog", "Have a cat", "Love them", "Not an animal lover", "Have loads"};
   String[] workoutArray = new String[]{"Bodybuilder", "Physique model", "Daily", "when i can", "Running", "Play sports", "Cardio", "Strength training", "HIIT", "Yoga", "Pilates", "CrossFit", "Cycling", "Swimming", "Rowing", "Boxing", "Martial arts", "Dance", "Barre", "Bodyweight training", "Resistance band training", "TRX training", "Calisthenics"};
   String[] drinkingArray = new String[]{"Weekends", "Regular", "Special occasion", "Don't drink"};
   String[] socialmediaArray = new String[]{"Passive scroller", "Addicted", "Rare", "Casual"};
   String[] kidsArray = new String[]{"One child", "two kids", "Three kids", "Four kids", "Five kids", "Love kids have none", "Never", "Maybe", "Love too", "Someday", "Definetly someday"};
   String[] dietaryArray = new String[]{"Vegetarian", "Vegan", "Ketogenic", "Paleo", "Gluten-free", "Low-carb", "Mediterranean", "DASH", "Flexitarian", "Raw food", "Intermittent fasting", "Whole30", "Atkins", "South Beach", "Weight Watchers", "Zone"};
   String[] gender = new String[]{"Male", "Female"};
   String[] pronouns = new String[]{"he/him", "she/her", "they/them", "ze/hir", "xe/xem", "ey/em", "ve/ver", "she/they", "he/they"};
   String[] starSigns = new String[]{"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
   String[] religions = new String[]{"Christianity", "Islam", "Hinduism", "Buddhism", "Judaism", "Sikhism", "Confucianism", "Taoism", "Shintoism", "Zoroastrianism", "Jainism", "Bahá'í Faith", "Daoism", "Rastafarianism", "Cao Dai", "Catholicism"};
  
   String[] relationshipGoals = new String[]{"Long-term partner", "Short-term fun", "Create a family", "New friends", "Still figuring it out"};
   String[] interests = new String[]{"Reading", "Writing", "Photography", "Traveling", "Cooking", "Hiking", "Painting", "Gaming", "Music", "Dancing", "Sports", "Fitness", "Meditation", "Coding", "Gardening", "Fishing", "Gym", "Surfing", "Netflix"};
    String[] heights = {"121cm", "122cm", "124cm", "125cm", "127cm", "129cm", "130cm", "132cm", "134cm", "135cm", "137cm", "139cm", "140cm", "142cm", "144cm", "145cm", "147cm", "149cm", "150cm", "152cm", "154cm", "155cm", "157cm", "159cm", "160cm", "162cm", "163cm", "165cm", "167cm", "168cm", "170cm", "172cm", "173cm", "175cm", "177cm", "178cm", "180cm", "182cm", "183cm", "185cm", "187cm", "188cm", "190cm", "192cm", "193cm", "195cm", "197cm", "198cm", "200cm", "202cm", "203cm", "205cm", "207cm", "208cm", "210cm", "212cm", "213cm", "215cm", "217cm", "218cm", "220cm"};

    String[] languages = new String[]{"English", "Spanish", "Chinese", "Arabic", "French", "Russian", "German", "Portuguese", "Italian", "Dutch", "Japanese", "Korean",
            "Swedish", "Danish", "Norwegian", "Finnish", "Polish", "Greek", "Turkish", "Hebrew", "Hindi", "Bengali", "Urdu", "Punjabi", "Marathi", "Telugu", "Tamil", "Gujarati", "Kannada", "Malayalam", "Persian", "Indonesian", "Malay", "Thai", "Vietnamese", "Filipino", "Swahili", "Czech", "Slovak", "Hungarian", "Romanian", "Bulgarian", "Ukrainian", "Croatian", "Serbian", "Slovenian", "Lithuanian", "Latvian", "Estonian", "Icelandic", "Greenlandic", "Faroese", "Irish", "Scottish Gaelic", "Welsh", "Cornish", "Breton", "Luxembourgish", "Catalan", "Galician",
            "Basque", "Asturian", "Occitan", "Friulian", "Ladin", "Sicilian", "Neapolitan", "Sardinian", "Romansh", "Lombard", "Venetian", "Emilian-Romagnol", "Corsican", "Arpitan", "Walloon", "Limburgish", "Low German", "Upper Sorbian", "Lower Sorbian", "Aromanian", "Megleno-Romanian", "Dalmatian", "Resian", "Istriot", "Skolt Sami", "Inari Sami", "Kildin Sami", "Lule Sami", "North Sami", "Pite Sami", "South Sami", "Ume Sami", "Eastern Mari", "Meadow Mari", "Hill Mari", "Komi-Permyak", "Komi-Zyrian", "Erzya", "Moksha", "Udmurt", "Tatar", "Bashkir", "Chuvash",
            "Yakut", "Chechen", "Ingush", "Kabardian", "Adyghe", "Abkhaz", "Ossetic", "Talysh", "Lezgian", "Tabasaran", "Lak", "Rutul", "Avar", "Andi", "Dargin", "Kumyk", "Nogai", "Karachay-Balkar", "Kalmyk", "Khakas", "Tuva", "Altai", "Shor", "Tofa", "Evenki", "Even", "Negidal", "Nivkh", "Orok", "Ulch", "Nanai", "Udege", "Nivkh", "Oroch", "Itelmen", "Koryak", "Chukchi", "Aleut", "Inupiaq", "Greenlandic", "Yupik", "Haida", "Tlingit", "Eyak", "Navajo", "Ojibwe", "Cree", "Innu", "Mi'kmaq", "Ahtna", "Dena'ina", "Hän", "Koyukon", "Gwich’in", "Tlingit"};

    String[] sexualOrientation = new String[]{"Straight","Lesbian", "Gay", "Bisexual", "Pansexual", "Questioning"};

   String[] counties = new String[]{"Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon", "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};

   String[][] arrays;
   int arrayIndex;
    SizeBasedOnDensity sizeBasedOnDensity;
    //btnlist = list of buttons so u can get the id's and more for pre lighting chosen buttons
    //context = xml file where your puttin buttons
    public ButtonCreator(Context context, List<Button> btnList) {
        this.context = context;
        this.btnList=btnList;

        //array of arrays used to add icon to each button by checking each buttons text agaist each array
        arrays = new String[][]{languages,gender,interests,counties,
                smokingArray, petsArray, workoutArray, drinkingArray,
                socialmediaArray, kidsArray, dietaryArray,
                pronouns, starSigns, religions, relationshipGoals,sexualOrientation,heights};


        //for resizing button based on screen density
        sizeBasedOnDensity = new SizeBasedOnDensity(context);


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void buttonCreator(TableLayout tableLayout, View.OnClickListener listener,String btnName, String... array) {
        counter = 0;

        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View childView = tableLayout.getChildAt(i);
            if (childView instanceof FlexboxLayout) {
                ((FlexboxLayout) childView).removeAllViews();
                // If the child view is a TableRow, remove all its child views except for the TextView
                FlexboxLayout row = (FlexboxLayout) childView;
                for (int j = 0; j < row.getChildCount(); j++) {
                    View buttonView = row.getChildAt(j);
                    if (buttonView instanceof Button) {
                        row.removeView(buttonView);

                    }

            }
            }
        }


        FlexboxLayout flexboxLayout = new FlexboxLayout(context, null, 0);
        flexboxLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        flexboxLayout.setFlexWrap(FlexWrap.WRAP);
        flexboxLayout.setJustifyContent(JustifyContent.CENTER);
       // flexboxLayout.setAlignItems(AlignItems.STRETCH);


        for (String s : array) {

            arrayIndex =0;

            Button button = new Button(context);

            button.setText(s);
            button.setId(View.generateViewId());
            button.setTag(button.getText());
            button.setOnClickListener(listener);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

            for (String[] arr : arrays) {

                List<String> list = Arrays.asList(arr);
                if (list.contains(s)) {
                    switch (arrayIndex){
                        case 0:
                            drawable = context.getResources().getDrawable(R.drawable.languages);
                            break;
                        case 1:
                            drawable = context.getResources().getDrawable(R.drawable.gender);
                            break;
                        case 2:
                            drawable = context.getResources().getDrawable(R.drawable.interests);
                            break;
                        case 3:
                            drawable = context.getResources().getDrawable(R.drawable.whereilive);
                            break;
                        case 4:
                            drawable = context.getResources().getDrawable(R.drawable.smoking);
                            break;
                        case 5:
                            drawable = context.getResources().getDrawable(R.drawable.pets);
                            break;
                        case 6:
                            drawable = context.getResources().getDrawable(R.drawable.gym);
                            break;
                        case 7:
                            drawable = context.getResources().getDrawable(R.drawable.drinking);
                            break;
                        case 8:
                            drawable = context.getResources().getDrawable(R.drawable.social);
                            break;
                        case 9:
                            drawable = context.getResources().getDrawable(R.drawable.babybottle);
                            break;
                        case 10:
                            drawable = context.getResources().getDrawable(R.drawable.diet);
                            break;
                        case 11:
                            drawable = context.getResources().getDrawable(R.drawable.pronoun);
                            break;
                        case 12:
                            drawable = context.getResources().getDrawable(R.drawable.starsign);
                            break;
                        case 13:
                            drawable = context.getResources().getDrawable(R.drawable.religion);
                            break;
                        case 14:
                            drawable = context.getResources().getDrawable(R.drawable.relationship);
                            break;
                        case 15:
                            drawable = context.getResources().getDrawable(R.drawable.sexualorientation);
                            break;
                        case 16:
                            drawable = context.getResources().getDrawable(R.drawable.ruler);
                            break;
                        default:
                            drawable = null;
                }
            }
                arrayIndex++;
            }
            if(drawable!=null) {
                drawable.setBounds(-20, 1, 80, 80);


                button.setCompoundDrawables(drawable, null, null, null);
            }

            GradientDrawable gdDefault = new GradientDrawable();
            gdDefault.setCornerRadius(100);
            gdDefault.setStroke(2, context.getResources().getColor(R.color.buttonText));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                gdDefault.setPadding(40, 0, 20, 0);
            }
            button.setBackground(gdDefault);
            button.setTextColor(context.getResources().getColor(R.color.buttonText));


            FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    sizeBasedOnDensity.heightRatio(30)
            );
            layoutParams.setMargins(15, 20, 15, 20);
            btnList.add(button);

            flexboxLayout.addView(button, layoutParams);
        }

        tableLayout.addView(flexboxLayout);
    }
    @Override
    public void onClick(View v) {

    }
}
