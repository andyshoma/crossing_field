package com.example.crossingfield.entry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crossingfield.R;
import com.example.crossingfield.home.HomeActivity;
import com.example.crossingfield.lib.MyHTTP;
import com.example.crossingfield.lib.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.crossingfield.lib.MathConstants.*;
import static com.example.crossingfield.lib.StringConstants.*;

public class EntryActivity extends AppCompatActivity {

    private Context context;
    private static final int RESULT_PICK_IMAGEFILE = 1000;

    private EditText nameText;
    private Spinner monthSpinner;
    private Spinner daySpinner;
    private Spinner areaSpinner;
    private EditText yearText;
    private EditText passwordText;
    private RadioGroup rg;

    private ImageView imageView;

    // 入力した生年月日
    private Integer month;
    private Integer day;

    // 入力した都道府県
    private String area;

    // 送信するデータ
    private String image;

    // 送信する画像データ
    private Bitmap bmp;
    private String imageSt;


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

        Button imageButton = (Button)findViewById(R.id.send_image);

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

        // imageボタンが押された時
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
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

                if (gender != null && name.equals("") == false && y.equals("") == false && password.equals("") == false && image != null){

                    String send_message = String.valueOf(ENTRY) + ',' + name + ',' + password + ',' + gender + ',' + y + '/' + m + '/' + d + ',' + area + ',';

                    String message = "ニックネーム : " + name + '\n' + "性別 : " + gender + '\n' + "生年月日 : " + y + '年' + m + '月' + d + '日' + '\n' + "居住地 : " + area + '\n' + "password : " + password;

                    // 入力確認ダイアログの表示
                    DialogFragment entryFragment = new EntryDialogFragment(context, message, send_message, bmp, image, name);
                    entryFragment.show(getSupportFragmentManager(), "entry");
                }else{
                    System.out.println("no");
                    Toast.makeText(context, "a", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData){
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK){
            Uri uri = null;
            if (resultData != null){
                uri = resultData.getData();

                try {
                    bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                    byte[] bImage =baos.toByteArray();
                    String base64 = Base64.encodeToString(bImage, Base64.DEFAULT);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("image", base64);

                    image = jsonObject.toString();
                }catch (IOException e){
                    e.printStackTrace();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
