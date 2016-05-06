package com.sughoshkumar.learningnetwork;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Fragment view to display the current
 * read and unread messages with feedback
 * Created by sughoshkumar.
 */
public class MessageView extends FragmentFactory {

    public MessageView(){
        createEmptyFragment(R.layout.message_view);
    }

    /**
     * Upon creating activity
     * @param savedInstanceState bundle if on orientation change
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateView(formatData());
    }

    /**
     * Format data that has new messages
     * @return list of users with messages
     */
    private ArrayList<UserInformation> formatData(){
        ArrayList<UserInformation> userList = new ArrayList<>();
        for (UserInformation u: getData()){
            if (u.hasMessage())
                userList.add(u);
        }
        return userList;
    }

    /**
     * Method to populate the UI using the data obtained
     *
     * @param userList list of user information
     */
    @Override
    protected void populateView(ArrayList<UserInformation> userList) {
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.messageList);
        MessageListAdapter listAdapter = new MessageListAdapter(getActivity(), userList);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}
