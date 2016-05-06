package com.sughoshkumar.learningnetwork;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Fragment to populate the current posts
 * Created by sughoshkumar
 */
public class PostsView extends FragmentFactory{

    public PostsView(){
        createEmptyFragment(R.layout.posts_view);
    }

    /**
     * Method to populate the UI using the data obtained
     *
     * @param userList list of user information
     */
    @Override
    protected void populateView(ArrayList<UserInformation> userList) {
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        ViewList listAdapter = new ViewList(getActivity(), userList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Upon creating activity
     * @param savedInstanceState bundle if on orientation change
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateView(getData());
    }


}
