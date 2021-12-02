package com.oliek.cartrout.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DialogFactory {

    private static final String DEFULT_POSITIVE_BTN_TEXT = "OK";

    private static Dialog sDialog = null;
    private static ProgressDialog sProgressDialog = null;

    public static ProgressDialog showDialog(AppCompatActivity appCompatActivity, String title, String
            message) {
        if(appCompatActivity.isFinishing()){
            return null;
        }

        if (null != sProgressDialog && sProgressDialog.isShowing()) {
            sProgressDialog.dismiss();
            sProgressDialog = null;
        }
        sProgressDialog = new ProgressDialog(appCompatActivity);
//        sProgressDialog.setTitle(title);
        sProgressDialog.setMessage(message);
        sProgressDialog.setCancelable(false);
        sProgressDialog.show();
        return sProgressDialog;
    }

    public static ProgressDialog showDialog(AppCompatActivity appCompatActivity, int title, int
            message) {
        return showDialog(appCompatActivity, appCompatActivity.getString(title), appCompatActivity
                .getString(message));

    }

    public static void showDialog(AppCompatActivity appCompatActivity, String title, String message,
                                  String positiveBtn, DialogInterface.OnClickListener positiveBtnListener,
                                  String negativeBtn, DialogInterface.OnClickListener negativeBtnListener,
                                  String neutralBtn, DialogInterface.OnClickListener neutralBtnListener,
                                  boolean isDismissable) {

        if(appCompatActivity.isFinishing()){
            return;
        }

        if (null != sDialog && sDialog.isShowing()) {
            sDialog.dismiss();
            sDialog = null;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(appCompatActivity);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(isDismissable);

        if (null != positiveBtn) {
            builder.setPositiveButton(positiveBtn, positiveBtnListener);
        }

        if (null != negativeBtn) {
            builder.setNegativeButton(negativeBtn, negativeBtnListener);
        }

        if (null != neutralBtn) {
            builder.setNeutralButton(neutralBtn, neutralBtnListener);
        }

        sDialog = builder.create();
        sDialog.show();
    }

    public static void showDialog(AppCompatActivity appCompatActivity, String title, String message, String btnText, DialogInterface.OnClickListener btnListener, boolean isDismissable) {
        showDialog(appCompatActivity, title, message, btnText, btnListener, null, null, null, null, isDismissable);
    }

    public static void showDialog(AppCompatActivity appCompatActivity, String title, String message, String positiveBtn, DialogInterface.OnClickListener positiveBtnListener,
                                  String negativeBtn, DialogInterface.OnClickListener negativeBtnListener, boolean isDismissable) {
        showDialog(appCompatActivity, title, message, positiveBtn, positiveBtnListener, negativeBtn, negativeBtnListener, null, null, isDismissable);
    }

    public static void showDialog(AppCompatActivity appCompatActivity, int title, int message, int btnText, DialogInterface.OnClickListener btnListener, boolean isDismissable) {
        showDialog(appCompatActivity, appCompatActivity.getString(title), appCompatActivity.getString(message), appCompatActivity.getString(btnText), btnListener, null, null, null, null, isDismissable);
    }

    public static void showDialog(AppCompatActivity appCompatActivity, int title, int message, int positiveBtn, DialogInterface.OnClickListener positiveBtnListener,
                                  int negativeBtn, DialogInterface.OnClickListener negativeBtnListener, boolean isDismissable) {
        showDialog(appCompatActivity, appCompatActivity.getString(title), appCompatActivity.getString(message), appCompatActivity.getString(positiveBtn), positiveBtnListener, appCompatActivity.getString(negativeBtn), negativeBtnListener, null, null, isDismissable);
    }

    public static void showDialog(AppCompatActivity appCompatActivity, int title, String message, int positiveBtn, DialogInterface.OnClickListener positiveBtnListener,
                                  int negativeBtn, DialogInterface.OnClickListener negativeBtnListener, boolean isDismissable) {
        showDialog(appCompatActivity, appCompatActivity.getString(title), message, appCompatActivity.getString(positiveBtn), positiveBtnListener, appCompatActivity.getString(negativeBtn), negativeBtnListener, null, null, isDismissable);
    }

    public static void showDialog(AppCompatActivity appCompatActivity, int title, int message, boolean isDismissable) {
        showDialog(appCompatActivity, appCompatActivity.getString(title), appCompatActivity.getString(message), DEFULT_POSITIVE_BTN_TEXT, null, isDismissable);
    }

    public static void showDialog(AppCompatActivity appCompatActivity, String title, String message, boolean isDismissable) {
        showDialog(appCompatActivity, title, message, DEFULT_POSITIVE_BTN_TEXT, null, isDismissable);
    }

    public static void showDialog(AppCompatActivity appCompatActivity, String title, List<String> items, DialogInterface.OnClickListener itemClickListener, boolean isDismissable) {

        if(appCompatActivity.isFinishing()){
            return;
        }

        if (null != sDialog && sDialog.isShowing()) {
            sDialog.dismiss();
            sDialog = null;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(appCompatActivity);
        builder.setTitle(title)
                .setSingleChoiceItems(items.toArray(new CharSequence[items.size()]), 0, itemClickListener)
                .setCancelable(isDismissable);
        sDialog = builder.create();
        sDialog.show();
    }

    public static void showDialog(AppCompatActivity appCompatActivity, int title, List<String> items, DialogInterface.OnClickListener itemClickListener, boolean isDismissable) {
        showDialog(appCompatActivity, appCompatActivity.getString(title), items, itemClickListener, isDismissable);
    }

    public static Dialog showDialog(AppCompatActivity appCompatActivity, int title, View customView, boolean isDismissable) {
        if (null != sDialog && sDialog.isShowing()) {
            sDialog.cancel();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(appCompatActivity);
        builder.setTitle(title);
        builder.setCancelable(isDismissable);
        sDialog = builder.create();
        sDialog.show();
        sDialog.setContentView(customView);

        return sDialog;
    }
}
