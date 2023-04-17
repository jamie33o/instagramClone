package com.example.instagramclone.reusable_code.ParseUtils;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

@ParseClassName("Profile")
public class ParseModel extends ParseObject {

    public static ParseQuery<ParseModel> getQuery(boolean getcurrentuser) {
        ParseQuery<ParseModel> query = ParseQuery.getQuery(ParseModel.class);
        if(getcurrentuser) {
            query.whereEqualTo("userclasspointer", UtilsClass.getCurrentUser());
        }else {
            query.whereNotEqualTo("userclasspointer", UtilsClass.getCurrentUser());
        }
        return query;
    }


    public static final String LOCATION = "location";

    public static final String KEY_LIKED_USERS = "likedUsers";
    public static final String USER_NAME = "userName";
    public static final String NAME = "name";
    public static final String ARTIST_NAME = "artistName";
    public static final String SONG_NAME = "songName";
    public static final String AGE = "age";
    public static final String WHERE_I_LIVE = "whereIlive";
    public static final String BIO = "bio";
    public static final String GENDER = "gender";
    public static final String PROFESSION = "profession";
    public static final String IMAGE_1_NAME = "image1Name";
    public static final String IMAGE_2_NAME = "image2Name";
    public static final String IMAGE_3_NAME = "image3Name";
    public static final String IMAGE_1_DATA = "image1";
    public static final String IMAGE_2_DATA = "image2";
    public static final String IMAGE_3_DATA = "image3";
    public static final String DRINKING = "drinking";
    public static final String DIETARY = "dietary";
    public static final String SOCIAL_MEDIA = "socialMedia";
    public static final String KIDS = "kids";
    public static final String JOB_TITLE_FIELD_OF_STUDY = "jobTitle_fieldOfStudy";
    public static final String COMPANY_RCOLLEDGE = "companyRcolledge";
    public static final String SEXUAL_ORIENTATION = "sexualOrientaion";
    public static final String STAR_SIGN = "starSign";
    public static final String RELIGION = "religion";
    public static final String HOMETOWN = "hometown";
    public static final String RELATIONSHIP_GOALS = "relationShipGoals";
    public static final String PETS = "pets";
    public static final String SMOKING = "smoking";
    public static final String SEARCH_DISTANCE = "searchDistance";
    public static final String HEIGHT = "height";
    public static final String WORKOUT = "workout";
    public static final String INTERESTS = "interests";
   public static final String CHOSEN_COUNTIES_LIST = "chosenCountiesList";
    public static final String LANGUAGES_LIST = "languagesList";
    public static final String PRONOUNS_LIST = "pronounsList";

    public static final String ALBUM_NAME = "albumName";

    public static final String TRACK_IMAGE = "trackImage";
    public static final String USER_CLASS_POINTER = "userclasspointer";

    public static final String DATE_OF_BIRTH = "dateofbirth";

    // Define getters and setters for fields


    public ParseGeoPoint getLocation() {
        return getParseGeoPoint(LOCATION);
    }

    public void setLocation(ParseGeoPoint location) {
        put(LOCATION, location);
    }

    public Date getDateOfBirth() {
        return getDate(DATE_OF_BIRTH);
    }

    public void setDateOfBirth(Date dateofbirth) {
        put(DATE_OF_BIRTH, dateofbirth);
    }
    public ParseUser getUserClaassPointer() {
        return getParseUser(USER_CLASS_POINTER);
    }

    public void setUserClassPointer(ParseUser userClassPointer) {
        put(USER_CLASS_POINTER, userClassPointer);
    }
    // Define getters and setters for fields
    public String getUserName() {
        return getString(USER_NAME);
    }

    public void setUserName(String userName) {
        put(USER_NAME, userName);
    }

    public String getName() {
        return getString(NAME);
    }

    public void setName(String name) {
        put(NAME, name);
    }

    public String getArtistName() {
        return getString(ARTIST_NAME);
    }

    public void setArtistName(String artistName) {
        put(ARTIST_NAME, artistName);
    }

    public void setSongName(String songName) {
        put(SONG_NAME, songName);
    }

    public String getSongName() {
        return getString(SONG_NAME);
    }

    public void setAge(int age) {
        put(AGE, age);
    }

    public int getAge() {
        return getInt(AGE);
    }

    public void setWhereILive(String whereILive) {
        put(WHERE_I_LIVE, whereILive);
    }

    public String getWhereILive() {
        return getString(WHERE_I_LIVE);
    }

    public void setBio(String bio) {
        put(BIO, bio);
    }

    public String getBio() {
        return getString(BIO);
    }

    public void setGender(String gender) {
        put(GENDER, gender);
    }

    public String getGender() {
        return getString(GENDER);
    }

    public void setProfession(String profession) {
        put(PROFESSION, profession);
    }

    public String getProfession() {
        return getString(PROFESSION);
    }

    public void setImage1Name(String image1Name) {
        put(IMAGE_1_NAME, image1Name);
    }

    public String getImage1Name() {
        return getString(IMAGE_1_NAME);
    }

    public void setImage2Name(String image2Name) {
        put(IMAGE_2_NAME, image2Name);
    }

    public String getImage2Name() {
        return getString(IMAGE_2_NAME);
    }

