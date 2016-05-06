package com.sughoshkumar.learningnetwork;

import android.graphics.Bitmap;
import java.io.Serializable;


/**
 * Persistent data class that is used to hold
 * and convert the JSONArray to manageable object.
 * Created by sughoshkumar.
 */
@SuppressWarnings("unused")
public class UserInformation implements Serializable{
    private Bitmap mIcon;
    private String mName;
    private String mDescription;
    private String mPost;
    private String mMessages;
    private int type;

    UserInformation(){
        mName = mDescription = mPost = "";
    }

    /**
     * Constructor to initialize user's information
     * @param mName mName of the user
     * @param desc mDescription of the user
     * @param mPost mPost by the user
     * @param resource icon
     */
    public UserInformation(String mName, String desc, String mPost, Bitmap resource){
        this.mName = mName;
        this.mDescription = desc;
        this.mPost = mPost;
        this.mIcon = resource;
    }

    /**
     * setter for mName
     * @param mName mName of the user
     */
    public void setName(String mName){
        this.mName = mName;
    }

    /**
     * setter for mDescription
     * @param mDescription mDescription of the user
     */
    public void setDescription(String mDescription){
        this.mDescription = mDescription;
    }

    /**
     * setter for mPost
     * @param mPost mPost by the user
     */
    public void setPost(String mPost){
        this.mPost = mPost;
    }

    /**
     * Setting message
     * @param message user message
     */
    public void setMessage(String message){
        mMessages = message;
    }

    /**
     * Setter for new message
     * @param type integer string
     */
    public void setType(String type){
        this.type = Integer.parseInt(type);
    }

    /**
     * Setter for icon
     * @param resource setting user image
     */
    public void setIcon(Bitmap resource){
        mIcon = resource;
    }

    /**
     * Getter for mName
     * @return mName
     */
    public String getName(){
        return mName;
    }

    /**
     * Getter for mDescription
     * @return mDescription of the user
     */
    public String getDescription(){
        return mDescription;
    }

    /**
     * Getter for mPost
     * @return mPost by the user
     */
    public String getPost(){
        return mPost;
    }

    /**
     * Getter for icon value
     * @return user image
     */
    public Bitmap getIcon(){
        return mIcon;
    }

    /**
     * Getter for type of message
     * @return 0 is read message and 1 is unread
     */
    public int getType(){
        return type;
    }

    /**
     * Getter for message
     * @return message
     */
    public String getMessage(){
        return mMessages;
    }

    /**
     * Type checker
     * @return false for read message and true for unread
     */
    public boolean isNewMessage(){
        return type == 1;
    }

    /**
     * Check message has come
     * @return message existing?
     */
    public boolean hasMessage(){
        return !mMessages.isEmpty();
    }

    @Override
    public String toString(){
        return "mName: " + mName +
                "\nmDescription:" + mDescription +
                "\nmPost: " + mPost;
    }
}
