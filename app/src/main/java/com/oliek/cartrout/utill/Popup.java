package com.oliek.cartrout.utill;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.oliek.cartrout.R;


public class Popup {
    Dialog mBottomSheetDialog;
    Display display;
    DisplayMetrics outMetrics;
    Context context;
    public void singleButtonPopup(final Context context, String title, String Message, String buttonPositive){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setMessage(Message)
                .setCancelable(false)
                .setPositiveButton(buttonPositive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void locationModePopup(final Context context, String title, String Message, String buttonPositive){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setMessage(Message)
                .setCancelable(false)
                .setPositiveButton(buttonPositive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public Dialog dialog(Context context, View l1) {

        mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);

        mBottomSheetDialog.setContentView(l1);
        mBottomSheetDialog.setCancelable(false);

        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.x;
        int percentage = (width * 40) / 100;
        int percentage1 = (height * 30) / 100;
        mBottomSheetDialog.getWindow().setLayout((width - (percentage/2)), (height - (percentage1/2)));
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        //mBottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        try {
            mBottomSheetDialog.show();
        }
        catch (WindowManager.BadTokenException e) {
        }
        return mBottomSheetDialog;

    }
    public Dialog dialogDelivered(Context context, View l1) {

        mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(l1);
        mBottomSheetDialog.setCancelable(false);

        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.x;
        int percentage = (width * 50) / 100;
        int percentage1 = (height * 20) / 100;

        mBottomSheetDialog.getWindow().setLayout((width - (percentage/2)), (height - (percentage1)));
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        //mBottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        try {
            mBottomSheetDialog.show();
        }
        catch (WindowManager.BadTokenException e) {
        }
        return mBottomSheetDialog;

    }

    public Dialog dialog_customer(Context context, View l1) {

        mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);

        mBottomSheetDialog.setContentView(l1);
        mBottomSheetDialog.setCancelable(false);
        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int percentage = (width * 30) / 100;
        int percentage1 = (height * 50) / 100;
        mBottomSheetDialog.getWindow().setLayout((width - (percentage/2)), (height - (percentage1/2)));
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        try {
            mBottomSheetDialog.show();
        }
        catch (WindowManager.BadTokenException e) {
        }
        return mBottomSheetDialog;

    }

    public Dialog dialoglogForce(Context context, View l1) {

        mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(l1);
        mBottomSheetDialog.setCancelable(false);
        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int percentage = (width * 70) / 100;
        mBottomSheetDialog.getWindow().setLayout((width - (percentage / 3)), LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
        try {
            mBottomSheetDialog.show();
        }
        catch (WindowManager.BadTokenException e) {
        }
        return mBottomSheetDialog;
    }
    public Dialog dialogBonus(Context context, View l1) {

        mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(l1);
        mBottomSheetDialog.setCancelable(false);
        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int percentage = (width * 50) / 100;
        mBottomSheetDialog.getWindow().setLayout((width - (percentage / 3)), LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);

        try {
            mBottomSheetDialog.show();
        }       catch (WindowManager.BadTokenException e) {

        }
        return mBottomSheetDialog;

    }




    //logout popup


    public Dialog dialoglog(Context context, View l1) {

        mBottomSheetDialog = new Dialog(context, R.style.MaterialDialogSheet);

        mBottomSheetDialog.setContentView(l1);
        mBottomSheetDialog.setCancelable(false);

        display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int percentage = (width * 30) / 100;




        mBottomSheetDialog.getWindow().setLayout((width - (percentage / 2)), LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);


        try {
            mBottomSheetDialog.show();
        }       catch (WindowManager.BadTokenException e) {

        }
        return mBottomSheetDialog;

    }



}
