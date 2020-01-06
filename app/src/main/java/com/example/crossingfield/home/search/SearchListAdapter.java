package com.example.crossingfield.home.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.crossingfield.R;
import com.example.crossingfield.lib.User;

import java.util.ArrayList;

public class SearchListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<User> users;
    private LayoutInflater layoutInflater;

    public class ViewHolder{
        TextView textView;
    }

    public SearchListAdapter(Context context, ArrayList<User> users){
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
            convertView = layoutInflater.inflate(R.layout.list_search, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.user_name);
            convertView.setTag(viewHolder);
            System.out.println("tab");
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.textView.setText(users.get(position).getUsername());

        return convertView;
    }
}
