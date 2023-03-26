package com.example.instagramclone.edit_profile_N_profile;





    public class EditProfileModel {

        private String name, age, county,userName,profileBio,chosenGender,gender,profileProfession, profileHobbies, image1,image2,image3;
        private String[] sports, chosenInterests, chosenCounties;


        public void setName(String name) {
            this.name = name;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setProfileBio(String profileBio) {
            this.profileBio = profileBio;
        }

        public void setProfileProfession(String profileProfession) {
            this.profileProfession = profileProfession;
        }

        public void setProfileHobbies(String profileHobbies) {
            this.profileHobbies = profileHobbies;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public void setImage3(String image3) {
            this.image3 = image3;
        }

        public void setSports(String[] sports) {
            this.sports = sports;
        }

        public void setChosenInterests(String[] chosenInterests) {
            this.chosenInterests = chosenInterests;
        }

        public void setChosenCounties(String[] chosenCounties) {
            this.chosenCounties = chosenCounties;
        }

        public String getName() {
            return name;
        }

        public String getCounty() {
            return county;
        }

        public String getChosenGender() {
            return chosenGender;
        }

        public void setChosenGender(String chosenGender) {
            this.chosenGender = chosenGender;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public EditProfileModel(String name, String age, String county, String userName, String profileBio, String chosenGender, String gender, String profileProfession, String profileHobbies, String[] chosenInterests, String[] chosenCounties) {
            this.name = name;
            this.age = age;
            this.county = county;
            this.userName = userName;
            this.profileBio = profileBio;
            this.chosenGender = chosenGender;
            this.gender = gender;
            this.chosenInterests = chosenInterests;
            this.profileProfession = profileProfession;
            this.profileHobbies = profileHobbies;
            this.chosenCounties = chosenCounties;


        }

        public EditProfileModel() {

        }

        public String getUserName() {
            return userName;
        }

        public String getProfileBio() {
            return profileBio;
        }

        public String getProfileProfession() {
            return profileProfession;
        }

        public String getProfileHobbies() {
            return profileHobbies;
        }

        public String getImage1() {
            return image1;
        }

        public String getImage2() {
            return image2;
        }

        public String getImage3() {
            return image3;
        }

        public String[] getSports() {
            return sports;
        }

        public String[] getChosenInterests() {
            return chosenInterests;
        }

        public String[] getChosenCounties() {
            return chosenCounties;
        }



        public String getProfileName() {
            return name;
        }

        public String getAge() {
            return age;
        }

        public String getProfileCounty() {
            return county;
        }
    }

