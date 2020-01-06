package com.example.crossingfield.home.search;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.crossingfield.R;
import com.example.crossingfield.lib.User;

import java.util.ArrayList;

public class SearchListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> users;
    private User my_user = new User();
    private LayoutInflater layoutInflater;

    public class ViewHolder{
        ImageView imageView;
        TextView textView;
        ImageButton imageButton;
    }

    public SearchListAdapter(Context context, ArrayList<User> users, User my_user){
        super();
        this.context = context;
        this.users = users;
        this.my_user = my_user;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return users.size();
    }

    @Override
    public Object getItem(int position){
        return users.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){

        ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_search, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.search_image);
            viewHolder.textView = convertView.findViewById(R.id.user_name);
            viewHolder.imageButton = convertView.findViewById(R.id.search_image_button);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.imageView.setImageBitmap(users.get(position).getPhoto());
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodSendTask task = new GoodSendTask(context, my_user.getUsername(), users.get(position).getUsername());
                System.out.println(position);
                task.execute();
            }
        });
        viewHolder.textView.setText(users.get(position).getUsername());

        return convertView;
    }
}
