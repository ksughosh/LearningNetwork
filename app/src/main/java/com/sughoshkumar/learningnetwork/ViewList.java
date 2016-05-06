package com.sughoshkumar.learningnetwork;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Adapter for the recycler view displaying messages
 * Created by sughoshkumar.
 */
public class ViewList extends RecyclerView.Adapter<ViewList.DataViewHolder> {

    // member instances
    private final LayoutInflater inflater;
    private List<UserInformation> userList = Collections.emptyList();

    /**
     * Constructor
     * @param context application context
     * @param list user information data
     */
    ViewList(Context context, List<UserInformation> list){
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
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_contents, parent, false);
        return new DataViewHolder(view);
    }

    /**
     * Binding the data with the view
     * @param holder view holder custom or inherited
     * @param position current position
     */
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        if (holder == null || holder.userPost == null || holder.icon == null) throw new NullPointerException();
        UserInformation currentUserInformation = userList.get(position);
        holder.icon.setImageBitmap(currentUserInformation.getIcon());
        holder.userName.setText(currentUserInformation.getName());
        holder.userDescription.setText(currentUserInformation.getDescription());
        holder.userPost.setText(currentUserInformation.getPost());
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
    class DataViewHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView userName, userDescription, userPost;

        public DataViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.userIcon);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userDescription = (TextView) itemView.findViewById(R.id.details);
            userPost = (TextView) itemView.findViewById(R.id.post);
        }
    }
}
