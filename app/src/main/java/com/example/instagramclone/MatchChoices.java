package com.example.instagramclone;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.util.DisplayMetrics;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.TableLayout;
        import android.widget.TableRow;

        import java.util.Arrays;

public class MatchChoices extends AppCompatActivity implements View.OnClickListener {
    public static boolean yes, no;
    int counter;

    String[] interests,gender,counties;
    Button notSelectedButton, selectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match_choices);


        gender = new String[]{"Male","Female"};
        counties = new String[]{"Antrim", "Armagh", "Carlow", "Cavan", "Clare", "Cork", "Derry", "Donegal", "Down", "Dublin", "Fermanagh", "Galway", "Kerry", "Kildare", "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan", "Offaly", "Roscommon", "Sligo", "Tipperary", "Tyrone", "Waterford", "Westmeath", "Wexford", "Wicklow"};
        interests = new String[]{"Reading", "Writing", "Photography", "Traveling", "Cooking", "Hiking", "Yoga", "Painting", "Gaming", "Music", "Dancing", "Sports", "Fitness", "Meditation", "Coding", "Gardening", "Fishing", "Gym", "Surfing", "Netflix"};



        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.activity_match_choices, null);

        notSelectedButton = view.findViewById(R.id.notSelectedButton);
        selectedButton = view.findViewById(R.id.selectedButton);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .8));
        WindowManager.LayoutParams params = getWindow().getAttributes();


        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);

        TableLayout genderLayout = findViewById(R.id.gender);
        buttonCreator(genderLayout,gender);





        TableLayout interestsLayout = findViewById(R.id.interestsTablelayout);
        buttonCreator(interestsLayout,interests);




        TableLayout countiesBtnLayout = findViewById(R.id.countiesTablelayout);
        buttonCreator(countiesBtnLayout,counties);



    }

    int[] interestsClicked = new int[20];
    int[] countiesClicked = new int[32];

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();

        if (buttonId <= 32) {
            Button clickedCountiestbtn = findViewById(buttonId);


            // Find the button using its ID
            // Set the background color of the button
            if (countiesClicked[buttonId] == buttonId) {
                clickedCountiestbtn.setBackground(notSelectedButton.getBackground());
                clickedCountiestbtn.setTextColor(notSelectedButton.getTextColors());


                countiesClicked[buttonId] = buttonId + 50;

            } else {
                clickedCountiestbtn.setBackground(selectedButton.getBackground());
                clickedCountiestbtn.setTextColor(selectedButton.getTextColors());
                countiesClicked[buttonId] = buttonId;
                System.out.println(buttonId);

            }

        } else if (buttonId <= 52) {
            Button clickedInterestbtn = findViewById(buttonId);

            if (interestsClicked[buttonId - 33] == buttonId) {

                clickedInterestbtn.setBackground(notSelectedButton.getBackground());
                clickedInterestbtn.setTextColor(notSelectedButton.getTextColors());

                interestsClicked[buttonId - 33] = buttonId + 60;
                System.out.println(Arrays.toString(interestsClicked));

            } else {
                clickedInterestbtn.setBackground(selectedButton.getBackground());
                clickedInterestbtn.setTextColor(selectedButton.getTextColors());

                interestsClicked[buttonId - 33] = buttonId;
                System.out.println(Arrays.toString(interestsClicked));

            }

        }
    }

    //findViewById(R.id.textView2);

       /* findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override//Yes button
            public void onClick(View v) {

                yes =true;

                finish();

            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override//No button
            public void onClick(View v) {

                no=true;

                finish();

            }
        });*/

    public void buttonCreator(TableLayout tableLayout, String[] array){

       // this.array = array;
    counter =0;
        int numRows = (int)Math.ceil((double)array.length / 4.0);////divides by colum amount
    int numCols = Math.min(array.length, 4);;////checks if array less than 4 then sets to array lenght
    Button[][] buttonArray = new Button[numRows][numCols];
    TableLayout table = new TableLayout(this);
        for(int row = 0;row<numRows;row++){
        TableRow currentRow = new TableRow(this);
            if(row == numRows-1 && array.length%4 >= 1)
                numCols = array.length%4;

        for (int button = 0; button < numCols; button++) {


            Button countiesBtn = new Button(this);
            countiesBtn.setText(array[counter]);
            countiesBtn.setLayoutParams(notSelectedButton.getLayoutParams());

            countiesBtn.setBackground(notSelectedButton.getBackground());
            countiesBtn.setId(counter);//View.generateViewId()
            countiesBtn.setTextColor(notSelectedButton.getTextColors());
            counter++;

            countiesBtn.setOnClickListener(this);


            buttonArray[row][button] = countiesBtn;
            currentRow.addView(countiesBtn);
        }
        // a new row has been constructed -> add to countiesTable
        table.addView(currentRow);
    }
// and finally takes that new countiesTable and add it to your layout.
        tableLayout.addView(table);

}




}




