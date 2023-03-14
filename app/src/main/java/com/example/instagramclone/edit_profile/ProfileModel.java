package com.example.instagramclone.edit_profile;





    public class ProfileModel {

        private String name, age, county,userName,profileBio,profileProfession, profileHobbies, image1,image2,image3;
        private String[] sports,choseninterests,chosencounties;


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

        public void setChoseninterests(String[] choseninterests) {
            this.choseninterests = choseninterests;
        }

        public void setChosencounties(String[] chosencounties) {
            this.chosencounties = chosencounties;
        }

        public ProfileModel(String name, String age, String county, String userName, String profileBio, String profileProfession, String profileHobbies, String[] choseninterests, String[] chosencounties, String image1, String image2, String image3){
            this.name = name;
            this.age = age;
            this.county = county;
            this.userName = userName;
            this.profileBio = profileBio;
            this.choseninterests = choseninterests;
            this.profileProfession = profileProfession;
            this.profileHobbies = profileHobbies;
            this.chosencounties = chosencounties;
            this.image1 = image1;
            this.image2 = image2;
            this.image3 = image3;





        }
        public ProfileModel() {
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

        public String[] getChoseninterests() {
            return choseninterests;
        }

        public String[] getChosencounties() {
            return chosencounties;
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

