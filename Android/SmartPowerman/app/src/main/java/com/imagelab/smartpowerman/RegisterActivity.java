package com.imagelab.smartpowerman;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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

import com.imagelab.smartpowerman.Network.Mysql;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private Button btnCancel, btnSignUp, btnIDcheck;
    private EditText register_id, register_pwd, register_pwd2, register_phone, register_email, register_name;

    private int idCheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Resource 할당
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnIDcheck = (Button) findViewById(R.id.btn_idcheck);
        register_id = (EditText) findViewById(R.id.register_id);
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
        setTitle("등록");

        // ID 중복확인
        register_id.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
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
        register_id.addTextChangedListener(textWatcher);




    }

    public void btnCancel_clicked(View v){
        this.finish();
    }

    public void btnSignUp_clicked(View v){
        // ID가 입력되지 않았을 경우
        if(register_id.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_LONG).show();
        }
        // ID 중복확인이 안된경우
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
        // 이메일이 입력되지 않았을 경우
        else if (register_email.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "이메일을 입력하세요", Toast.LENGTH_LONG).show();
        }
        // 그 외
        else {

        }
    }

    public void btnIDcheck_clicked(View v) {
        // ID가 입력되지 않았을 경우
        if(register_id.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_LONG).show();
        }
        // 그 외
        else {
            // 중복확인 하기

            new AsyncTask<Object, Object, Object>() {
                String id = register_id.getText().toString();
                int res = 0;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Object doInBackground(Object... params) {
                    Mysql task = new Mysql("http://168.131.153.24/sql/mysql_sel_user_id.php?id=" + id);
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
                        Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다", Toast.LENGTH_LONG).show();
                        idCheck = 1;
                    }
                    else if (res == 2){
                        Toast.makeText(getApplicationContext(), "서버 에러입니다", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "사용중인 아이디입니다", Toast.LENGTH_LONG).show();
                    }
                }
            }.execute();
        }
    }

}
