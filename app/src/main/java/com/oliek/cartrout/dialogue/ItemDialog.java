package com.oliek.cartrout.dialogue;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.adapters.ProductPopopAdapter;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.ProductModel;

import java.util.ArrayList;


public class ItemDialog extends DialogFragment implements RecycleViewItemCallBack {
    public static ArrayList<ProductModel> mlist;
    public static RecycleViewItemCallBack recycleViewItemCallBack;
    private static Context mcontext;
    ProductPopopAdapter listAdapter;
    ProgressBar progressBar;
    SearchView serch;
    RecyclerView recyclerview;
    public static ItemDialog newInstance(Context context, RecycleViewItemCallBack recycleViewItemCallBack, ArrayList<ProductModel>  list) {

        ItemDialog fragment = new ItemDialog();
        ItemDialog.recycleViewItemCallBack = recycleViewItemCallBack;
        ItemDialog.mlist=list;
        ItemDialog.mcontext=context;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Perform action on click
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_product_list, null);
        RecyclerView recyclerview=(RecyclerView)view.findViewById(R.id.recyclerview);


        LinearLayoutManager layoutManagerNewsTrending = new LinearLayoutManager(mcontext);
        layoutManagerNewsTrending.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManagerNewsTrending);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerview.getContext(),
                layoutManagerNewsTrending.getOrientation());
        recyclerview.addItemDecoration(dividerItemDecoration);
        ProductPopopAdapter listAdapter = new ProductPopopAdapter(mcontext, mlist,  this);
        recyclerview.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        SearchView serch=(SearchView)view.findViewById(R.id.serch);
        serch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<ProductModel>  temp = new ArrayList();
                for(ProductModel d: mlist){
                    //or use .equal(text) with you want equal match
                    //use .toLowerCase() for better matches
                    if(d.getName().toLowerCase().contains(newText.toLowerCase())){
                        temp.add(d);
                    }
                }
                //update recyclerview
                listAdapter.updateList(temp);



                return false;
            }
        });
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        return dialog;
    }


    @Override
    public void onItemClick(Object data, Object sender) {
        if (sender.equals(GlobalConstants.DATA)) {

            ProductModel model = (ProductModel) data;
            recycleViewItemCallBack.onItemClick(model,GlobalConstants.DATA);
        }
    }
    void filter(String text){
        ArrayList<ProductModel>  temp = new ArrayList();
        for(ProductModel d: mlist){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        listAdapter.updateList(temp);
    }
}
