package com.example.instagramclone.main_tabs.likedprofiles_tab.messsaging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.*;
import android.os.Bundle;

import android.view.View;
import android.widget.*;
import com.example.instagramclone.R;

import com.example.instagramclone.reusable_code.*;
import com.example.instagramclone.reusable_code.ParseUtils.*;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetFileCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.livequery.ParseLiveQueryClient;

import com.parse.livequery.SubscriptionHandling;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Messaging_Activity extends AppCompatActivity implements View.OnClickListener {
    List<Message> messages;
    RecyclerView messageRecyclerView;
    ParseUser recipientUserclass;
    Button send_button;
    private SubscriptionHandling<ParseMesssageModel> subscriptionHandling;
    TextView profileName;
    EditText edtMessage;
    ParseUser currentUser;
    ImageView profilePic;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        setTitle(null);
        //TOOLBAR
        // Find the ImageView inside the included layout
        //had to use this to find the image view inside toolbar
        Toolbar toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        ImageButton backbtn = toolbar.findViewById(R.id.btnBackToolbar);
        backbtn.setVisibility(View.VISIBLE);
        backbtn.setOnClickListener(this);

        profilePic = toolbar.findViewById(R.id.imgprofilepic);
        profilePic.setVisibility(View.VISIBLE);
        profileName = findViewById(R.id.toolbar_txt);
        profileName.setVisibility(View.VISIBLE);

        currentUser = UtilsClass.getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        recipientUserclass = bundle.getParcelable("user");

        PiccassoLoadToImageView picasso = new PiccassoLoadToImageView(this);

        ParseModel.getQuery(false).whereEqualTo(ParseModel.USER_CLASS_POINTER,recipientUserclass).getFirstInBackground(new GetCallback<ParseModel>() {
            @Override
            public void done(ParseModel object, ParseException e) {
                ParseFile image = object.getImage1Data();
                profileName.setText(object.getName());
                if(image!=null) {
                    image.getFileInBackground(new GetFileCallback() {
                        @Override
                        public void done(File file, ParseException e) {
                            picasso.getImageNloadIntoImageview(profilePic, file.getAbsolutePath(), "", 150, 150, 100);

                        }
                    });

                }
            }
        });


        send_button = findViewById(R.id.button_send);
        edtMessage = findViewById(R.id.edit_text_message);


        send_button.setOnClickListener(this);
        // Initialize RecyclerView
        messageRecyclerView = findViewById(R.id.rv_messages);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: replace these sample values with your actual data
        messages = new ArrayList<>();

        messageAdapter = new MessageAdapter(this, messages);
        messageRecyclerView.setAdapter(messageAdapter);

        getMessages();
        setupLiveQuery();

    }


    public void addMessage(String messageText, String messageTime,ParseUser userclassPointer) {
            Message message = new Message(messageText, messageTime,userclassPointer);
            messages.add(message);
        messageAdapter.notifyItemInserted(messages.size() -1);
    }



    private void initLiveQuery() {
        if (subscriptionHandling != null) {
            subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, (query, object) -> {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addMessage( object.getMessageText(),object.getMessageTime(),object.getSenderUserClassPointer());
                    }
                });


            });
            subscriptionHandling.handleEvent(SubscriptionHandling.Event.DELETE, (query, object) -> {

            });
            subscriptionHandling.handleEvent(SubscriptionHandling.Event.UPDATE, (query, object) -> {

            });
        }
    }
    private void setupLiveQuery() {

        ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
        ParseQuery<ParseMesssageModel> parseQuery = MessageQueries.getNewMessageQuery(recipientUserclass,currentUser);
        subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);
        subscriptionHandling.handleSubscribe(query -> {
            initLiveQuery();
        });

    }

    public void getMessages(){

        MessageQueries.getMessagesQuery(recipientUserclass,currentUser).findInBackground(new FindCallback<ParseMesssageModel>() {
            @Override
            public void done(List<ParseMesssageModel> parseMesssageModels, ParseException e) {
                if(e==null&&parseMesssageModels.size()>0){

                    for (ParseMesssageModel message : parseMesssageModels){

                        addMessage( message.getMessageText(),message.getMessageTime(),message.getSenderUserClassPointer());
                    }
                }else{
                    //no messages
                }
            }
        });

    }

    public void sendMessage(){
        String messageText = edtMessage.getText().toString();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String currentTimeString = currentTime.format(formatter);
// Create a new message object
        ParseMesssageModel message = new ParseMesssageModel();
// Set the message properties using the setters
        message.setSenderUserclassPointer(currentUser);
        message.setMessageTime(currentTimeString);
        message.setMessageText(messageText);
        message.setRecipientUserclassPointer(recipientUserclass);
// Save the message object to the database in the background
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    addMessage(messageText,currentTimeString,currentUser);
                    Snackbar_Dialog.showSnackbar(Messaging_Activity.this, "Success!!!\n Message sent", 2000);

                    edtMessage.setText("");
                } else {

                    System.out.println(e.getMessage());
                    Snackbar_Dialog.showSnackbar(Messaging_Activity.this, "Error!!!\n Message not sent", 2000);

                }
            }
        });
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnBackToolbar){
            finish();
        }
        if(v.getId()== send_button.getId()){
            sendMessage();
        }




    }
}