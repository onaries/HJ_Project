package com.imagelab.smartpowerman;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.imagelab.smartpowerman.Handler.BackPressCloseHandler;
import com.imagelab.smartpowerman.Notification.QuickstartPreferences;

import java.util.ArrayList;

/*
* Main Activity
*
*/
public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener{

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    private BackPressCloseHandler backPressCloseHandler;

    private ImageButton btn_faq, btn_graph;
    private PieChart pieChart;

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
        //pieChart = (PieChart) findViewById(R.id.pieChart);

        //createChart();

//        btn_faq = (ImageButton) findViewById(R.id.btn_faq);
//        btn_graph = (ImageButton) findViewById(R.id.btn_graph);

        // 버튼 클릭
//        btn_faq.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn_faq_clicked(v);
//            }
//        });
//        btn_graph.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                btn_graph_clicked(v);
//            }
//        });
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

    // 원형그래프 함수
    public void createChart(){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

//        pieChart.setCenterTextTypeface(mTfLight);
//        pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.setOnChartValueSelectedListener(this);

        setData(4, 100);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pieChart.setEntryLabelColor(Color.WHITE);
//        pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(12f);
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}

