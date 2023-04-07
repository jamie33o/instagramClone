package com.example.instagramclone.realm;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmModule;

@RealmModule(classes = { RealmModel.class })

public class RealmModel extends RealmObject {

    @PrimaryKey
    private String userName;
    private String name;



    private String artistName;
    private String songName;
    private String age;
    private String whereIlive;
    private String bio;
    private String gender;
    private String profession;
    private String image1Name;
    private String image2Name;
    private String image3Name;
    private byte[] image1Data;

    private byte[] image2Data;
    private byte[] image3Data;
    private String drinking;
    private String dietary;
    private String socialMedia;
    private String kids;

    private String jobTitle_fieldOfStudy;
    private String companyRcolledge;
    private String sexualOrientaion;

    private String starSign;
    private String religion;
    private String hometown;
    private String relationShipGoals;
    private String pets;
    private String smoking;
    private double lattitude;
    private double longitude;
    private int searchDistance;
    private String height;



    private RealmList<String> workout;

    private RealmList<String> interests;
    private RealmList<String> chosenCounties;
    private RealmList<String> languages;
    private RealmList<String> pronounes;

    public byte[] getImage1Data() {
        return image1Data;
    }

    public void setImage1Data(byte[] image1Data) {
        this.image1Data = image1Data;
    }

    public byte[] getImage2Data() {
        return image2Data;
    }

    public void setImage2Data(byte[] image2Data) {
        this.image2Data = image2Data;
    }

    public byte[] getImage3Data() {
        return image3Data;
    }

    public void setImage3Data(byte[] image3Data) {
        this.image3Data = image3Data;
    }

    public String getProfession() {
        return profession;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSearchDistance() {
        return searchDistance;
    }

    public void setSearchDistance(int searchDistance) {
        this.searchDistance = searchDistance;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getJobTitle_fieldOfStudy() {
        return jobTitle_fieldOfStudy;
    }

    public void setJobTitle_fieldOfStudy(String jobTitle_fieldOfStudy) {
        this.jobTitle_fieldOfStudy = jobTitle_fieldOfStudy;
    }

    public String getCompanyRcolledge() {
        return companyRcolledge;
    }

    public void setCompanyRcolledge(String companyRcolledge) {
        this.companyRcolledge = companyRcolledge;
    }

    public String getSexualOrientaion() {
        return sexualOrientaion;
    }

    public void setSexualOrientaion(String sexualOrientaion) {
        this.sexualOrientaion = sexualOrientaion;
    }

    public RealmList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(RealmList<String> languages) {
        this.languages = languages;
    }

    public RealmList<String> getPronounes() {
        return pronounes;
    }

    public void setPronounes(RealmList<String> pronounes) {
        this.pronounes = pronounes;
    }

    public String getStarSign() {
        return starSign;
    }

    public void setStarSign(String starSign) {
        this.starSign = starSign;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getRelationShipGoals() {
        return relationShipGoals;
    }

    public void setRelationShipGoals(String relationShipGoals) {
        this.relationShipGoals = relationShipGoals;
    }

    public String getPets() {
        return pets;
    }

    public void setPets(String pets) {
        this.pets = pets;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public RealmList<String> getWorkout() {
        return workout;
    }

    public void setWorkout(RealmList<String> workout) {
        this.workout = workout;
    }

    public String getDrinking() {
        return drinking;
    }

    public void setDrinking(String drinking) {
        this.drinking = drinking;
    }

    public String getDietary() {
        return dietary;
    }

    public void setDietary(String dietary) {
        this.dietary = dietary;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getKids() {
        return kids;
    }

    public void setKids(String kids) {
        this.kids = kids;
    }

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWhereIlive() {
        return whereIlive;
    }

    public void setWhereIlive(String whereIlive) {
        this.whereIlive = whereIlive;
    }

    /*public String getUserName() {
        return userName;
    }*/

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileProfession() {
        return profession;
    }

    public void setProfileProfession(String profileProfession) {
        this.profession = profileProfession;
    }


    public String getImage1Name() {
        return image1Name;
    }

    public void setImage1Name(String image1Name) {
        this.image1Name = image1Name;
    }

    public String getImage2Name() {
        return image2Name;
    }

    public void setImage2Name(String image2Name) {
        this.image2Name = image2Name;
    }

    public String getImage3Name() {
        return image3Name;
    }

    public void setImage3Name(String image3Name) {
        this.image3Name = image3Name;
    }



    public RealmList<String> getInterests() {
        return interests;
    }

    public void setInterests(RealmList<String> interests) {
        this.interests = interests;
    }

    public RealmList<String> getChosenCounties() {
        return chosenCounties;
    }

    public void setChosenCounties(RealmList<String> chosenCounties) {
        this.chosenCounties = chosenCounties;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}