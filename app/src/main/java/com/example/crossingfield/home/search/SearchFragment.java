package com.example.crossingfield.home.search;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.crossingfield.R;
import com.example.crossingfield.home.message.MessageListAdapter;
import com.example.crossingfield.lib.MySocket;
import com.example.crossingfield.lib.User;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.crossingfield.lib.MathConstants.*;
import static com.example.crossingfield.lib.StringConstants.*;

public class SearchFragment extends Fragment {

    private View view1;

    private RadioGroup rg;
    private EditText oldFrom;
    private EditText oldTo;
    private Spinner areaSpinner;

    private String area;
    private String sendMessage;

    public SearchFragment(){ }
    public FragmentManager fragmentManager;

    public class Model{
        @SerializedName("user")
        @Expose
        public Person person;
    }

    public class Person{
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("age")
        @Expose
        public Integer age;
        @SerializedName("pass")
        @Expose
        public String password;
        @SerializedName("area")
        @Expose
        public String area;
        @SerializedName("name")
        @Expose
        public String user_name;

    }

    public static SearchFragment newInstance(){
        SearchFragment searchFragment = new SearchFragment();

        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        this.view1 = view;

        rg = (RadioGroup) view.findViewById(R.id.search_radio);

        oldFrom = (EditText)view.findViewById(R.id.old_edit1);
        oldTo = (EditText)view.findViewById(R.id.old_edit2);

        areaSpinner = (Spinner)view.findViewById(R.id.search_area);
        ArrayAdapter areaAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.area, android.R.layout.simple_spinner_item);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        fragmentManager = getFragmentManager();

        // 都道府県のSpinner
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner)adapterView;
                area = (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Button searchButton = (Button) view.findViewById(R.id.search_done);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = rg.getCheckedRadioButtonId();

                String gender = null;
                if (checkedId != -1){
                    // 選択されているラジオボタンの取得
                    RadioButton genderButton = (RadioButton) view1.findViewById(checkedId);
                    gender = genderButton.getText().toString();
                    if (gender.equals("男性") == true){
                        gender = "male";
                    }else{
                        gender = "famale";
                    }
                }

                String old = oldFrom.getText().toString() + ',' +  oldTo.getText().toString();

                sendMessage = String.valueOf(SEARCH) + ',' + gender + ',' + old + ',' + area;
                search();
            }
        });

        Button popButton = (Button)view.findViewById(R.id.pop01);
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentManager != null){
                    fragmentManager.popBackStack();
                }
            }
        });
    }

    private void search(){
        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                MySocket socket = new MySocket(getContext());
                socket.setMessage(sendMessage);
                String get_message = socket.trySend();

                return get_message;
            }

            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);

                ArrayList<String> jsons = new ArrayList<>(Arrays.asList(string.split("@")));
                ArrayList<User> users = new ArrayList<>();

                for (String json : jsons){
                    Gson gson = new Gson();
                    Model model = gson.fromJson(json, Model.class);
                    users.add(setUser(model));
                }

                ListView listView = getActivity().findViewById(R.id.search_list);

                BaseAdapter adapter = new SearchListAdapter(getContext(), users);
                listView.setAdapter(adapter);

                if(fragmentManager != null){
                    fragmentManager.popBackStack();
                }
            }
        }.execute();
    }

    public User setUser(Model model){
        User user = new User();
        user.setGender(model.person.gender);
        user.setOld(model.person.age);
        user.setArea(model.person.area);
        user.setUsername(model.person.user_name);

        return user;
    }

}
