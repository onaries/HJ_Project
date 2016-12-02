package com.imagelab.smartpowerman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.imagelab.smartpowerman.Adapter.ListData;


public class SettingsActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private com.imagelab.smartpowerman.Adapter.ListAdapter listAdapter;

    private String user_email;

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

        // 이전 액티비티로부터 값 받기 (이메일 값)
        user_email = getIntent().getStringExtra("USER_EMAIL");

        // 커스텀 리스트뷰 구성
        ListData[] listDatas = new ListData[6];
        ListData listData1 = new ListData(R.drawable.ic_pref_privacy, R.string.pref_header_privacy, 0, 1);
        ListData listData2 = new ListData(R.drawable.ic_pref_charge_plan, R.string.pref_header_charge_plan, 0, 1);
        ListData listData3 = new ListData(R.drawable.ic_pref_charge, R.string.pref_header_charge, 0, 1);
        ListData listData4 = new ListData(R.drawable.ic_pref_notification, R.string.pref_header_notifications, 1, 0);
        ListData listData5 = new ListData(R.drawable.ic_pref_secession, R.string.pref_header_leave);
        ListData listData6 = new ListData(R.drawable.ic_pref_logout, R.string.pref_header_logout);
        listDatas[0] = listData1;
        listDatas[1] = listData2;
        listDatas[2] = listData3;
        listDatas[3] = listData4;
        listDatas[4] = listData5;
        listDatas[5] = listData6;


//        adapter = new ArrayAdapter<String>(this, R.layout.list_settings, list);
//        listView.setAdapter(adapter);


        // 리스트뷰 설정
        listAdapter = new com.imagelab.smartpowerman.Adapter.ListAdapter(this, R.layout.list_settings, listDatas);
        listView.setAdapter(listAdapter);   // 어댑터 설정

        listView.setPadding(16, 16, 16, 16);    // 여백 설정


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {      // 항목 선택 리스너

//                String strPos = String.valueOf(position);

                Intent intent;
                intent = new Intent(SettingsActivity.this, PreferenceActivity.class);
                intent.putExtra("num", position);
                intent.putExtra("USER_EMAIL", user_email);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // 뒤로 가기
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
