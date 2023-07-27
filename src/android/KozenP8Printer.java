package com.sistepar.cordova.kozenp8;

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

public class KozenP8Printer extends CordovaPlugin {

    private static final String TAG = "KozenP8Printer";
    
    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

        Context context = this.cordova.getActivity().getApplicationContext();
        final POIPrinterManager printerManager = new POIPrinterManager(context);

        if ("state".equals(action)) {

            printerManager.open();

            PrintLine p1 = new TextPrintLine("HOLIIII", PrintLine.CENTER);
            printerManager.addPrintLine(p1);

            POIPrinterManager.IPrinterListener listener = new POIPrinterManager.IPrinterListener() {
                @Override
                public void onStart() {}

                @Override
                public void onFinish() {
                    //printerManager.cleanCache();
                    printerManager.close();
                }

                @Override
                public void onError(int code, String msg) {
                    Log.e("POIPrinterManager", "code: " + code + "msg: " + msg);
                    printerManager.close();
                }
            };

            printerManager.beginPrint(listener);

            callbackContext.success("ok");
            return true;

        }

        return false;

    }
}