package com.example.instagramclone;

import java.util.ArrayList;
import java.util.List;

public class ButtonTxtArraysSingleton {
        private static ButtonTxtArraysSingleton instance = null;


    public  String[] smokingArray = new String[]{"Don't smoke", "When Stressed", "Sometimes", "Only when drinking"};
    public  String[] petsArray = new String[]{"Like them have none", "Have a dog", "Have a cat", "Love them", "Not an animal lover", "Have loads"};
    public  String[] workoutArray = new String[]{"Bodybuilder", "Physique model", "Daily", "when i can", "Running", "Play sports", "Cardio", "Strength training", "HIIT", "Yoga", "Pilates", "CrossFit", "Cycling", "Swimming", "Rowing", "Boxing", "Martial arts", "Dance", "Barre", "Bodyweight training", "Resistance band training", "TRX training", "Calisthenics"};
    public  String[] drinkingArray = new String[]{"Weekends", "Regular", "Special occasion", "Don't drink"};
    public  String[] socialmediaArray = new String[]{"Passive scroller", "Addicted", "Rare", "Casual"};
    public  String[] kidsArray = new String[]{"One child", "two kids", "Three kids", "Four kids", "Five kids", "Love kids have none", "Never", "Maybe", "Love too", "Someday", "Definetly someday"};
    public  String[] dietaryArray = new String[]{"Vegetarian", "Vegan", "Ketogenic", "Paleo", "Gluten-free", "Low-carb", "Mediterranean", "DASH", "Flexitarian", "Raw food", "Intermittent fasting", "Whole30", "Atkins", "South Beach", "Weight Watchers", "Zone"};
    public String[] gender = new String[]{"Male", "Female"};
    public  String[] pronouns = new String[]{"he/him", "she/her", "they/them", "ze/hir", "xe/xem", "ey/em", "ve/ver", "she/they", "he/they"};
    public  String[] starSigns = new String[]{"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    public  String[] religions = new String[]{"Christianity", "Islam", "Hinduism", "Buddhism", "Judaism", "Sikhism", "Confucianism", "Taoism", "Shintoism", "Zoroastrianism", "Jainism", "Bahá'í Faith", "Daoism", "Rastafarianism", "Cao Dai", "Catholicism"};

    public  String[] relationshipGoals = new String[]{"Long-term partner", "Short-term fun", "Create a family", "New friends", "Still figuring it out"};
    public  String[] interests = new String[]{"Reading", "Writing", "Photography", "Traveling", "Cooking", "Hiking", "Painting", "Gaming", "Music", "Dancing", "Sports", "Meditation", "Coding", "Gardening", "Fishing", "Surfing", "Netflix"};

    public  String[] languages = new String[]{"English", "Spanish", "Chinese", "Arabic", "French", "Russian", "German", "Portuguese", "Italian", "Dutch", "Japanese", "Korean",
            "Swedish", "Danish", "Norwegian", "Finnish", "Polish", "Greek", "Turkish", "Hebrew", "Hindi", "Bengali", "Urdu", "Punjabi", "Marathi", "Telugu", "Tamil", "Gujarati", "Kannada", "Malayalam", "Persian", "Indonesian", "Malay", "Thai", "Vietnamese", "Filipino", "Swahili", "Czech", "Slovak", "Hungarian", "Romanian", "Bulgarian", "Ukrainian", "Croatian", "Serbian", "Slovenian", "Lithuanian", "Latvian", "Estonian", "Icelandic", "Greenlandic", "Faroese", "Irish", "Scottish Gaelic", "Welsh", "Cornish", "Breton", "Luxembourgish", "Catalan", "Galician",
            "Basque", "Asturian", "Occitan", "Friulian", "Ladin", "Sicilian", "Neapolitan", "Sardinian", "Romansh", "Lombard", "Venetian", "Emilian-Romagnol", "Corsican", "Arpitan", "Walloon", "Limburgish", "Low German", "Upper Sorbian", "Lower Sorbian", "Aromanian", "Megleno-Romanian", "Dalmatian", "Resian", "Istriot", "Skolt Sami", "Inari Sami", "Kildin Sami", "Lule Sami", "North Sami", "Pite Sami", "South Sami", "Ume Sami", "Eastern Mari", "Meadow Mari", "Hill Mari", "Komi-Permyak", "Komi-Zyrian", "Erzya", "Moksha", "Udmurt", "Tatar", "Bashkir", "Chuvash",
            "Yakut", "Chechen", "Ingush", "Kabardian", "Adyghe", "Abkhaz", "Ossetic", "Talysh", "Lezgian", "Tabasaran", "Lak", "Rutul", "Avar", "Andi", "Dargin", "Kumyk", "Nogai", "Karachay-Balkar", "Kalmyk", "Khakas", "Tuva", "Altai", "Shor", "Tofa", "Evenki", "Even", "Negidal", "Nivkh", "Orok", "Ulch", "Nanai", "Udege", "Nivkh", "Oroch", "Itelmen", "Koryak", "Chukchi", "Aleut", "Inupiaq", "Greenlandic", "Yupik", "Haida", "Tlingit", "Eyak", "Navajo", "Ojibwe", "Cree", "Innu", "Mi'kmaq", "Ahtna", "Dena'ina", "Hän", "Koyukon", "Gwich’in", "Tlingit"};

    public  String[] sexualOrientation = new String[]{"Straight","Lesbian", "Gay", "Bisexual", "Pansexual", "Questioning"};

   public  String[] counties = new String[]{"Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon", "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};

    public  String[][] arrays;
    public  List<String> heightsList;


    private ButtonTxtArraysSingleton() {
        // Private constructor to prevent instantiation from outside
        heightsList = new ArrayList<>();

        for (int i = 120; i < 235; i++) {

            heightsList.add(i + "cm");
        }
        String[] heights = heightsList.toArray(new String[0]);
        //array of arrays used to add icon to each button by checking each buttons text agaist each array
        arrays = new String[][]{languages,gender,interests,counties,
                smokingArray, petsArray, workoutArray, drinkingArray,
                socialmediaArray, kidsArray, dietaryArray,
                pronouns, starSigns, religions, relationshipGoals,sexualOrientation,heights};



    }

    public static ButtonTxtArraysSingleton getInstance() {
        if(instance == null) {
            instance = new ButtonTxtArraysSingleton();
        }
        return instance;
    }
}


