package com.sughoshkumar.learningnetwork;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to populate the current posts
 * Created by sughoshkumar
 */
public class PostsView extends Fragment implements MainActivity.MainJsonResult{

    // member instances
    private RecyclerView recyclerView;
    private ViewList listAdapter;
    private Context mContext;
    private static ArrayList<UserInformation> cachedData;


    /**
     * Upon creating activity
     * @param savedInstanceState bundle if on orientation change
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        if (cachedData != null)
            populateView(cachedData);
    }

    /**
     * Saving state
     * @param outState bundle
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        try {
            outState.putByteArray("cached", UserInformation.pack(cachedData));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return inflater.inflate(R.layout.posts_view, container, false);
    }

    /**
     * Populates the list
     * @param userList list of user information
     */
    public void populateView(ArrayList<UserInformation> userList){
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        listAdapter = new ViewList(mContext, userList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Resource to bitmap converter for user image
     * @param resource resource id
     * @return bitmap
     */
    public Bitmap getBitmapFromResource(int resource){
        return BitmapFactory.decodeResource(getResources(), resource);
    }

    /**
     * Implementing functional interface for retrieving
     * data
     * @param jsonList array from obtained from url
     */
    @Override
    public void onResultReceived(JSONArray jsonList) {
        List<UserInformation> userList = new ArrayList<>();
        Log.e("JSON", jsonList.toString());
        try {
            for (int i =0; i< jsonList.length(); i++){
                JSONObject object = jsonList.getJSONObject(i);
                UserInformation user = new UserInformation();
                user.setName(object.getString("userName"));
                user.setDescription(object.getString("description"));
                user.setPost(object.getString("post"));
                user.setMessage(object.getString("message"));
                user.setType(object.getString("type"));
                user.setIcon(getBitmapFromResource(getResources().getIdentifier(object.getString("icon"),
                        "drawable" , mContext.getPackageName())));
                userList.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cachedData = new ArrayList<>(userList);
        populateView(new ArrayList<>(userList));
    }

    /**
     * Object sorter for messages
     * @return user list contating messages
     */
    public static ArrayList<UserInformation> getData(){
        ArrayList<UserInformation> sortList = new ArrayList<>();
        for (UserInformation u : cachedData){
            if (!u.getMessage().isEmpty())
                sortList.add(u);
        }
        return sortList;
    }
}
