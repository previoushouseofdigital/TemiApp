package com.digitopia.temiapp;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Switch;

import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.constants.HardButton;
import com.robotemi.sdk.constants.Page;
import com.robotemi.sdk.listeners.OnBatteryStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnUserInteractionChangedListener;
import com.robotemi.sdk.map.MapDataModel;
import com.robotemi.sdk.map.MapImage;
import com.robotemi.sdk.map.MapModel;
import com.robotemi.sdk.navigation.model.Position;
import com.robotemi.sdk.navigation.model.SafetyLevel;
import com.robotemi.sdk.navigation.model.SpeedLevel;
import com.robotemi.sdk.permission.Permission;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.List;

public class JavascriptHandler {

    private static final String ROBOT_TAG = "[ROBOT]: ";
    private static Robot sRobot = MainActivity.sRobot;
    private static WebView webView = WebviewActivity.webView;


    /**
     * Temi system functions
     * */

    // ------- SETTERS
    @JavascriptInterface
    public void setVolume(int volume) {
        sRobot.setVolume(volume);
        Log.i(ROBOT_TAG, "Volume set to:" + volume);
    }

    @JavascriptInterface
    public void setGotoSpeed(String speed) {
        Log.i(ROBOT_TAG, "Speed level is " + speed);
        SpeedLevel speedLevel;

        switch(speed.toLowerCase().trim()) {
            case "high":
                speedLevel = SpeedLevel.HIGH;
                break;
            case "slow":
                speedLevel = SpeedLevel.SLOW;
                break;
            default:
                speedLevel = SpeedLevel.MEDIUM;
        }
        sRobot.setGoToSpeed(speedLevel);
    }

    @JavascriptInterface
    public void setPrivacyMode(boolean mode) {
        sRobot.setPrivacyMode(mode);
        Log.i(ROBOT_TAG, "Privacy mode status is: " + mode);
    }

    @JavascriptInterface
    public void setDetectionModeOn(boolean mode) {
        sRobot.setDetectionModeOn(mode);
        Log.i(ROBOT_TAG, "Detection mode status is: " + mode);
    }

    @JavascriptInterface
    public void setTrackUserOn(boolean mode) {
        sRobot.setTrackUserOn(mode);
        Log.i(ROBOT_TAG, "Track mode status is: " + mode);
    }

    @JavascriptInterface
    public void startPage() {
        sRobot.startPage(Page.HOME);
        Log.i(ROBOT_TAG, "Going to startpage");
    }



    @JavascriptInterface
    public void quitApp() {
        sRobot.setKioskModeOn(false);
        sRobot.setKioskModeOn(false);
        MainActivity.fa.finish();
        System.exit(0);
    }

    @JavascriptInterface
    public void stopWebApp() {
        WebviewActivity.destroyWebView();
        Log.i(ROBOT_TAG, "Stopping webview ...");
    }

    @JavascriptInterface
    public boolean startKioskMode() {
        sRobot.requestToBeKioskApp();
        sRobot.setKioskModeOn(true);
        Log.i(ROBOT_TAG, "Starting kiosk mode");
        return sRobot.isKioskModeOn();
    }

    @JavascriptInterface
    public boolean stopKioskMode() {
        sRobot.setKioskModeOn(false);
        Log.i(ROBOT_TAG, "Stopping kiosk mode");
        return sRobot.isKioskModeOn();
    }




    // ------- GETTERS
    @JavascriptInterface
    public int getBatteryLevel() {
        BatteryData batteryData = sRobot.getBatteryData();
        Log.i(ROBOT_TAG, "Battery is: " + batteryData);
        return batteryData.getBatteryPercentage();
    }

    @JavascriptInterface
    public boolean getChargeStatus() {
        BatteryData batteryData = sRobot.getBatteryData();
        Log.i(ROBOT_TAG, "Battery is: " + batteryData);
        return batteryData.isCharging();
    }

    @JavascriptInterface
    public String getSerialNumber() {
        Log.i(ROBOT_TAG, "Serial number is: " + sRobot.getSerialNumber());
        return sRobot.getSerialNumber();
    }

    @JavascriptInterface
    public boolean getHardButtonStatus() {
        boolean var = sRobot.isHardButtonsDisabled();
        Log.i(ROBOT_TAG, "Hard button status is: " + var);
        return var;
    }

    @JavascriptInterface
    public boolean getPrivacyMode() {
        boolean var = sRobot.getPrivacyMode();
        Log.i(ROBOT_TAG, "Privacy mode status is: " + var);
        return var;
    }

    @JavascriptInterface
    public boolean isDetectionModeOn() {
        boolean var = sRobot.isDetectionModeOn();
        Log.i(ROBOT_TAG, "Detection mode status is: " + var);
        return var;
    }

    @JavascriptInterface
    public boolean isTrackUserOn() {
        boolean var = sRobot.isTrackUserOn();
        Log.i(ROBOT_TAG, "Track user status is: " + var);
        return var;
    }

