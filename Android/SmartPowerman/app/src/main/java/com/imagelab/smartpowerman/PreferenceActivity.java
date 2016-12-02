package com.imagelab.smartpowerman;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.imagelab.smartpowerman.Network.Mysql;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PreferenceActivity extends AppCompatActivity {

    private ListView list_preference;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // ActionBar 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                 // 뒤로 가기 버튼 활성화
        getSupportActionBar().setDisplayShowCustomEnabled(true);               // 액션바 커스텀 뷰 활성화


        // Resource 할당
        list_preference = (ListView) findViewById(R.id.list_preference);

        // 이전 액티비티에서 값 불러오기
        int result = getIntent().getIntExtra("num", 0);
        user_email = getIntent().getStringExtra("USER_EMAIL");

        // 인텐트



        // 이전 액티비티에서 불러온 값을 이용해 레이아웃 구성
        switch (result) {
            case 0:             // 개인정보변경 선택
                // 레이아웃 설정
                setContentView(R.layout.activity_pref_privacy);

                // 액션바 설정
                getSupportActionBar().setCustomView(R.layout.actionbar_pref_privacy);      // 액션바 커스텀 레이아웃 지정

                // DB 작업
                new AsyncTask<Object, Object, Boolean>(){

                    EditText txt_pri_email, txt_pri_name, txt_pri_phone;
                    String name, phone;

                    ProgressDialog dialog;


                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();

                        // Resource 할당
                        txt_pri_email = (EditText) findViewById(R.id.txt_pri_email);
                        txt_pri_name = (EditText) findViewById(R.id.txt_pri_name);
                        txt_pri_phone = (EditText) findViewById(R.id.txt_pri_phone);

                        // ProgressDialog 실행
                        dialog = new ProgressDialog(PreferenceActivity.this);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setMessage("정보를 불러오는 중입니다");
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();

                    }

                    @Override
                    protected Boolean doInBackground(Object... params) {

                        Mysql task = new Mysql("http://168.131.153.24/sql/mysql_sel_user_email.php?email=" + user_email);
                        String result = task.phpTask();

                        if (result.equals("no email\n")){
                            return false;
                        }

                        try {
                            JSONArray ja = new JSONArray(result);
                            JSONObject jo = ja.getJSONObject(0);
                            name = jo.getString("user_name");
                            phone = jo.getString("user_phone");
                            return true;

                        } catch (JSONException e){
                            return false;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    }

                    @Override
                    protected void onPostExecute(Boolean o) {
                        super.onPostExecute(o);

                        dialog.dismiss();

                        // 결과값이 true 인 경우
                        if (o) {
                            txt_pri_email.setText(user_email);
                            txt_pri_name.setText(name);
                            txt_pri_phone.setText(phone);
                        }
                        else {
                            Toast.makeText(PreferenceActivity.this, "정보를 불러오지 못했습니다", Toast.LENGTH_LONG).show();
                        }

                    }
                }.execute();

//                Intent privacyIntent = new Intent(this, PrefPrivacyActivity.class);
//                privacyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(privacyIntent);
                break;
            case 1:             // 사용요금계획 선택
                // 레이아웃 설정
                setContentView(R.layout.activity_pref_charge_plan);

                // 액션바 설정
                getSupportActionBar().setCustomView(R.layout.actionbar_pref_charge_plan);      // 액션바 커스텀 레이아웃 지정

//                Intent chargePlanIntent = new Intent(this, PrefChargePlanActivty.class);
//                chargePlanIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(chargePlanIntent);

                break;
            case 2:             // 요금 계산 선택
                // 레이아웃 설정
                setContentView(R.layout.activity_pref_charge);

                // 액션바 설정
                getSupportActionBar().setCustomView(R.layout.actionbar_pref_charge);      // 액션바 커스텀 레이아웃 지정

//                Intent chargeIntent = new Intent(this, PrefChargeActivity.class);
//                chargeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(chargeIntent);
                break;
            case 3:             // 알림 설정 선택
                break;

            default:

                break;

        }


        Log.i("num",  String.valueOf(result));

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
