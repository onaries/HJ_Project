package com.imagelab.smartpowerman.Handler;

import android.app.Activity;
import android.widget.Toast;

import com.imagelab.smartpowerman.R;

/**
 * Created by Nanodir on 2016-11-22.
 */

public class BackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity, R.string.str_button_close, Toast.LENGTH_SHORT);
        toast.show();
    }
}
