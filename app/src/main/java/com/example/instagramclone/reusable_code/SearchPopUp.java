package com.example.instagramclone.reusable_code;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.SwitchCompat;

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
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import com.example.instagramclone.ButtonTxtArraysSingleton;
        import com.example.instagramclone.reusable_code.ParseUtils.ParseModel;
        import com.example.instagramclone.R;
        import com.parse.GetCallback;
        import com.parse.ParseException;
        import com.parse.SaveCallback;

        import java.util.ArrayList;
        import java.util.List;

public class SearchPopUp extends AppCompatActivity implements View.OnClickListener {
     private AddBtnTxtToArray addBtnTxtToArray;
    private TableLayout tableLayout;
    private List<Button> countieBtnList;
    private String distance;
    private double searchDistance = 0;

    private TextView distanceText;

    private TableRow tableRow;

    private List<String> chosenCountiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_pop_up);


        chosenCountiesList = new ArrayList<>();

        //set the size of the page for pop up activity
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .8));
        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        TextView textView = findViewById(R.id.txtview_title);
        textView.setText(R.string.search_pop_title);


        findViewById(R.id.savebtn).setOnClickListener(this);
        findViewById(R.id.backbtn).setOnClickListener(this);

        //layout for the counties buttons
        tableLayout = findViewById(R.id.tablelayout);

        //singleton class of string arrays for buttons
        ButtonTxtArraysSingleton singletonInstance = ButtonTxtArraysSingleton.getInstance();

        //class that creates the buttons
        countieBtnList = new ArrayList<>();
        ButtonCreator buttonCreator = new ButtonCreator(this, countieBtnList);
        buttonCreator.buttonCreator(tableLayout, this,singletonInstance.counties);


        addBtnTxtToArray = new AddBtnTxtToArray(this);


        distanceText = findViewById(R.id.distance);
        SeekBar startDistanceSeekBar = findViewById(R.id.start_distance_seekbar);



        tableRow = findViewById(R.id.seekBar_row);
        tableRow.setVisibility(View.GONE);

        SwitchCompat switchBtn = findViewById(R.id.switch_btn);

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


        //parse query to get previously selected buttons or distance
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {

                    if(parseModel.getChosenCounties()!=null)
                     chosenCountiesList = new ArrayList<>(parseModel.getChosenCounties());

                    searchDistance = parseModel.getSearchDistance();
                    //changes the colour of stroke around button of previously selected buttons
                    LightUpPreSelectedbtn lightUpPreSelectedbtn = new LightUpPreSelectedbtn(SearchPopUp.this);
                    lightUpPreSelectedbtn.lightUpPreSelectBtn(chosenCountiesList, countieBtnList);
                }
            }
        });
    }






    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backbtn) {
            finish();
            return;
        }

        if (!(v instanceof ImageButton)) {
            //adds txt of btn to array
            addBtnTxtToArray.addBtnClickedTxtToArray(chosenCountiesList, v, 4);

        }

        // Save the search prefs
        if (v.getId() == R.id.savebtn) {
            saveToParse();
        }



    }

    public void saveToParse() {
//save all selected buttons txt to local datastore to be uploaded at later stage
        ParseModel.getQuery(true).fromPin().getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel parseModel, ParseException e) {
                if (e == null) {

                    if(chosenCountiesList.size()>0) {
                        parseModel.setSearchDistance(0);
                        parseModel.setChosenCounties(chosenCountiesList);
                    }else {
                        parseModel.setSearchDistance(searchDistance);
                    }
                    // Save the object locally
                    parseModel.pinInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null) {
                                Intent resultIntent = new Intent();
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }else{
                                //error dialog
                            }
                        }
                    });

                }
            }
        });
    }
}


