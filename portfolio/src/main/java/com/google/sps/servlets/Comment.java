package com.google.sps.servlets;

/**
 * Class representing a comment. Contains the display
 * name of the user and the actual text of the comment.
 */
public class Comment {
    public String displayName;
    public String text;

    public Comment(String displayName, String text) {
        this.displayName = displayName;
        this.text = text;
    }
}
