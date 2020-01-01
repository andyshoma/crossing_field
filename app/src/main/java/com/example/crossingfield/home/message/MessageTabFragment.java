package com.example.crossingfield.home.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.crossingfield.R;
import com.example.crossingfield.lib.User;

import java.util.ArrayList;

public class MessageTabFragment extends Fragment {

    ArrayList<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_tab_message, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle saveInstaceState){
        super.onActivityCreated(saveInstaceState);

        ListView listView = getActivity().findViewById(R.id.message_list);

        sampleUsers();

        BaseAdapter adapter = new MessageListAdapter(getContext(), users);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("name", users.get(position).getUsername());
                intent.putExtra("icon", users.get(position).getPhoto());
                startActivity(intent);
            }
        });

    }

    private void setUsers(ArrayList<User> users){
        this.users = users;
    }

    private void sampleUsers(){
        ArrayList<User> users = new ArrayList<>();

        users.add(new User("andy", "male", 21, "和歌山", R.drawable.monky));
        users.add(new User("maep", "male", 22, "香川", R.drawable.monky));
        users.add(new User("yossi", "male", 22, "岐阜", R.drawable.monky));
        users.add(new User("arthur", "male", 22, "愛知", R.drawable.monky));

        setUsers(users);
    }
}
