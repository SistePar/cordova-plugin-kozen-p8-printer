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
//import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.net.Uri;

import com.pos.sdk.printer.POIPrinterManager;
import com.pos.sdk.printer.models.BitmapPrintLine;
import com.pos.sdk.printer.models.PrintLine;
import com.pos.sdk.printer.models.TextPrintLine;

//import java.util.ArrayList;
//import java.util.List;

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

        } else if (action.equals("close")) {
            close();
            return true;

        } else if (action.equals("status")) {
            callbackContext.success(getStatus());
            return true;

        } else if (action.equals("addPrintLine")) {
            String text = args.getString(0);
            int align = Integer.parseInt(args.getString(1));

            addPrintLine(text, align);
            return true;

        } else if (action.equals("addPrintLineSizeBold")) {
            String text = args.getString(0);
            int align = Integer.parseInt(args.getString(1));
            int size = Integer.parseInt(args.getString(2));
            boolean bold = Boolean.parseBoolean(args.getString(3));

            addPrintLineSizeBold(text, align, size, bold);
            return true;
        
        } else if (action.equals("addPrintBase64")) {
            String encodedImage = args.getString(0);
            int align = Integer.parseInt(args.getString(1));

            addPrintBase64(encodedImage, align);
            return true;

        } else if (action.equals("beginPrint")) {
            beginPrint();
            return true;

        } else if (action.equals("printTest")) {
            cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    printerManager = new POIPrinterManager(context);
                    printerManager.open();

                    PrintLine p1 = new TextPrintLine("HOLA SISTEPAR", PrintLine.CENTER);
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
                            printerManager.cleanCache();
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
                        return;
                    }

                    printerManager.beginPrint(listener);
                }
            });

            return true;
        }

        return false;

    }


    void open(Context context) {
        //cordova.getThreadPool().execute(new Runnable() {
        //    public void run() {
                printerManager = new POIPrinterManager(context);
                printerManager.open();
                printerManager.setPrintGray(1000);
        //    }
        //});
    }


    void close() {
        if (printerManager != null) {
            printerManager.close();
        }
    }


    int getStatus() {
        return printerManager.getPrinterState();
    }


    void addPrintLine(String text, int align) {
        PrintLine pl = new TextPrintLine(text, align);
        printerManager.addPrintLine(pl);
    }


    void addPrintLineSizeBold(String text, int align, int size, boolean bold) {
        PrintLine pl = new TextPrintLine(text, align, size, bold);
        printerManager.addPrintLine(pl);
    }


    void addPrintBase64(final String encodedString, int align) {
        //align PrintLine.CENTER
        final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
        final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        printerManager.addPrintLine(new BitmapPrintLine(decodedBitmap, align));
    }


    void beginPrint() {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {

                POIPrinterManager.IPrinterListener listener = new POIPrinterManager.IPrinterListener() {
                    @Override
                    public void onStart() {}
                    @Override
                    public void onFinish() {
                        //beep(1);
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
                    return;
                }

                printerManager.beginPrint(listener);
            }
        });
    }



    /*void beep(final long count) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone notification = RingtoneManager.getRingtone(cordova.getActivity().getBaseContext(), ringtone);

                // If phone is not set to silent mode
                if (notification != null) {
                    for (long i = 0; i < count; ++i) {
                        notification.play();
                        long timeout = 2000;
                        while (notification.isPlaying() && (timeout > 0)) {
                            timeout = timeout - 100;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
            }
        });
    }*/

    /*private static List<TextPrintLine> printList(String leftStr, String centerStr, String rightStr, int size, boolean bold){
        TextPrintLine textPrintLine1 = new TextPrintLine(leftStr, PrintLine.LEFT, size, bold);
        TextPrintLine textPrintLine2 = new TextPrintLine(centerStr,PrintLine.CENTER, size, bold);
        TextPrintLine textPrintLine3 = new TextPrintLine(rightStr, PrintLine.RIGHT, size, bold);
        List<TextPrintLine> list = new ArrayList<>();
        list.add(textPrintLine1);
        list.add(textPrintLine2);
        list.add(textPrintLine3);
        return list;
    }*/
}