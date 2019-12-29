package com.example.crossingfield.entry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.crossingfield.R;
import static com.example.crossingfield.lib.MathConstants.*;
import static com.example.crossingfield.lib.StringConstants.*;

public class EntryActivity extends AppCompatActivity {

    private Context context;

    private EditText nameText;
    private Spinner monthSpinner;
    private Spinner daySpinner;
    private Spinner areaSpinner;
    private EditText yearText;
    private EditText passwordText;
    private RadioGroup rg;

    // 入力した生年月日
    private Integer month;
    private Integer day;

    // 入力した都道府県
    private String area;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        context = getApplicationContext();

        monthSpinner = (Spinner) findViewById(R.id.month);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context,
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);

        daySpinner = (Spinner)findViewById(R.id.day);

        areaSpinner = (Spinner)findViewById(R.id.area);
        ArrayAdapter areaAdapter = ArrayAdapter.createFromResource(context,
                R.array.area, android.R.layout.simple_spinner_item);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        areaSpinner.setAdapter(areaAdapter);

        nameText = (EditText)findViewById(R.id.name);
        yearText = (EditText)findViewById(R.id.year);
        passwordText = (EditText)findViewById(R.id.password);

        rg = (RadioGroup)findViewById(R.id.radio);

        final Button entryButton = (Button)findViewById(R.id.entry);



        // 月の選択により日の候補が決定
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner)adapterView;
                month = Integer.parseInt(spinner.getSelectedItem().toString());

                ArrayAdapter dayAdapter;
                switch (month){
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        dayAdapter = ArrayAdapter.createFromResource(context,
                                R.array.day_31, android.R.layout.simple_spinner_item);
                        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        daySpinner.setAdapter(dayAdapter);
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        dayAdapter = ArrayAdapter.createFromResource(context,
                                R.array.day_30, android.R.layout.simple_spinner_item);
                        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        daySpinner.setAdapter(dayAdapter);
                        break;
                    case 2:
                        dayAdapter = ArrayAdapter.createFromResource(context,
                                R.array.day_29, android.R.layout.simple_spinner_item);
                        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        daySpinner.setAdapter(dayAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 日にちのSpinner
        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner)adapterView;
                day = Integer.parseInt(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        // エントリーボタンが押された時
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedId = rg.getCheckedRadioButtonId();

                String gender = null;
                if (checkedId != -1){
                    // 選択されているラジオボタンの取得
                    RadioButton genderButton = (RadioButton) findViewById(checkedId);
                    gender = genderButton.getText().toString();
                    if (gender.equals("男性") == true){
                        gender = "male";
                    }else{
                        gender = "famale";
                    }
                }

                String name = nameText.getText().toString();
                String y = yearText.getText().toString();
                String m = String.valueOf(month);
                String d = String.valueOf(day);
                String password = passwordText.getText().toString();

                if (gender != null && name.equals("") == false && y.equals("") == false && password.equals("") == false){
                    String message = "ニックネーム : " + name + '\n' + "性別 : " + gender + '\n' + "生年月日 : " + y + '年' + m + '月' + d + '日' + '\n' + "居住地 : " + area + '\n' + "password : " + password;
                    String sendMessage = String.valueOf(ENTRY) + ",{" + USER_NAME + ':' + name + ',' + PASSWORD + ':' + password + ',' + GENDER + ':' + gender + ',' + BIRTHDAY + ':' + y + ':' + m + ':' + d + ',' + AREA + ':' + area;
                    // 入力確認ダイアログの表示
                    DialogFragment entryFragment = new EntryDialogFragment(context, message, sendMessage);
                    entryFragment.show(getSupportFragmentManager(), "entry");
                }else{
                    System.out.println("no");
                    Toast.makeText(context, "a", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
