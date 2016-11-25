package com.imagelab.smartpowerman;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.imagelab.smartpowerman.Handler.BackPressCloseHandler;
import com.imagelab.smartpowerman.Network.Mysql;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private EditText idEditText;
    private EditText pwdEditText;

    private Button signinButton, signupButton, fotgotButton;
    private CheckBox chb_saveid, chb_autologin;

    private BackPressCloseHandler backPressCloseHandler;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Resource 할당
        idEditText = (EditText) findViewById(R.id.idEditText);
        pwdEditText = (EditText) findViewById(R.id.pwdEditText);
        signinButton = (Button) findViewById(R.id.signInButton);
        signupButton = (Button) findViewById(R.id.signUpButton);
        fotgotButton = (Button) findViewById(R.id.forgotButton);
        chb_saveid = (CheckBox) findViewById(R.id.chb_saveid);
        chb_autologin = (CheckBox) findViewById(R.id.chb_autologin);

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
        fotgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotButton_clicked(view);
            }
        });

        // 설정값 불러오기
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // 이메일 저장
        if (sharedPreferences.getBoolean("saveid", false)){
            idEditText.setText(sharedPreferences.getString("user_email", ""));
            chb_saveid.setChecked(true);
        }

        // 자동 로그인
        if (sharedPreferences.getBoolean("autologin", false)) {
            pwdEditText.setText(sharedPreferences.getString("user_pwd", ""));
            chb_autologin.setChecked(true);
            signinButton_clicked(new View(getApplicationContext()));
        }



        /* 체크 박스 선택 */
        // 아이디저장 선택
        chb_saveid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 클릭 시
                if (chb_saveid.isChecked()){
                    sharedPreferences.edit().putBoolean("saveid", true).apply();
                    sharedPreferences.edit().putString("user_email", idEditText.getText().toString()).apply();
                }

                // 해제 시
                else {
                    sharedPreferences.edit().putBoolean("saveid", false).apply();
                }
            }
        });
        // 자동로그인 선택
        chb_autologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이디 저장 선택 시에만 되게
                if (chb_saveid.isChecked()){
                    // 클릭 시
                    if (chb_autologin.isChecked()){
                        sharedPreferences.edit().putBoolean("autologin", true).apply();
                        sharedPreferences.edit().putString("user_pwd", pwdEditText.getText().toString()).apply();
                    }

                    // 해제 시
                    else {
                        sharedPreferences.edit().putBoolean("autologin", false).apply();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "아이디 저장을 선택해야지만 자동로그인을 할 수 있습니다", Toast.LENGTH_LONG).show();
                    chb_autologin.setChecked(false);
                }

            }
        });

        // 권한 설정
        checkPermission();

        // BackButton 핸들러 설정
        backPressCloseHandler = new BackPressCloseHandler(this);

        // ActionBar 설정
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_login);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    // Back버튼 클릭 시
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
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

            new AsyncTask<Object, Object, Boolean>() {

                String email = idEditText.getText().toString();        // 이메일 값
                String pwd = pwdEditText.getText().toString();      // PWD 값

                String pwd_md5 = getMD5(pwd);   // 암호화된 PWD 값

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Boolean doInBackground(Object... params) {
                    Mysql task = new Mysql("http://168.131.153.24/sql/mysql_user_login.php?email=" + email + "&pwd=" + pwd_md5);
                    String result = task.phpTask();

                    // 결과값이 1일 경우
                    if (result.equals("1\n")){
                        return true;    // 로그인 성공
                    }

                    return false;   // 로그인 실패
                }



                @Override
                protected void onPostExecute(Boolean o) {
                    super.onPostExecute(o);

                    // 아이디와 비밀번호가 데이터베이스에 있는 데이터와 일치할 경우 메인 액티비티로 이동
                    if (o) {
                        Toast.makeText(getApplicationContext(), "로그인 되었습니다", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("LOGIN", true);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다", Toast.LENGTH_LONG).show();
                    }
                }
            }.execute();
        }
    }

    // 등록 버튼 클릭 시
    public void signupButton_clicked(View v){
        // RegisterActivity 실행
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // 아이디 비밀번호찾기 버튼 클릭 시
    public void forgotButton_clicked(View v){
        // ForgotActivity 실행
        Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // 암호화 함수
    public static String getMD5(String str){
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

    // 마시멜로우 이상에서 권한 설정
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
