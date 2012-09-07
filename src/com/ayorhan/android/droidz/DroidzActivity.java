package com.ayorhan.android.droidz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created with IntelliJ IDEA.
 * User: yigithan
 * Date: 9/7/12
 * Time: 12:26 PM
 */

public class DroidzActivity extends Activity{
    private static final String TAG = DroidzActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Turn the title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Make full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set MainGamePanel as the View
        setContentView(new MainGamePanel(this));

        Log.d(TAG, "View Added");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }
}