    public void setImage3Name(String image3Name) {
        put(IMAGE_3_NAME, image3Name);
    }

    public String getImage3Name() {
        return getString(IMAGE_3_NAME);
    }

    public void setImage1Data(ParseFile image1Data) {
        put(IMAGE_1_DATA, image1Data);
    }

    public ParseFile getImage1Data() {
        return getParseFile(IMAGE_1_DATA);
    }

    public void setImage2Data(ParseFile image2Data) {
        put(IMAGE_2_DATA, image2Data);
    }

    public ParseFile getImage2Data() {
        return getParseFile(IMAGE_2_DATA);
    }

    public void setImage3Data(ParseFile image3Data) {
        put(IMAGE_3_DATA, image3Data);
    }

    public ParseFile getImage3Data() {
        return getParseFile(IMAGE_3_DATA);
    }
    public void setDrinking(String drinking) {
        put(DRINKING, drinking);
    }

    public String getDrinking() {
        return getString(DRINKING);
    }

    public void setDietary(String dietary) {
        put(DIETARY, dietary);
    }

    public String getDietary() {
        return getString(DIETARY);
    }

    public void setSocialMedia(String socialMedia) {
        put(SOCIAL_MEDIA, socialMedia);
    }

    public String getSocialMedia() {
        return getString(SOCIAL_MEDIA);
    }

    public void setKids(String kids) {
        put(KIDS, kids);
    }

    public String getKids() {
        return getString(KIDS);
    }

    public void setJobTitleFieldOfStudy(String jobTitleFieldOfStudy) {
        put(JOB_TITLE_FIELD_OF_STUDY, jobTitleFieldOfStudy);
    }

    public String getJobTitleFieldOfStudy() {
        return getString(JOB_TITLE_FIELD_OF_STUDY);
    }

    public void setCompanyRcolledge(String companyRcolledge) {
        put(COMPANY_RCOLLEDGE, companyRcolledge);
    }

    public String getCompanyRcolledge() {
        return getString(COMPANY_RCOLLEDGE);
    }

    public void setSexualOrientation(String sexualOrientation) {
        put(SEXUAL_ORIENTATION, sexualOrientation);
    }

    public String getSexualOrientation() {
        return getString(SEXUAL_ORIENTATION);
    }

    public void setStarSign(String starSign) {
        put(STAR_SIGN, starSign);
    }

    public String getStarSign() {
        return getString(STAR_SIGN);
    }

    public void setReligion(String religion) {
        put(RELIGION, religion);
    }

    public String getReligion() {
        return getString(RELIGION);
    }

    public void setHometown(String hometown) {
        put(HOMETOWN, hometown);
    }

    public String getHometown() {
        return getString(HOMETOWN);
    }

    public void setRelationshipGoals(String relationshipGoals) {
        put(RELATIONSHIP_GOALS, relationshipGoals);
    }

    public String getRelationshipGoals() {
        return getString(RELATIONSHIP_GOALS);
    }

    public void setPets(String pets) {
        put(PETS, pets);
    }

    public String getPets() {
        return getString(PETS);
    }

    public void setSmoking(String smoking) {
        put(SMOKING, smoking);
    }

    public String getSmoking() {
        return getString(SMOKING);
    }

    public double getSearchDistance() {
        return getDouble(SEARCH_DISTANCE);
    }

    public String getHeight() {
        return getString(HEIGHT);
    }

    public List<String> getWorkout() {
        return getList(WORKOUT);
    }

    public List<String> getInterests() {
        return getList(INTERESTS);
    }

    public List<String> getChosenCounties() {
        return getList(CHOSEN_COUNTIES_LIST);
    }

    public List<String> getLanguages() {
        return getList(LANGUAGES_LIST);
    }

    public List<String> getPronouns() {
        return getList(PRONOUNS_LIST);
    }
    // Define getter and setter for the field
    public List<ParseUser> getLikedUsers() {
        return getList(KEY_LIKED_USERS);
    }

    public void setLikedUsers(List<ParseUser> likedUsers) {
        put(KEY_LIKED_USERS, likedUsers);
    }

    public void setSearchDistance(double searchDistance) {
        put(SEARCH_DISTANCE, searchDistance);
    }

    public void setHeight(String height) {
        put(HEIGHT, height);
    }

    public void setWorkout(List<String> workout) {
        put(WORKOUT, workout);
    }

    public void setInterests(List<String> interests) {
        put(INTERESTS, interests);
    }

    public void setChosenCounties(List<String> chosenCounties) {
        put(CHOSEN_COUNTIES_LIST, chosenCounties);
    }

    public void setLanguages(List<String> languages) {
        put(LANGUAGES_LIST, languages);
    }

    public void setPronouns(List<String> pronouns) {
        put(PRONOUNS_LIST, pronouns);
    }

    public String getAlbumName() {
        return getString(ALBUM_NAME);
    }

    public void setAlbumName(String albumName) {
        put(ALBUM_NAME, albumName);
    }
    public String getTrackImage() {
        return getString(TRACK_IMAGE);
    }

    public void setTrackImage(String trackImage) {
        put(TRACK_IMAGE, trackImage);
    }

}