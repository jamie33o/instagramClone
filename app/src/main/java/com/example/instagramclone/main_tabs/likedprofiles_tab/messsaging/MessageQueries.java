package com.example.instagramclone.main_tabs.likedprofiles_tab.messsaging;

import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MessageQueries {


    //for getting all messages between 2 users
    public static ParseQuery<ParseMesssageModel> getMessagesQuery(ParseUser currentuser, ParseUser receiverUsername) {
        ParseQuery<ParseMesssageModel> querySent = ParseQuery.getQuery(ParseMesssageModel.class);
        querySent.whereEqualTo(ParseMesssageModel.KEY_SENDER_USERCLASS_POINTER, currentuser);
        querySent.whereEqualTo(ParseMesssageModel.KEY_RECIPIENT_USERCLASS_POINTER, receiverUsername);

        ParseQuery<ParseMesssageModel> queryReceived = ParseQuery.getQuery(ParseMesssageModel.class);
        queryReceived.whereEqualTo(ParseMesssageModel.KEY_SENDER_USERCLASS_POINTER, receiverUsername);
        queryReceived.whereEqualTo(ParseMesssageModel.KEY_RECIPIENT_USERCLASS_POINTER, currentuser);

        List<ParseQuery<ParseMesssageModel>> queries = new ArrayList<>();
        queries.add(querySent);
        queries.add(queryReceived);

        return ParseQuery.or(queries);

    }

    //for gettin new messages livequery
    public static ParseQuery<ParseMesssageModel> getNewMessageQuery(ParseUser recipientpointer,ParseUser currentUser) {
        ParseQuery<ParseMesssageModel> queryReceived = ParseQuery.getQuery(ParseMesssageModel.class);
        queryReceived.whereEqualTo(ParseMesssageModel.KEY_SENDER_USERCLASS_POINTER, recipientpointer);
        queryReceived.whereEqualTo(ParseMesssageModel.KEY_RECIPIENT_USERCLASS_POINTER, currentUser);
        return queryReceived;
    }



}
