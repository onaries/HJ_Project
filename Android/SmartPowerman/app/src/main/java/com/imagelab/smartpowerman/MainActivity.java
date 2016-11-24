package com.imagelab.smartpowerman;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.imagelab.smartpowerman.Handler.BackPressCloseHandler;
import com.imagelab.smartpowerman.Notification.QuickstartPreferences;

/*
* Main Activity
*
*/
public class MainActivity extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    private BackPressCloseHandler backPressCloseHandler;

    private ImageButton btn_faq, btn_graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Boolean loginCheck = intent.getBooleanExtra("LOGIN", false);

        // 로그인 체크
        if (!loginCheck) {
            this.finish();      // 비정상접속한 경우 강제종료
        }
        setContentView(R.layout.activity_main);

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

        // ActionBar 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);          // 뒤로 가기 버튼 활성화
        getSupportActionBar().setDisplayShowCustomEnabled(true);        // 액션바 커스텀 뷰 활성화
        getSupportActionBar().setCustomView(R.layout.actionbar_main);   // 액션바 커스텀 레이아웃 지정

        // BackButton Press Close Handler
        backPressCloseHandler = new BackPressCloseHandler(this);

        // Resource 할당
        btn_faq = (ImageButton) findViewById(R.id.btn_faq);
        btn_graph = (ImageButton) findViewById(R.id.btn_graph);

        // 버튼 클릭
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_faq_clicked(v);
            }
        });
        btn_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_graph_clicked(v);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
        checkPlayServices();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    // Back 버튼 함수
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // 메뉴 버튼 추가하기 (강제로 ... 버튼 안나오게)
        for (int i = 0; i < menu.size(); i++){
            MenuItem item = menu.getItem(i);
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            // 뒤로 가기
            case android.R.id.home:
                this.finish();
                return true;

            // 알림 버튼
            case R.id.action_noti:
                Intent intent2 = new Intent(MainActivity.this, NotificationActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent2);
                return true;

            // 설정 버튼
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
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

    // FAQ 버튼 클릭 함수
    public void btn_faq_clicked(View v){
        Intent intent = new Intent(MainActivity.this, FAQActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    // 그래프 버튼 클릭 함수
    public void btn_graph_clicked(View v){
        Intent intent = new Intent(MainActivity.this, GraphActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
