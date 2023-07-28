/**
 * Cordova Plugin to use SDK of P8 POS Printer
 * @author: sistepar.com
 */

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
    POIPrinterManager printerManager;
    

    public KozenP8Printer() {
    }


    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        final CordovaInterface cordova = this.cordova;
        final Context context = cordova.getActivity().getBaseContext();

        if (action.equals("open")) {
            open(context);
            return true;

        } else if (action.equals("status")) {
            callbackContext.success(getStatus());
            return true;

        } else if (action.equals("addPrintLine")) {
            String text = args.getString(0);
            int align = Integer.parseInt(args.getString(1));
            addPrintLine(text, align);
            return true;

        } else if (action.equals("beginPrint")) {
            beginPrint();
            return true;

        } else if (action.equals("printTest")) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    printerManager = new POIPrinterManager(context);
                    printerManager.open();

                    PrintLine p1 = new TextPrintLine("HOLIIII", PrintLine.CENTER);
                    printerManager.addPrintLine(p1);
                    printerManager.addPrintLine(new TextPrintLine(""));
                    printerManager.addPrintLine(new TextPrintLine(""));
                    printerManager.addPrintLine(new TextPrintLine(""));
                    //printerManager.setLineSpace(5);

                    POIPrinterManager.IPrinterListener listener = new POIPrinterManager.IPrinterListener() {
                        @Override
                        public void onStart() {
                            Log.d(TAG, "printer inicia");
                        }
                        @Override
                        public void onFinish() {
                            //printerManager.cleanCache();
                            printerManager.close();
                        }
                        @Override
                        public void onError(int cod, String msj) {
                            Log.e(TAG, "cod: " + cod + "msj: " + msj);
                            printerManager.close();
                        }
                    };

                    if(getStatus() == 4){
                        printerManager.close();
                        return true;
                    }

                    printerManager.beginPrint(listener);
                }
            });

            return true;
        }

        return false;

    }


    void open(Context context) {
        printerManager = new POIPrinterManager(context);
        printerManager.open();
    }


    void getStatus() {
        return printerManager.getPrinterState();
    }


    void addPrintLine(String text, int align) {
        PrintLine pl = new TextPrintLine(text, align);
        printerManager.addPrintLine(pl);
    }


    void beginPrint() {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {

                POIPrinterManager.IPrinterListener listener = new POIPrinterManager.IPrinterListener() {
                    @Override
                    public void onStart() {
                        Log.d(TAG, "printer inicia");
                    }
                    @Override
                    public void onFinish() {
                        //printerManager.cleanCache();
                        printerManager.close();
                    }
                    @Override
                    public void onError(int cod, String msj) {
                        Log.e(TAG, "cod: " + cod + "msj: " + msj);
                        printerManager.close();
                    }
                };

                if (getStatus() == 4) {
                    printerManager.close();
                    return true;
                }

                printerManager.beginPrint(listener);
            }
        });
    }
}