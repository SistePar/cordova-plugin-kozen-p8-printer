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

import com.pos.sdk.printer.POIPrinterManager;
import com.pos.sdk.printer.models.BitmapPrintLine;
import com.pos.sdk.printer.models.PrintLine;
import com.pos.sdk.printer.models.TextPrintLine;

import java.util.ArrayList;
import java.util.List;

public class KozenP8_Printer extends CordovaPlugin {

    private static final String TAG = "KozenP8_Printer";
    
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

        Context context = this.cordova.getActivity().getApplicationContext();
        final POIPrinterManager printerManager = new POIPrinterManager(context);

        if (action.equals("state")) {

            callbackContext.success("ok");
            return true;

        } else {
            return false;
        }

    }
}