package com.example.crossingfield.home.good;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.crossingfield.R;
import com.example.crossingfield.lib.User;

import java.util.ArrayList;

public class GoodListAdapter extends BaseAdapter {

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
        ImageButton imageButton;
    }

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<User> users;
    View view;

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

        view = convertView;

        final ViewHolder viewHolder;
        if (view == null){
            view = layoutInflater.inflate(R.layout.list_good, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = view.findViewById(R.id.image_thumb);
            viewHolder.textView = view.findViewById(R.id.name_list);
            viewHolder.imageButton = view.findViewById(R.id.thanks);

            viewHolder.imageButton.setBackground(view.getResources().getDrawable(R.drawable.thanks));
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.imageView.setImageResource(users.get(position).getPhoto());
        viewHolder.textView.setText(users.get(position).getUsername());

        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // いいねありがとうボタンが押された時
            }
        });

        return view;
    }
}
