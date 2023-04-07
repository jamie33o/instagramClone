package com.example.instagramclone.reusable_code;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.GradientDrawable;
        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.Gravity;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.CompoundButton;
        import android.widget.ImageButton;
        import android.widget.SeekBar;
        import android.widget.Switch;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import com.example.instagramclone.R;
        import com.example.instagramclone.realm.RealmManager;
        import com.example.instagramclone.realm.RealmModel;
        import com.example.instagramclone.reusable_database_queries.DataBaseUtils;
        import com.example.instagramclone.reusable_database_queries.ReusableQueries;
        import com.example.instagramclone.reusable_database_queries.UtilsClass;

        import java.util.ArrayList;
        import java.util.List;

        import io.realm.Realm;
        import io.realm.RealmList;
public class MatchChoices extends AppCompatActivity implements View.OnClickListener {
     AddBtnTxtToArray addBtnTxtToArray;
     String[] countiesIreland;
    private TableLayout tableLayout;
    private DataBaseUtils queryDatabase;
    List<Button> countieBtnList;
    private String distance;
    private int searchDistance = 0;

    private TextView distanceText;
     SeekBar startDistanceSeekBar;

    TableRow tableRow;
    ButtonCreator buttonCreator;
    TextView textView;
     Switch switchBtn;

    List<String> chosenCountiesList;
    RealmList<String> realmList ;
    private Realm realm;
    private RealmModel results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match_choices);
        countiesIreland = new String[]{"Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon", "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};


        queryDatabase = new ReusableQueries(this);
        realm = RealmManager.getRealmInstance();

        results = realm.where(RealmModel.class).equalTo("userName", UtilsClass.getCurrentUsername()).findFirst();
        chosenCountiesList = new ArrayList<>();




        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .8));
        WindowManager.LayoutParams params = getWindow().getAttributes();


        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        textView = findViewById(R.id.txtview_title);
        findViewById(R.id.savebtn).setOnClickListener(this);
        findViewById(R.id.backbtn).setOnClickListener(this);

        tableLayout = findViewById(R.id.tablelayout);

        GetUserLocation getUserLocation = new GetUserLocation(this, MatchChoices.this);
        getUserLocation.onStart();


        countieBtnList = new ArrayList<>();
        buttonCreator = new ButtonCreator(this,countieBtnList);
        buttonCreator.buttonCreator(tableLayout, this, "", countiesIreland);

        addBtnTxtToArray = new AddBtnTxtToArray(this);


        distanceText = findViewById(R.id.distance);
        startDistanceSeekBar = findViewById(R.id.start_distance_seekbar);


        textView.setText("Search");

        tableRow = findViewById(R.id.seekBar_row);
        tableRow.setVisibility(View.GONE);

        switchBtn = findViewById(R.id.switch_btn);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tableLayout.setVisibility(View.GONE);
                    tableRow.setVisibility(View.VISIBLE);
                    chosenCountiesList.clear();

                } else {
                    tableLayout.setVisibility(View.VISIBLE);
                    tableRow.setVisibility(View.GONE);
                    searchDistance = 0;

                }
            }
        });

        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setStroke(3,Color.GRAY,10,10);
        gdDefault.setCornerRadius(15);
        tableRow.setBackground(gdDefault);

        startDistanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // Update start distance text
                searchDistance = progress;
                 distance = progress+"\n km";
                distanceText.setText( distance);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });



        if(results!=null) {
            realm.beginTransaction();
            realmList = results.getChosenCounties();
            chosenCountiesList = new ArrayList<>(realmList);
            searchDistance = results.getSearchDistance();
            realm.commitTransaction();
        }

        LightUpPreSelectedbtn lightUpPreSelectedbtn = new LightUpPreSelectedbtn(this);
        lightUpPreSelectedbtn.lightUpPreSelectBtn(chosenCountiesList,countieBtnList);

    }






    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backbtn) {
            finish();
            return;
        }

        if (!(v instanceof ImageButton)) {

            addBtnTxtToArray.addBtnClickedTxtToArray(chosenCountiesList, v, 4);


        }

        // Save the search prefs
        if (v.getId() == R.id.savebtn) {


            uploadToRealm();

            Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
        }



    }

    public void uploadToRealm() {
// Set the value of a dynamic property using reflection
        realmList = new RealmList<>();
        realmList.addAll(chosenCountiesList);

        realm.beginTransaction();

        results.setChosenCounties(realmList);
        results.setSearchDistance(searchDistance);

        realm.commitTransaction();

    }


}


