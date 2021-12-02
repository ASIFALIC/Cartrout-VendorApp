package com.oliek.cartrout.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.activity.ItemCatAcivity;
import com.oliek.cartrout.activity.ItemManagementAcivity;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.CategoryModel;
import com.oliek.cartrout.model.CreditModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecycleViewItemCallBack mRecycleViewItemCallBack;
    private ArrayList<CategoryModel> list;
    private CategoryListAdapter.OnItemClickListener mItemClickListener;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat myDateFormat;
    int collu,collu1,collu2;
    private Date d;
    private String date;

    public CategoryListAdapter(Context mContext, ArrayList<CategoryModel> mList, RecycleViewItemCallBack mRecycleViewItemCallBack) {
        this.mContext = mContext;
        this.mRecycleViewItemCallBack = mRecycleViewItemCallBack;
        this.list = mList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        RatingBar ratingbar;
        CardView crd_main;
        Switch sw_status;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.txt_name);

            crd_main=itemView.findViewById(R.id.crd_main);
            sw_status=itemView.findViewById(R.id.sw_status);




        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_itemcat_item, parent, false);

        return new CategoryListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CategoryListAdapter.MyViewHolder viewHolder = (CategoryListAdapter.MyViewHolder) holder;
        CategoryModel model = list.get(position);


        viewHolder.txt_name.setText(model.getName());
        viewHolder.sw_status.setOnCheckedChangeListener(null);
        if(model.getStatus()==0){
            viewHolder.sw_status.setChecked(false);
        }else {
            viewHolder.sw_status.setChecked(true);

        }
        viewHolder.crd_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecycleViewItemCallBack.onItemClick( list.get(position), GlobalConstants.VIEW);


            }
        });
        viewHolder.sw_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {


                    mRecycleViewItemCallBack.onItemClick( list.get(position), GlobalConstants.KEY_ACTVE);

                } else {

                    mRecycleViewItemCallBack.onItemClick( list.get(position), GlobalConstants.KEY_INACTVE);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeData(int position) {
        list.remove(position);
    }

    // for both short and long click
    public void SetOnItemClickListener(final CategoryListAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

}