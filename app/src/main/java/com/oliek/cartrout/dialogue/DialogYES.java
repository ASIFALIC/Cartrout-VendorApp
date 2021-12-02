package com.oliek.cartrout.dialogue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.oliek.cartrout.R;
import com.oliek.cartrout.callback.PopupButtonCallback;

public class DialogYES extends DialogFragment {
    public static String text;
    public static PopupButtonCallback popupButtonCallback;
    public static DialogYES newInstance(PopupButtonCallback popupButtonCallback, String text) {

        DialogYES fragment = new DialogYES();
        DialogYES.popupButtonCallback = popupButtonCallback;
        DialogYES.text=text;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Perform action on click
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_yes, null);

        TextView btnAccept = (TextView)view.findViewById(R.id.btn_accept);
        TextView btnDecline = (TextView)view.findViewById(R.id.btn_decline);
        TextView taitil_dil = (TextView)view.findViewById(R.id.taitil_dil);
        taitil_dil.setText(text);

        builder.setView(view);
        final AlertDialog dialog = builder.create();


        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupButtonCallback.onNegativeButtonClicked(text);
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupButtonCallback.onPositiveButtonClicked(text);
            }
        });

        return dialog;
    }

    public void showMessage(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }

}
