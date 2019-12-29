package com.example.crossingfield.home.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.crossingfield.R;

public class MessageTabFragment extends Fragment {

    private static final String[] texts = {
            "Ventnor",
            "Wroxall",
            "Whitewell",
            "Ryde",
            "StLawrence",
            "Lake",
            "Sandown",
            "Shanklin"
    };

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

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, texts);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("name", texts[position]);
                startActivity(intent);
            }
        });

    }

}
