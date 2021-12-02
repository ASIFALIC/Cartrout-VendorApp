package com.oliek.cartrout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.CategoryModel;
import com.oliek.cartrout.model.Itam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BestSellingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecycleViewItemCallBack mRecycleViewItemCallBack;
    private ArrayList<Itam> list;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat myDateFormat;
    int collu,collu1,collu2;
    private Date d;
    private String date;

    public BestSellingAdapter(Context mContext, ArrayList<Itam> mList) {
        this.mContext = mContext;
        this.list = mList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.txt_name);



        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_best_selling_item, parent, false);

        return new BestSellingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final BestSellingAdapter.MyViewHolder viewHolder = (BestSellingAdapter.MyViewHolder) holder;
        Itam model = list.get(position);
        viewHolder.txt_name.setText(model.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeData(int position) {
        list.remove(position);

    }




}