    @JavascriptInterface
    public String getGotoSpeed() {
        SpeedLevel var = sRobot.getGoToSpeed();
        Log.i(ROBOT_TAG, "Goto speed is: " + var);
        return String.valueOf(var);
    }

    @JavascriptInterface
    public String getPermisionStatus() {
        int var = sRobot.checkSelfPermission(Permission.SETTINGS);
        Log.i(ROBOT_TAG, "Permision is: " + var);
        return String.valueOf(var);
    }

    @JavascriptInterface
    public boolean isSelectedKioskApp() {
        boolean var = sRobot.isSelectedKioskApp();
        Log.i(ROBOT_TAG, "Kiosk is: " + var);
        return var;
    }

    @JavascriptInterface
    public boolean isKioskModeOn() {
        boolean var = sRobot.isKioskModeOn();
        Log.i(ROBOT_TAG, "Kiosk is: " + var);
        return var;
    }

    @JavascriptInterface
    public String getWakeupWord() {
        String var = sRobot.getWakeupWord();
        Log.i(ROBOT_TAG, "Permision is: " + var);
        return var;
    }



    // ------- UTILS

    // Hide temi top bar
    @JavascriptInterface
    public void showTopBar() {
        Log.i(ROBOT_TAG, "Showing top bar");
        sRobot.showTopBar();
    }

    // Show temi top bar
    @JavascriptInterface
    public void hideTopBar() {
        Log.i(ROBOT_TAG, "Showing top bar");
        //MainActivity.refreshTemiUi();
        sRobot.hideTopBar();
    }

    // Toggle navigation billboard
    @JavascriptInterface
    public void toggleNavigationBillboard(boolean mode) {
        Log.i(ROBOT_TAG, "Toggeling navigation billboard " + mode);
        sRobot.toggleNavigationBillboard(mode);
    }

    // Set top badge
    @JavascriptInterface
    public void setTopBadgeEnabled(boolean mode) {
        Log.i(ROBOT_TAG, "Top badge is" + mode);
        sRobot.setTopBadgeEnabled(mode);
    }

    // Restart temi
    @JavascriptInterface
    public void restart() {
        Log.i(ROBOT_TAG, "Restarting .... ");
        sRobot.restart();
    }

    // Shutting down temi
    @JavascriptInterface
    public void shutdown() {
        Log.i(ROBOT_TAG, "Shutting down temi .... ");
        sRobot.shutdown();
    }

    // Locking temi
    @JavascriptInterface
    public void setLocked(boolean mode) {
        Log.i(ROBOT_TAG, "Setting locked mode to " + mode);
        sRobot.setLocked(mode);
    }

    // Getting locked status
    @JavascriptInterface
    public boolean isLocked() {
        boolean mode = sRobot.isLocked();
        Log.i(ROBOT_TAG, "Setting locked mode to " + mode);
        return mode;
    }

    @JavascriptInterface
    public void setHardButtonMode(String button_str, String mode_str) {

        HardButton button;
        HardButton.Mode mode;

        switch(button_str.toLowerCase().trim()) {
            case "main":
                button = HardButton.MAIN;
                break;
            case "power":
                button = HardButton.POWER;
                break;
            case "volume":
                button = HardButton.VOLUME;
                break;
            default:
                button = HardButton.MAIN;
        }

        switch(mode_str.toLowerCase().trim()) {
            case "disabled":
                mode = HardButton.Mode.DISABLED;
                break;
            case "enabled":
                mode = HardButton.Mode.ENABLED;
                break;
            case "main_block_follow":
                mode = HardButton.Mode.MAIN_BLOCK_FOLLOW;
                break;
            default:
                mode = HardButton.Mode.ENABLED;
        }

        sRobot.setHardButtonMode(button, mode);
        //sRobot.setHardButtonsDisabled(mode_str);

        Log.i(ROBOT_TAG, "Setting button " + button + " mode to " + mode);
    }


    /**
     * Temi functions for moving
     * */

    // Tilt screen by angle
    @JavascriptInterface
    public void tiltBy(int angle) {
        sRobot.tiltAngle(angle);
        Log.i(ROBOT_TAG, "Tilt function activated");
    }

    // Turn by angle
    @JavascriptInterface
    public void turnBy(int angle) {
        sRobot.turnBy(angle, 1);
        Log.i(ROBOT_TAG, "Turn function activated");
    }

    // Move in X - Y
    @JavascriptInterface
    public void moveX(int x) {
        sRobot.skidJoy(x, 0);
        //Log.i(ROBOT_TAG, "Move function activated");
    }

    @JavascriptInterface
    public void stopMovement() {
        sRobot.stopMovement();
        Log.i(ROBOT_TAG, "All movement has stoped");
    }

    /**
     * Temi functions for speach etc
     * */

    // TTS Message
    @JavascriptInterface
    public void robotSpeak(String message) {
        sRobot.speak(TtsRequest.create(message, true));
        Log.i(ROBOT_TAG, "Message: " + message);
    }

