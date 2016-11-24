package com.imagelab.smartpowerman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
* 아이디 / 비밀번호 찾기 액티비티
*
* */
public class ForgotActivity extends AppCompatActivity {

    private Button btn_id_forgot, btn_pwd_forgot;
    private EditText forgot_name, forgot_email1, forgot_id, forgot_email2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        // Resource 할당
        btn_id_forgot = (Button) findViewById(R.id.btn_id_forgot);
        btn_pwd_forgot = (Button) findViewById(R.id.btn_pwd_forgot);
        forgot_name = (EditText) findViewById(R.id.forgot_name);
        forgot_email1 = (EditText) findViewById(R.id.forgot_email1);
        forgot_id = (EditText) findViewById(R.id.forgot_id);
        forgot_email2 = (EditText) findViewById(R.id.forgot_email2);

        // 버튼 클릭
        btn_id_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_id_forgot_clicked(view);
            }
        });
        btn_pwd_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_pwd_forgot_clicked(view);
            }
        });

        // ActionBar 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);              // 뒤로 가기 버튼 활성화
        getSupportActionBar().setDisplayShowCustomEnabled(true);            // 액션바 커스텀 뷰 활성화
        getSupportActionBar().setCustomView(R.layout.actionbar_forgot);     // 액션바 커스텀 레이아웃 지정
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

    // 아이디찾기 버튼 클릭 시
    public void btn_id_forgot_clicked(View v){
        // 이름값이 빈칸일 경우
        if (forgot_name.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "이름이 빈칸입니다", Toast.LENGTH_LONG).show();
        }
        // 이메일값이 빈칸일 경우
        else if (forgot_email1.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "이메일이 빈칸입니다", Toast.LENGTH_LONG).show();
        }
        // 그 외
        else {

        }
    }

    // 비밀번호찾기 버튼 클릭 시
    public void btn_pwd_forgot_clicked(View v){
        // 아이디값이 빈칸일 경우
        if (forgot_id.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "아이디가 빈칸입니다", Toast.LENGTH_LONG).show();
        }
        // 이메일값이 빈칸일 경우
        else if (forgot_email2.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "이메일이 빈칸입니다", Toast.LENGTH_LONG).show();
        }
        // 그 외
        else {

        }
    }
}
