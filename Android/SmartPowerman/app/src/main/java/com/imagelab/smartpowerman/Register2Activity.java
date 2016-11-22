package com.imagelab.smartpowerman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.imagelab.smartpowerman.R;

public class Register2Activity extends AppCompatActivity {

    private Spinner sp_type, sp_date;
    private RadioButton rb_noti_agree, rb_noti_disagree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        // Resource 할당
        sp_type = (Spinner) findViewById(R.id.sp_type);
        sp_date = (Spinner) findViewById(R.id.sp_date);
        rb_noti_agree = (RadioButton) findViewById(R.id.rb_noti_agree);
        rb_noti_disagree = (RadioButton) findViewById(R.id.rb_noti_disagree);

        // 스피너 목록 추가
        String[] str1 = getResources().getStringArray(R.array.sp_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, str1);
        sp_type.setAdapter(adapter);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] str2 = getResources().getStringArray(R.array.sp_date);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, str2);
        sp_date.setAdapter(adapter1);
        sp_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
