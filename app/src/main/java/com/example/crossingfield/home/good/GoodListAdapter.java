package com.example.crossingfield.home.good;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crossingfield.R;
import com.example.crossingfield.User;

import java.util.ArrayList;

public class GoodListAdapter extends BaseAdapter {

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<User> users;

    public GoodListAdapter(Context context, ArrayList<User> users){
        super();
        this.context = context;
        this.users = users;
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
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_good, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.image_thumb);
            viewHolder.textView = convertView.findViewById(R.id.name_list);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.imageView.setImageResource(users.get(position).getPhoto());
        viewHolder.textView.setText(users.get(position).getUsername());

        return convertView;
    }
}
