package com.sistepar.cordova.kozenp8.printer;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class KozenP8_Printer extends CordovaPlugin {

    private static final String LOG_TAG = "KozenP8_Printer";
    
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals("state")) {

            callbackContext.success("ok");

            return true;
        } else {
            return false;
        }

        return true;
    }
}