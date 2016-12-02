package com.imagelab.smartpowerman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PastUsageActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_usage);

        // ActionBar 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);              // 뒤로 가기 버튼 활성화
        getSupportActionBar().setDisplayShowCustomEnabled(true);            // 액션바 커스텀 뷰 활성화
        getSupportActionBar().setCustomView(R.layout.actionbar_pastusage);      // 액션바 커스텀 레이아웃 지정




    }
}
