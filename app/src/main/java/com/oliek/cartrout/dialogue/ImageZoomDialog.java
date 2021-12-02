package com.oliek.cartrout.dialogue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.OrderImage;
import com.oliek.cartrout.utill.TouchImageView;


public class ImageZoomDialog extends DialogFragment {
    public static OrderImage model;
    public static RecycleViewItemCallBack popupButtonCallback;
    public static ImageZoomDialog newInstance(OrderImage mmodel, RecycleViewItemCallBack popupButtonCallback) {

        ImageZoomDialog fragment = new ImageZoomDialog();
        ImageZoomDialog.model=mmodel;
        ImageZoomDialog.popupButtonCallback=popupButtonCallback;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Perform action on click
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.order_image_fullscreen_preview, null);

        final String decoded = Uri.decode(model.getImage());
        final TouchImageView image = (TouchImageView) view.findViewById(R.id.image_preview);
        final Button clos = (Button) view.findViewById(R.id.closebtn);
        clos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupButtonCallback.onItemClick(model,"Closs");
            }
        });
        ImageLoader.getInstance().displayImage(GlobalConstants.BASE_IMAGE_URL + model.getImage(), image);
        builder.setView(view);
        final AlertDialog dialog = builder.create();



        return dialog;
    }

    public void showMessage(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }

}