    /**
    * Temi functions for positioning
    * */

    @JavascriptInterface
    public void beWithMe() {
        sRobot.beWithMe();
        Log.i(ROBOT_TAG, "Robot is following");
    }

    @JavascriptInterface
    public void constraintBeWith() {
        sRobot.constraintBeWith();
        Log.i(ROBOT_TAG, "Robot is following in constrain mode");
    }

    // Goto POI on map
    @JavascriptInterface
    public void goTo(String location) {
        sRobot.goTo(location.toLowerCase().trim());
        Log.i(ROBOT_TAG, "Going to:" + location);
    }

    // Goto X Y position on map
    @JavascriptInterface
    public void goToPosition(float x, float y, float yaw, int tiltAngle) {

        Position pos = new Position(x, y, yaw, tiltAngle);
        sRobot.goToPosition(pos);

        Log.i(ROBOT_TAG, "Going to x: " + x +  " y: " + y);
    }

    /**
     * Temi functions for map & location stuff
     * */

    // Save POI on map
    @JavascriptInterface
    public void saveLocation(String location) {
        sRobot.saveLocation(location);
        Log.i(ROBOT_TAG, "Saved new location:" + location);
    }

    @JavascriptInterface
    public String getLocations() throws JSONException {
        List<String> locationList = sRobot.getLocations();
        Log.i(ROBOT_TAG, "Robot maps:" + locationList);
        return String.valueOf(locationList);
    }


    @JavascriptInterface
    public String getMaps() throws JSONException {
        Log.i(ROBOT_TAG, "Logged robot maps");

        JSONArray mapArray = new JSONArray();
        List<MapModel> mapList = sRobot.getMapList();

        // collect all waypoints
        for (MapModel map : mapList) {
            JSONObject mapJson = new JSONObject();

            mapJson.put("name", map.getName());
            mapJson.put("id", map.getId());

            mapArray.put(mapJson);
        }

        Log.i(ROBOT_TAG, String.valueOf(mapList));
        return mapArray.toString();
    }

    @JavascriptInterface
    public void loadMap(String mapId) throws JSONException {
        Log.i(ROBOT_TAG, "Loaded map " + mapId);
        sRobot.loadMap(mapId);
    }


    /**
     * Temi listener functions callbacks
     * */


    // Updates if folow mode is activated
    public static void onBeWithMeStatus(String status) {
        try {
            webView.evaluateJavascript("javascript: " +"onBeWithMeStatus(\"" + status + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error" +  e);
        }
    }

    // Updates if folow mode is activated
    public static void onGoToLocationStatusChanged(@NotNull String location, @NotNull String status, int id, @NotNull String description) throws JSONException {

        JSONObject json = new JSONObject();

        json.put("location", location);
        json.put("status", status);
        json.put("id", id);
        json.put("description", description);

        try {
            webView.evaluateJavascript("javascript: " +"onGoToLocationStatusChanged(\"" + json + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error" +  e);
        }
    }

    // Updates if battery level has changed
    public static void onBatteryLevelChanged(int status) {
        try {
            webView.evaluateJavascript("javascript: " +"onBatteryLevelChanged(\"" + status + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error" +  e);
        }
    }

    // Updates if battery is charging
    public static void onBatteryChargingChanged(boolean status) {
        try {
            webView.evaluateJavascript("javascript: " +"onBatteryChargingChanged(\"" + status + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error" +  e);
        }
    }

    public static void onLoadMapStatusChanged(String status) {
        try {
            webView.evaluateJavascript("javascript: " +"onLoadMapStatusChanged(\"" + status + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error" +  e);
        }
    }

    public static void onMovementVelocityChanged(float velocity) {
        try {
            webView.evaluateJavascript("javascript: " +"onMovementVelocityChanged(\"" + velocity + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error" +  e);
        }
    }

    public static void OnUserInteractionChanged(boolean status) {
        try {
            webView.evaluateJavascript("javascript: " +"OnUserInteractionChanged(\"" + status + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error" +  e);
        }
    }

    public static void onDetectionStateChanged(int status) {
        try {
            webView.evaluateJavascript("javascript: " +"onDetectionStateChanged(\"" + status + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error" +  e);
        }
    }

    public static void onDetectionDataChanged(double angle, double distance, boolean isdetected) {
        try {
            webView.evaluateJavascript("javascript: " +"onDetectionDataChanged(\"" + angle + "," + distance + "," + isdetected + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error " +  e);
        }
    }

    public static void onRobotLifted(boolean status, String reason) {
        try {
            webView.evaluateJavascript("javascript: " +"onRobotLifted(\"" + status + "," + reason + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error" +  e);
        }
    }

    public static void onWakeupWord(String word, int deg) {

        try {
            webView.evaluateJavascript("javascript: " +"onWakeupWord(\"" + word + "," + deg + "\")",null);
        } catch (NullPointerException e) {
            Log.e(ROBOT_TAG, "Listener error" +  e);
        }
    }
}