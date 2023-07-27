package com.sistepar.cordova.kozenp8;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
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
    private Context context;

    public KozenP8Printer() {
    }


    /*@Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super(cordova, webView);
        this.context = cordova.getActivity().getBaseContext(); //webView.getContext()
    }*/


    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        final CordovaInterface cordova = this.cordova;
        this.context = cordova.getActivity().getBaseContext();

        if (action.equals("status")) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    final POIPrinterManager printerManager = new POIPrinterManager(cordova.getActivity().getBaseContext());

                    printerManager.open();

                    PrintLine p1 = new TextPrintLine("HOLIIII", PrintLine.CENTER);
                    printerManager.addPrintLine(p1);
                    printerManager.setLineSpace(5);

                    POIPrinterManager.IPrinterListener listener = new POIPrinterManager.IPrinterListener() {
                        @Override
                        public void onStart() {
                            Log.d(TAG, "printer inicia")
                        }
                        @Override
                        public void onFinish() {
                            printerManager.cleanCache();
                            printerManager.close();
                        }
                        @Override
                        public void onError(int cod, String msj) {
                            Log.e(TAG, "cod: " + cod + "msj: " + msj);
                            printerManager.close();
                        }
                    };

                    printerManager.beginPrint(listener);

                    callbackContext.success("ok");
                }
            });


            return true;

        }

        return false;

    }
}