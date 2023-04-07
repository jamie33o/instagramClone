package com.example.instagramclone.edit_profile_N_profile;

import android.content.Context;

import com.example.instagramclone.realm.RealmModel;
import com.example.instagramclone.realm.RealmManager;
import com.example.instagramclone.reusable_database_queries.UtilsClass;
import com.parse.GetCallback;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class QueriesEditProfile{
   public String name;

    String age;
    String county;
    String userName;
    String profileBio;
    String yourgender;
    String profileProfession;


    String whereilive;
    RealmModel results;
    Context context;
    Realm realm;
    public QueriesEditProfile(Context context){

        this.context = context;

        realm = RealmManager.getRealmInstance();

        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();


    }


    public void uploadImages(){
        ParseQuery<ParseObject> query = new ParseQuery<>("Profile");
        query.whereEqualTo("username", UtilsClass.getCurrentUsername());

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject objects, ParseException e) {
                if (e == null) {
                    if (objects != null) {
                        realm.beginTransaction();

                        if (results.getImage1Name() != null) {
                            File file1 = new File(results.getImage1Name());
                            byte[] data1 = new byte[0];
                            try {
                                FileInputStream fis = new FileInputStream(file1);
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                byte[] a = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = fis.read(a)) != -1) {
                                    bos.write(a, 0, bytesRead);
                                }
                                data1 = bos.toByteArray();
                                fis.close();
                            } catch (IOException f) {
                                f.printStackTrace();
                            }
                            ParseFile parseFile1 = new ParseFile("image1.png", data1);
                            objects.put("image1", parseFile1);
                        }


                        if (results.getImage2Name() != null) {
                            File file2 = new File(results.getImage2Name());
                            byte[] data2 = new byte[0];
                            try {
                                FileInputStream fis = new FileInputStream(file2);
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                byte[] b = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = fis.read(b)) != -1) {
                                    bos.write(b, 0, bytesRead);
                                }
                                data2 = bos.toByteArray();
                                fis.close();
                            } catch (IOException f) {
                                f.printStackTrace();
                            }
                            ParseFile parseFile2 = new ParseFile("image2.png", data2);
                            objects.put("image2", parseFile2);
                        }


                        if (results.getImage3Name() != null) {
                            File file3 = new File(results.getImage3Name());
                            byte[] data3 = new byte[0];
                            try {
                                FileInputStream fis = new FileInputStream(file3);
                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                byte[] c = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = fis.read(c)) != -1) {
                                    bos.write(c, 0, bytesRead);
                                }
                                data3 = bos.toByteArray();
                                fis.close();
                            } catch (IOException f) {
                                f.printStackTrace();
                            }
                            ParseFile parseFile3 = new ParseFile("image3.png", data3);
                            objects.put("image3", parseFile3);
                        }





                        objects.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {

                                }
                            }
                        });
                        realm.commitTransaction();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void data_From_Realm_To_Database(){

        ParseQuery<ParseObject> query = new ParseQuery<>("Profile");
        query.whereEqualTo("username", UtilsClass.getCurrentUsername());

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject objects, ParseException e) {

                if (e == null) {

                    if (objects != null) {
                        realm.beginTransaction();

                        ParseGeoPoint location = new ParseGeoPoint(results.getLattitude(), results.getLongitude());
                        objects.put("location", location);
                        if(results.getWhereIlive()!=null)
                            objects.put("whereilive", results.getWhereIlive());
                        if(results.getBio()!=null)
                            objects.put("bio", results.getBio());
                        if(results.getGender()!=null)
                            objects.put("gender", results.getGender());
                        if(results.getProfession()!=null)
                            objects.put("profession", results.getProfession());
                        if(results.getDrinking()!=null)
                            objects.put("drinking", results.getDrinking());
                        if(results.getDietary()!=null)
                            objects.put("dietary", results.getDietary());
                        if(results.getSocialMedia()!=null)
                            objects.put("socialmedia", results.getSocialMedia());
                        if(results.getKids()!=null)
                            objects.put("kids", results.getKids());
                        if(results.getJobTitle_fieldOfStudy()!=null)
                            objects.put("jobTitle_fieldOfStudy", results.getJobTitle_fieldOfStudy());
                        if(results.getSexualOrientaion()!=null)
                            objects.put("sexualOrientation", results.getSexualOrientaion());
                        if(results.getStarSign()!=null)
                            objects.put("starsign", results.getStarSign());
                        if(results.getReligion()!=null)
                            objects.put("religion", results.getReligion());
                        if(results.getHometown()!=null)
                            objects.put("hometown", results.getHometown());
                        if(results.getRelationShipGoals()!=null)
                            objects.put("relationshipgoals", results.getRelationShipGoals());
                        if(results.getPets()!=null)
                            objects.put("pets", results.getPets());
                        if(results.getSmoking()!=null)
                            objects.put("smoking", results.getSmoking());
                        if(results.getLattitude()!=0)
                            objects.put("lattitude", results.getLattitude());
                        if(results.getLongitude()!=0)
                            objects.put("longitude", results.getLongitude());

                        objects.put("chosenCounties", results.getChosenCounties());
                        objects.put("searchDistance", results.getSearchDistance());

                        if(results.getHeight()!=null)
                            objects.put("height", results.getHeight());
                        if(results.getCompanyRcolledge()!=null)
                            objects.put("companyRcolledge", results.getCompanyRcolledge());
                        if(results.getWorkout()!=null)
                            objects.put("workout", results.getWorkout());
                        if(results.getInterests()!=null)
                            objects.put("interests", results.getInterests());
                        if(results.getLanguages()!=null)
                            objects.put("languages", results.getLanguages());
                        if(results.getPronounes()!=null)
                            objects.put("pronouns", results.getPronounes());

                        objects.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {



                            }
                        });


                        realm.commitTransaction();

                        uploadImages();
                    }





                }
            }

        });


    }

    public void queryProfileNstoreInRealm() {
        ParseQuery<ParseObject> query = new ParseQuery<>("Profile");
        query.whereEqualTo("username", UtilsClass.getCurrentUsername());

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject Object, ParseException e) {

                if (e == null) {

                    if (Object != null) {

                        realm.beginTransaction();
                        if (Object.get("bio") != null) {
                            profileBio = Object.get("bio") + "";
                            results.setBio(profileBio);
                        }

                        if (Object.get("name") != null) {
                            name = Object.get("name") + "";
                            results.setName(name);
                        }
                        if (Object.get("age") != null) {
                            age = Object.get("age") + "";
                            results.setAge(age);
                        }
                        if (Object.get("county") != null) {
                            whereilive = Object.get("county") + "";
                            results.setWhereIlive(whereilive);
                        }

                        if (Object.get("profession") != null) {
                            profileProfession = Object.get("profession") + "";
                            results.setProfession(profileProfession);
                        }
                        if(Object.get("yourGender") != null) {
                            yourgender = Object.get("yourGender") + "";
                            results.setGender(yourgender);
                        }
                        if(Object.get("searchDistance") != null) {
                            int searchDistance =  Object.getInt("searchDistance");
                            results.setSearchDistance(searchDistance);
                        }
                        if(Object.get("drinking") != null) {
                            String drinking= Object.get("drinking") + "";
                            results.setDrinking(drinking);
                        }

                        if(Object.get("socialMedia") != null) {
                            String socialMedia= Object.get("socialMedia") + "";
                            results.setSocialMedia(socialMedia);
                        }

                        if(Object.get("kids") != null) {
                            String kids= Object.get("kids") + "";
                            results.setKids(kids);
                        }

                        if(Object.get("jobTitle_fieldOfStudy") != null) {
                            String jobTitle_fieldOfStudy= Object.get("jobTitle_fieldOfStudy") + "";
                            results.setJobTitle_fieldOfStudy(jobTitle_fieldOfStudy);
                        }

                        if(Object.get("companyRcolledge") != null) {
                            String companyRcolledge= Object.get("companyRcolledge") + "";
                            results.setCompanyRcolledge(companyRcolledge);
                        }

                        if(Object.get("sexualOrientaion") != null) {
                            String sexualOrientaion= Object.get("sexualOrientaion") + "";
                            results.setSexualOrientaion(sexualOrientaion);
                        }

                        if(Object.get("starSign") != null) {
                            String starSign= Object.get("starSign") + "";
                            results.setStarSign(starSign);
                        }

                        if(Object.get("religion") != null) {
                            String religion= Object.get("religion") + "";
                            results.setReligion(religion);
                        }


                        if(Object.get("hometown") != null) {
                            String hometown= Object.get("hometown") + "";
                            results.setHometown(hometown);
                        }

                        if(Object.get("relationShipGoals") != null) {
                            String relationShipGoals= Object.get("relationShipGoals") + "";
                            results.setRelationShipGoals(relationShipGoals);
                        }

                        if(Object.get("pets") != null) {
                            String pets= Object.get("pets") + "";
                            results.setPets(pets);
                        }

                        if(Object.get("smoking") != null) {
                            String smoking= Object.get("smoking") + "";
                            results.setSmoking(smoking);
                        }
                        if (Object.get("workout") != null) {
                            List<String> workout = Object.getList("workout");
                            RealmList<String> realmList = new RealmList<String>();
                            if(workout!=null) {
                                realmList.addAll(workout);
                                results.setWorkout(realmList);
                            }
                        }

                        if (Object.get("interests") != null) {
                            List<String> interests = Object.getList("interests");
                            RealmList<String> realmList = new RealmList<String>();
                            if(interests!=null) {
                                realmList.addAll(interests);
                                results.setInterests(realmList);
                            }
                        }

                        if (Object.get("pronouns") != null) {
                            List<String> pronouns = Object.getList("pronouns");
                            RealmList<String> realmList = new RealmList<String>();
                            if(pronouns!=null) {
                                realmList.addAll(pronouns);
                                results.setPronounes(realmList);
                            }
                        }

                        if (Object.get("languages") != null) {
                            List<String> languages = Object.getList("languages");
                            RealmList<String> realmList = new RealmList<>();
                            if(languages!=null) {
                                realmList.addAll(languages);
                                results.setLanguages(realmList);
                            }
                        }

                        if(Object.get("height") != null) {
                            String height= Object.getString("height");
                            results.setHeight(height);
                        }

                        if(Object.get("diet") != null) {
                            String diet= Object.getString("diet");
                            results.setDietary(diet);
                        }


                        ParseFile imageFile1 = (ParseFile) Object.get("image1");
                        if(imageFile1!=null) {

                            imageFile1.getFileInBackground(new GetFileCallback() {
                                @Override
                                public void done(File file1, ParseException e) {
                                    if(e == null) {

                                        results.setImage1Name(file1.getAbsolutePath());
                                    }
                                }
                            });


                        }
                        ParseFile imageFile2 = (ParseFile) Object.get("image2");
                        if(imageFile2!=null) {
                            imageFile2.getFileInBackground(new GetFileCallback() {
                                @Override
                                public void done(File file2, ParseException e) {
                                    if(e == null) {

                                        results.setImage2Name(file2.getAbsolutePath());
                                    }
                                }
                            });


                        }
                        ParseFile imageFile3 = (ParseFile) Object.get("image3");
                        if(imageFile3!=null) {
                            imageFile3.getFileInBackground(new GetFileCallback() {
                                @Override
                                public void done(File file3, ParseException e) {
                                    if(e == null) {

                                        results.setImage3Name(file3.getAbsolutePath());
                                    }
                                }
                            });

                        }
                        realm.commitTransaction();
                    }
                }
            }
        });
    }

}
