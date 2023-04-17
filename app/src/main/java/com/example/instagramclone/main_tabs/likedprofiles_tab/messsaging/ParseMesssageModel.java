package com.example.instagramclone.main_tabs.likedprofiles_tab.messsaging;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Messages")
public class ParseMesssageModel extends ParseObject {


    public static final String KEY_SENDER_USERCLASS_POINTER = "sender_userclass_pointer";
    public static final String KEY_RECIPIENT_USERCLASS_POINTER = "recipient_userclass_pointer";


    public static final String KEY_MESSAGE_TEXT = "messagetext";
    public static final String KEY_MESSAGE_TIME = "time";

    public void setRecipientUserclassPointer(ParseUser recipientUserclassPointer) {
        put(KEY_RECIPIENT_USERCLASS_POINTER, recipientUserclassPointer);
    }
    public ParseUser getRecipientUserclassPointer() {
        return getParseUser(KEY_RECIPIENT_USERCLASS_POINTER);
    }

    public void setSenderUserclassPointer(ParseUser senderUserclassPointer) {
        put(KEY_SENDER_USERCLASS_POINTER, senderUserclassPointer);
    }

    public ParseUser getSenderUserClassPointer() {
        return getParseUser(KEY_SENDER_USERCLASS_POINTER);
    }

    public String getMessageText() {
        return getString(KEY_MESSAGE_TEXT);
    }

    public void setMessageText(String messageText) {
        put(KEY_MESSAGE_TEXT, messageText);
    }

    public String getMessageTime() {
        return getString(KEY_MESSAGE_TIME);
    }

    public void setMessageTime(String messageTime) {
        put(KEY_MESSAGE_TIME, messageTime);
    }


}


