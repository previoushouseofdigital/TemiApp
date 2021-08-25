package com.digitopia.temiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnBatteryStatusChangedListener;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionDataChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnMovementVelocityChangedListener;
import com.robotemi.sdk.listeners.OnRobotLiftedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnUserInteractionChangedListener;
import com.robotemi.sdk.map.OnLoadMapStatusChangedListener;
import com.robotemi.sdk.model.DetectionData;
import com.robotemi.sdk.permission.OnRequestPermissionResultListener;
import com.robotemi.sdk.permission.Permission;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        OnRobotReadyListener,
        OnBatteryStatusChangedListener,
        OnGoToLocationStatusChangedListener,
        OnDetectionStateChangedListener,
        OnUserInteractionChangedListener,
        OnRequestPermissionResultListener,
        OnRobotLiftedListener,
        OnMovementVelocityChangedListener,
        OnBeWithMeStatusChangedListener,
        OnLoadMapStatusChangedListener,
        OnDetectionDataChangedListener,
        Robot.WakeupWordListener {

    private static final String TAG = "MAIN";
    private static final String ROBOT_TAG = "ROBOT";
    public static final String WEBVIEW_URL = "";
    public static ActionBar actionBar;

    public static Robot sRobot;
    public static String sSerialNumber;
    private boolean mPermissionGranted;

    public static Activity fa;

    private static String test_json = "{\"button_names\":[{\"button_name\" : \"Test site\", \"url\" : \"http://192.168.2.15\"}, {\"button_name\" : \"What's my browser\", \"url\" : \"https://www.whatismybrowser.com\"}]}";



    //Button startButton = (Button) findViewById(R.id.start);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = findViewById(R.id.rootContainer);

//        JSONObject jsonObject = null;
//        JSONArray buttonArray = null;
//
//        try {
//            jsonObject = new JSONObject(test_json);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        try {

            //assert jsonObject != null;
            //buttonArray = jsonObject.getJSONArray("button_names");

            //List<Button> buttonList = null;


//            for (int i = 0; i < buttonArray.length(); i++) {
//                JSONObject nObject = new JSONObject(buttonArray.getString(i));
//
//                buttonList.set(i, new Button(this));
//
//                buttonList.get(i).setText(nObject.getString("button_name"));
//                buttonList.get(i).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//                buttonList.get(i).setOnClickListener(v -> {
//                    try {
//                        showWebview(this, nObject.getString("url"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                });
//
//                if (linearLayout != null) {
//                    linearLayout.addView(buttonList.get(i));
//                }



//
//            } catch (JSONException jsonException) {
//            jsonException.printStackTrace();
//        }



        // Create Button Dynamically
        Button btnShow = new Button(this);
        btnShow.setText("Launch test site");
        btnShow.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        //btnShow.setOnClickListener(v -> showWebview(this, "https://zorabots-qrscan.hod.cloud/"));
        btnShow.setOnClickListener(v -> showWebview(this, "http://192.168.2.15"));

                //Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_LONG).show());

        // Add Button to LinearLayout
        if (linearLayout != null) {
            linearLayout.addView(btnShow);

        }


        fa = this;
        sRobot = Robot.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "Robot has started");

        sRobot.hideTopBar();

