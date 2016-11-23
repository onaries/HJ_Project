package com.imagelab.smartpowerman;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.imagelab.smartpowerman.Network.Mysql;
import com.imagelab.smartpowerman.Notification.QuickstartPreferences;
import com.imagelab.smartpowerman.Notification.RegistrationIntentService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "RegisterActivity";
    private Button btnCancel, btnSignUp, btnIDcheck;
    private EditText register_pwd, register_pwd2, register_phone, register_email, register_name;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    private int idCheck = 0;
    private String strGCM = "";

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();

        // 설정값 불러오기
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        strGCM = sharedPreferences.getString("GCM", null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Resource 할당
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnIDcheck = (Button) findViewById(R.id.btn_idcheck);
        register_pwd = (EditText) findViewById(R.id.register_pwd);
        register_pwd2 = (EditText) findViewById(R.id.register_pwd2);
        register_phone = (EditText) findViewById(R.id.register_phone);
        register_email = (EditText) findViewById(R.id.register_email);
        register_name = (EditText) findViewById(R.id.register_name);

        // 버튼 클릭
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnCancel_clicked(view);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignUp_clicked(view);
            }
        });
        btnIDcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIDcheck_clicked(v);
            }
        });

        // ActionBar 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_register);

        // EMAIL 중복확인
        register_email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
        TextWatcher emailTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 변경되기 전에 입력 되었던 텍스트에 대한 내용
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 변경된 텍스트의 결과
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 텍스트가 변경되었을 때의 결과 - 변경되었을 경우
                if (idCheck == 1){
                    idCheck = 0;    // 아이디 중복확인을 다시하도록 설정
                }
            }
        };
        register_email.addTextChangedListener(emailTextWatcher);

        // 전화번호 포맷
        register_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // GCM 알림
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences.getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.i(TAG, "전송");
                }
                else {
                    Log.i(TAG, "에러");
                }
            }
        };

        if (checkPlayServices()) {
            startService(new Intent(this, RegistrationIntentService.class));
        }


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

    public void btnCancel_clicked(View v){
        this.finish();
    }

    public void btnSignUp_clicked(View v){
        // 이메일이 입력되지 않았을 경우
        if(register_email.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "이메일을 입력하세요", Toast.LENGTH_LONG).show();
        }
        // 이메일 중복확인이 안된경우
        else if (idCheck == 0){
            Toast.makeText(getApplicationContext(), "중복확인 버튼을 눌러주세요", Toast.LENGTH_LONG).show();
        }
        // 비밀번호가 입력되지 않았을 경우
        else if (register_pwd.getText().toString().length() == 0 || register_pwd2.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
        }
        // 비밀번호가 일치하지 않는 경우(재입력 값과)
        else if (!register_pwd.getText().toString().equals(register_pwd2.getText().toString()) ){
            Toast.makeText(getApplicationContext(), "비밀번호와 비밀번호 재입력 값이 다릅니다", Toast.LENGTH_LONG).show();
        }
        else if (register_name.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "이름을 입력하세요", Toast.LENGTH_LONG).show();
        }
        // 전화번호가 입력되지 않았을 경우
        else if (register_phone.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "전화번호를 입력하세요", Toast.LENGTH_LONG).show();
        }
        // 그 외
        else {
            // DB로 전송하기
            new AsyncTask<Object, Object, Object>() {

                String email = register_email.getText().toString();
                String pwd = LoginActivity.getMD5(register_pwd.getText().toString());
                String name = register_name.getText().toString();
                String phone = register_phone.getText().toString();
                String gcm = strGCM;
                int res = 0;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    Log.i("TEST", email + pwd + name + phone + gcm);
                }

                @Override
                protected Object doInBackground(Object... params) {
                    Mysql task = new Mysql("http://168.131.153.24/sql/mysql_ins_user_register.php?email=" + email + "&pwd="
                            + pwd + "&name=" + name + "&phone=" + phone + "&gcm=" + gcm);
                    String result = task.phpTask();

                    if (result.equals("")){
                        return true;
                    }
                    return false;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    if((Boolean)o){
                        Toast.makeText(getApplicationContext(), "등록되었습니다", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "오류입니다", Toast.LENGTH_LONG).show();
                    }
                }
            }.execute();

        }
    }

    public void btnIDcheck_clicked(View v) {
        // ID가 입력되지 않았을 경우
        if(register_email.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "이메일을 입력하세요", Toast.LENGTH_LONG).show();
        }
        // 그 외
        else {
            // 중복확인 하기

            new AsyncTask<Object, Object, Object>() {
                String email = register_email.getText().toString();
                int res = 0;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Object doInBackground(Object... params) {
                    Mysql task = new Mysql("http://168.131.153.24/sql/mysql_sel_user_email.php?email=" + email);
                    String result = task.phpTask();

                    try {
                        JSONArray ja = new JSONArray(result);
                        JSONObject jo = ja.getJSONObject(0);
                        Log.i("TEST", jo.toString());

                    } catch (JSONException e){
                        // 아이디가 없는 경우
                        res = 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                        res = 2;
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);

                    // ID가 DB에 없다면
                    if (res == 1){
                        Toast.makeText(getApplicationContext(), "사용가능한 이메일입니다", Toast.LENGTH_LONG).show();
                        idCheck = 1;
                    }
                    else if (res == 2){
                        Toast.makeText(getApplicationContext(), "서버 에러입니다", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "사용중인 이메일입니다", Toast.LENGTH_LONG).show();
                    }
                }
            }.execute();
        }
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }


}
