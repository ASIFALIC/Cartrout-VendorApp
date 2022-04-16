package com.oliek.cartrout.dialogue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.CategoryModel;

public class DialogCategoryEdit extends DialogFragment {
    public static RecycleViewItemCallBack recycleViewItemCallBack;
    public static CategoryModel categoryModel;
    public static DialogCategoryEdit newInstance(RecycleViewItemCallBack recycleViewItemCallBack, CategoryModel categoryModel) {
        DialogCategoryEdit fragment = new DialogCategoryEdit();
        DialogCategoryEdit.recycleViewItemCallBack = recycleViewItemCallBack;
        DialogCategoryEdit.categoryModel=categoryModel;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Perform action on click
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_category_edit, null);

        Button dialog_submit = (Button)view.findViewById(R.id.dialog_submit);
        Button dialog_cancel = (Button)view.findViewById(R.id.dialog_cancel);
        EditText name = (EditText)view.findViewById(R.id.et_name);
        TextView tatil = (TextView)view.findViewById(R.id.taitil);
        tatil.setText(categoryModel.getName()+" Edit");
        name.setText(categoryModel.getName());

        EditText order = (EditText)view.findViewById(R.id.et_order);
        order.setText(categoryModel.getOrder()+"");

        builder.setView(view);

        final AlertDialog dialog = builder.create();


        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViewItemCallBack.onItemClick("Closs","Closs");
            }
        });
        dialog_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                categoryModel.setName(name.getText().toString());
                categoryModel.setOrder(Integer.parseInt(order.getText().toString()));
                recycleViewItemCallBack.onItemClick(categoryModel,GlobalConstants.DATA);
            }
        });

        return dialog;
    }

    public void showMessage(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }

}
