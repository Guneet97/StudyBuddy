package com.studdybuddy.finalstudybuddy;

public class DiscussionBoard {

    private String title, desc, username;

    public DiscussionBoard(String title, String desc, String imageUrl, String username) {
        this.title = title;
        this.desc = desc;

        this.username = username;
    }

    public DiscussionBoard() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

}



