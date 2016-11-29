package com.imagelab.smartpowerman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // ActionBar 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                 // 뒤로 가기 버튼 활성화
        getSupportActionBar().setDisplayShowCustomEnabled(true);               // 액션바 커스텀 뷰 활성화
        getSupportActionBar().setCustomView(R.layout.actionbar_settings);      // 액션바 커스텀 레이아웃 지정

        // Resource 할당
        listView = (ListView) findViewById(R.id.setting_list);

        String[] list = getResources().getStringArray(R.array.settings);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String strPos = String.valueOf(position);
                Toast.makeText(SettingsActivity.this, strPos, Toast.LENGTH_SHORT).show();

                Intent intent;
                intent = new Intent(SettingsActivity.this, PreferenceActivity.class);
                intent.putExtra("num", position);

                switch(position) {
                    case 0:     // 개인정보변경 선택
                        startActivity(intent);
                        break;
                    case 1:     // 사용요금계획 선택
                        startActivity(intent);
                        break;
                    case 2:     // 요금 계산 선택
                        startActivity(intent);
                        break;
                    case 3:     // 알림 설정 선택
                        startActivity(intent);
                        break;
                    case 4:     // 탈퇴하기 선택
                        Log.i("pos", "5");
                        break;
                    case 5:     // 로그아웃 선택
                        Log.i("pos", "6");
                        break;
                    default:
                        Log.i("pos", "default");
                        break;
                }

            }
        });
    }
}
