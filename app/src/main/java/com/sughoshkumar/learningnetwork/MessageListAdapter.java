package com.sughoshkumar.learningnetwork;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Adapter for recycleview for displaying posts content
 * Created by sughoshkumar.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageData> {

    // member instances
    private final LayoutInflater inflater;
    private List<UserInformation> userList = Collections.emptyList();

    /**
     * Constructor
     * @param context application context
     * @param list user information data
     */
    MessageListAdapter(Context context, List<UserInformation> list){
        inflater = LayoutInflater.from(context);
        userList = list;
    }

    /**
     * Upon view created
     * @param parent parent view
     * @param viewType resource ID
     * @return view holder that will populate list
     */
    @Override
    public MessageData onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.message_content, parent, false);
        return new MessageData(view);
    }

    /**
     * Binding the data with the view
     * @param holder view holder custom or inherited
     * @param position current position
     */
    @Override
    public void onBindViewHolder(MessageData holder, int position) {
        if (holder == null || holder.userName == null || holder.icon == null || holder.message == null)
            throw new NullPointerException();
        UserInformation user = userList.get(position);
        if (user.isNewMessage()) {
            holder.message.setTypeface(null, Typeface.BOLD);
            holder.icon.setBorderWidth(5);
            holder.icon.animate();
            holder.icon.setBorderColor(Color.YELLOW);
        }
        holder.message.setText(user.getMessage());
        holder.icon.setImageBitmap(user.getIcon());
        holder.userName.setText(user.getName());
    }


    /**
     * Total items in the list
     * @return list size
     */
    @Override
    public int getItemCount() {
        return userList.size();
    }

    /**
     * Class that contains the view structure to be
     * initialized just once
     */
    class MessageData extends RecyclerView.ViewHolder{
        private CircleImageView icon;
        private TextView userName, message;

        MessageData(View view){
            super(view);
            icon = (CircleImageView) view.findViewById(R.id.messageUserIcon);
            userName = (TextView) view.findViewById(R.id.messageUserName);
            message = (TextView) view.findViewById(R.id.message);
        }

    }
}
