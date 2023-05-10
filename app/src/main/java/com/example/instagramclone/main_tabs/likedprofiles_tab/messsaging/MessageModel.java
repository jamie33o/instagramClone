package com.example.instagramclone.main_tabs.likedprofiles_tab.messsaging;

import com.parse.ParseUser;

public class MessageModel {
    private String messageText;
    private String messageTime;

    private ParseUser userClassPointer;


    public MessageModel(String messageText, String messageTime, ParseUser userClassPointer) {
        this.messageText = messageText;
        this.messageTime = messageTime;
        this.userClassPointer=userClassPointer;
    }
    public ParseUser getUserclassPointer() {
        return userClassPointer;
    }

    public void setUserClassPointer(ParseUser userClassPointer) {
        this.userClassPointer = userClassPointer;
    }
    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

}
