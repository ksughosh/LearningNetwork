package com.sughoshkumar.learningnetwork;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Fragment view to display the current
 * read and unread messages with feedback
 * Created by sughoshkumar.
 */
public class MessageView extends Fragment {

    /**
     * Upon creating activity
     * @param savedInstanceState bundle if on orientation change
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<UserInformation> mData = PostsView.getData();
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.messageList);
        MessageListAdapter listAdapter = new MessageListAdapter(getActivity(), mData);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        return inflater.inflate(R.layout.message_view, container, false);
    }

}
