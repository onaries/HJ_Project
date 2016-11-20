package com.imagelab.smartpowerman;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.jar.Manifest;

public class LoginActivity extends AppCompatActivity {

    private EditText idEditText;
    private EditText pwdEditText;

    private Button signinButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Resource 할당
        idEditText = (EditText) findViewById(R.id.idEditText);
        pwdEditText = (EditText) findViewById(R.id.pwdEditText);
        signinButton = (Button) findViewById(R.id.signInButton);
        signupButton = (Button) findViewById(R.id.signUpButton);

        // 버튼 클릭
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinButton_clicked(view);
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupButton_clicked(view);
            }
        });

        checkPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // 로그인 버튼 클릭 시
    public void signinButton_clicked(View v){
        // ID 항목이 비어있다면
        if (idEditText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "아이디를 입력해주십시오", Toast.LENGTH_LONG).show();
        }
        // PWD 항목이 비어있다면
        else if (pwdEditText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주십시오", Toast.LENGTH_LONG).show();
        }
        // 그 외의 경우 (둘다 빈칸이 아닌 경우)
        else {
            String id = idEditText.getText().toString();        // ID 값
            String pwd = pwdEditText.getText().toString();      // PWD 값

            String pwd_md5 = getMD5(pwd);   // 암호화된 PWD 값

            // 테스트
            Toast.makeText(getApplicationContext(), id + " " + pwd_md5, Toast.LENGTH_LONG).show();

        }
    }

    // 등록 버튼 클릭 시
    public void signupButton_clicked(View v){
        // RegisterActivity 실행
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // 암호화 함수
    public String getMD5(String str){
        String MD5 = "";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            MD5 = sb.toString();

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            MD5 = null;
        }
        return MD5;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(android.Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(android.Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(android.Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED
                    ){
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_NETWORK_STATE,
                        android.Manifest.permission.VIBRATE,
                        android.Manifest.permission.GET_ACCOUNTS
                }, 1);
            }
        }
    }
}
