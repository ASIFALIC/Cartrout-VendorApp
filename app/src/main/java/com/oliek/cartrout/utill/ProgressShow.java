package com.oliek.cartrout.utill;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;

import com.oliek.cartrout.R;


public class ProgressShow {

    ProgressDialog pDialog;

    public ProgressDialog progressDialog(Context context)
    {
        ProgressDialog dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressdialog);

        // dialog.setMessage(Message);
        return dialog;
    }






    }
