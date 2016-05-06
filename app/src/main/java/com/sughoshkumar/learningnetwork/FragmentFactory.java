package com.sughoshkumar.learningnetwork;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sughoshkumar.
 */
public abstract class FragmentFactory extends Fragment implements MainActivity.MainJsonResult{

    // member instances
    private static ArrayList<UserInformation> mUserDataList = new ArrayList<>();
    private int mLayoutResource;

    /**
     * Constructor setting the default layout to invalid
     */
    public FragmentFactory(){
        mLayoutResource = -1;
    }

    /**
     * Delegate method that gets updated upon
     * retrieving the date from the
     * @param jsonList JSONArray object containing the JSON
     */
    @Override
    public void onResultReceived(JSONArray jsonList) {
        ArrayList<com.sughoshkumar.learningnetwork.UserInformation> userList = new ArrayList<>();
        Log.e("JSON", jsonList.toString());
        try {
            for (int i =0; i< jsonList.length(); i++){
                JSONObject object = jsonList.getJSONObject(i);
                com.sughoshkumar.learningnetwork.UserInformation user = new com.sughoshkumar.learningnetwork.UserInformation();
                user.setName(object.getString("userName"));
                user.setDescription(object.getString("description"));
                user.setPost(object.getString("post"));
                user.setMessage(object.getString("message"));
                user.setType(object.getString("type"));
                user.setIcon(getBitmapFromResource(getResources().getIdentifier(object.getString("icon"),
                        "drawable" , getActivity().getPackageName())));
                userList.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mUserDataList = new ArrayList<>(userList);
        populateView(mUserDataList);
    }

    /**
     * Method to populate the UI using the data obtained
     * @param userList list of user information
     */
    protected abstract void populateView(ArrayList<UserInformation> userList);

    /**
     * Getter for the data
     * @return data retrieved
     */
    protected ArrayList<UserInformation> getData(){
        return mUserDataList;
    }

    /**
     * Setting the layout resource id
     * @param layoutResource resource id
     */
    protected void createEmptyFragment(int layoutResource){
        this.mLayoutResource = layoutResource;
    }

    /**
     * Setter for data from external class
     * @param list user information lis
     */
    public void setData(ArrayList<UserInformation> list){
        mUserDataList = list;
    }

    /**
     * On fragment view created
     * @param inflater view inflater
     * @param container view group
     * @param savedInstanceState bundle
     * @return inflated view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(mLayoutResource, container, false);
    }

    /**
     * Upon activity created
     * @param savedInstanceState bundle
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Resource to bitmap converter for user image
     * @param imageResource resource id
     * @return bitmap
     */
    public Bitmap getBitmapFromResource(int imageResource){
        return BitmapFactory.decodeResource(getResources(), imageResource);
    }

}
