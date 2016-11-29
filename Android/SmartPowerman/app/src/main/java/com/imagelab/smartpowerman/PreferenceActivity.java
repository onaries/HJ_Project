package com.imagelab.smartpowerman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

public class PreferenceActivity extends AppCompatActivity {

    private ListView list_preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        // ActionBar 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                 // 뒤로 가기 버튼 활성화
        getSupportActionBar().setDisplayShowCustomEnabled(true);               // 액션바 커스텀 뷰 활성화
        getSupportActionBar().setCustomView(R.layout.actionbar_settings);      // 액션바 커스텀 레이아웃 지정

        // Resource 할당
        list_preference = (ListView) findViewById(R.id.list_preference);

        // 이전 액티비티에서 값 불러오기
        int result = getIntent().getIntExtra("num", 0);

        // 이전 액티비티에서 불러온 값을 이용해 리스트뷰 구성
        switch (result) {
            case 0:             // 개인정보변경 선택
                break;
            case 1:             // 사용요금계획 선택
                break;
            case 2:             // 요금 계산 선택
                break;
            case 3:             // 알림 설정 선택
                break;

        }


        Log.i("num",  String.valueOf(result));

    }
}