//        sRobot.addOnRobotReadyListener(this);
//        sRobot.addOnBatteryStatusChangedListener(this);
//        sRobot.addOnGoToLocationStatusChangedListener(this);
//        sRobot.addOnDetectionStateChangedListener(this);
//        sRobot.addOnUserInteractionChangedListener(this);
//        sRobot.addOnRequestPermissionResultListener(this);
//        sRobot.addOnRobotLiftedListener(this);
//        sRobot.addOnMovementVelocityChangedListener(this);
//        sRobot.addOnBeWithMeStatusChangedListener(this);
//        sRobot.addOnLoadMapStatusChangedListener(this);
//        sRobot.addOnDetectionDataChangedListener(this);

        // add robot event listener
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Robot has stopped");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {

        // remove robot event listeners
        sRobot.removeOnRobotReadyListener(this);
        sRobot.removeOnBatteryStatusChangedListener(this);
        sRobot.removeOnGoToLocationStatusChangedListener(this);
        sRobot.removeOnDetectionStateChangedListener(this);
        sRobot.removeOnUserInteractionChangedListener(this);
        sRobot.removeOnRequestPermissionResultListener(this);
        sRobot.removeOnRobotLiftedListener(this);
        sRobot.removeOnMovementVelocityChangedListener(this);
        sRobot.removeOnBeWithMeStatusChangedListener(this);
        sRobot.removeOnLoadMapStatusChangedListener(this);
        sRobot.removeOnDetectionDataChangedListener(this);

        finishAndRemoveTask();

        super.onDestroy();
    }

    void refreshTemiUi() {
        Log.i(ROBOT_TAG, "Refreshed UI");
        try {
            ActivityInfo activityInfo = getPackageManager()
                    .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            sRobot.onStart(activityInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void quitApp(View view) {
        MainActivity.this.finish();
        System.exit(0);
    }



    //----------------------------------------------------------------------------------------------
    // ROBOT EVENT LISTENERS
    //----------------------------------------------------------------------------------------------
    /**
     * Configures robot after it is ready
     * @param isReady True if robot initialized correctly; False otherwise
     */
    @SuppressLint("LogNotTimber")
    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {

            List<Permission> permissions = Arrays.asList(Permission.MAP, Permission.SETTINGS, Permission.FACE_RECOGNITION, Permission.SEQUENCE, Permission.UNKNOWN);

            sRobot.requestPermissions(permissions, 1);
            sRobot.setDetectionModeOn(true);

            //sRobot.requestToBeKioskApp();
            //sRobot.setKioskModeOn(true);

            //showWebview(this, "http://192.168.2.15");

            sSerialNumber = sRobot.getSerialNumber();
            sRobot.toggleNavigationBillboard(true); // hides navigation billboard
            sRobot.setTopBadgeEnabled(false);


            //sRobot.hideTopBar();


            Log.i(TAG, "[ROBOT][READY]");

            //showWebview(this, "http://192.168.2.15");
            //showWebview(this, "https://www.whatismybrowser.com");
            //showWebview(this, "https://play.google.com/store/apps/details?id=com.google.android.webview");

            // place app in temi's top bar menu
//            try {
//                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
//                sRobot.onStart(activityInfo);
//            } catch (PackageManager.NameNotFoundException e) {
//                throw new RuntimeException(e);
//            }

            refreshTemiUi();

//            startButton.setOnClickListener(v -> {
//                showWebview(this, "http://192.168.2.15");
//            });


        }
    }


    @Override
    public void onRobotLifted(boolean isLifted, String reason) {
        Log.i(ROBOT_TAG, "Robot has been lifted: " + reason);
        JavascriptHandler.onRobotLifted(isLifted, reason);
    }

    @Override
    public void onMovementVelocityChanged(float velocity) {
        Log.i(ROBOT_TAG, "Movement at velocity: " + String.valueOf(velocity));
        JavascriptHandler.onMovementVelocityChanged(velocity);
    }

    @Override
    public void onBatteryStatusChanged(@Nullable BatteryData batteryData) {
        Log.i(TAG, "Battery update: " + String.valueOf(batteryData));

        JavascriptHandler.onBatteryChargingChanged(batteryData.isCharging());
        JavascriptHandler.onBatteryLevelChanged(batteryData.getBatteryPercentage());
    }

    @Override
    public void onDetectionStateChanged(int state) {
        Log.i(TAG, "User detected " + state);
        JavascriptHandler.onDetectionStateChanged(state);
    }

    @Override
    public void onGoToLocationStatusChanged(@NotNull String location, @NotNull String status, int id, @NotNull String description) {
        Log.i(ROBOT_TAG, "Location update: " + location + " status: " + status + " id: " + id + " desc: " + description);

        try {
            JavascriptHandler.onGoToLocationStatusChanged(location, status, id, description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUserInteraction(boolean interaction) {
        //Log.i(ROBOT_TAG, "User interaction");
        //JavascriptHandler.OnUserInteractionChanged(interaction);
    }

    @Override
    public void onBeWithMeStatusChanged(@NotNull String status) {
        Log.i(ROBOT_TAG, "Be with me update " + status);
        JavascriptHandler.onBeWithMeStatus(status);
    }

    @Override
    public void onRequestPermissionResult(@NotNull Permission permission, int i, int i1) {
        Log.i("Requested Permission : ", String.valueOf(permission));
    }

    @Override
    public void onDetectionDataChanged(@NotNull DetectionData detectionData) {
        //Log.i(ROBOT_TAG, "Detection data: angle = " + detectionData.getAngle() + " distance = " + detectionData.getDistance() + " detected = " + detectionData.isDetected());
        //JavascriptHandler.onDetectionDataChanged(detectionData.getAngle(), detectionData.getDistance(), detectionData.isDetected());
    }

    @Override
    public void onLoadMapStatusChanged(int i) {

        String state = "";

        switch(i) {
            case 0:
                state = "complete";
                break;
            case 1:
                state = "start";
                break;
            case 1000:
                state = "Unkown error";
                break;
            case 2000:
                state = "Robox error";
                break;
            case 2001:
                state = "Loading without charging";
                break;
            case 2002:
                state = "Another task is blocking";
                break;
            case 3000:
                state = "Timeout";
                break;
            case 4000:
                state = "Invalid PB file";
                break;
            case 5000:
                state = "Error while getting data from remote";
                break;
            default:
                state = "Start";
        }

        Log.i(ROBOT_TAG, "Map has status: " + state);
        JavascriptHandler.onLoadMapStatusChanged(state);
    }



    /**
     * Show web-view from URL
     * @param url URL to display
     */
    @SuppressLint("LogNotTimber")
    public void showWebview(Context context, String url) {
        Intent intent = new Intent(this, WebviewActivity.class);
        intent.putExtra(WEBVIEW_URL, url);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                mPermissionGranted = false;
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 10);
            } else {
                mPermissionGranted = true;
            }
        } else {
            mPermissionGranted = true;
        }

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.i(TAG, "URL not found");
        }

        //NDIActivity.main(context);
    }


    @Override
    public void onWakeupWord(@NotNull String s, int i) {
        Log.i(ROBOT_TAG, "Temi was woken up with word " + s + "from " + i + " deg");
        JavascriptHandler.onWakeupWord(s,i);
    }
}