package com.oliek.cartrout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.ProductModel;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProductPopopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecycleViewItemCallBack mRecycleViewItemCallBack;
    private ArrayList<ProductModel> list;
    private ProductPopopAdapter.OnItemClickListener mItemClickListener;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat myDateFormat;
    int collu,collu1,collu2;
    private Date d;
    LinearLayout itam;
    private String date;

    public ProductPopopAdapter(Context mContext, ArrayList<ProductModel> mList, RecycleViewItemCallBack mRecycleViewItemCallBack) {
        this.mContext = mContext;
        this.mRecycleViewItemCallBack = mRecycleViewItemCallBack;
        this.list = mList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_sl,tv_name,tv_amount;
        private LinearLayout itam;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            itam= (LinearLayout) itemView.findViewById(R.id.itam);



        }
    }
    public void updateList(ArrayList<ProductModel> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product_popup_list_layout, parent, false);


        return new ProductPopopAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ProductPopopAdapter.MyViewHolder viewHolder = (ProductPopopAdapter.MyViewHolder) holder;
        ProductModel model = list.get(position);
        viewHolder.tv_name.setText(model.getName()+"");
        viewHolder.itam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleViewItemCallBack.onItemClick(model, GlobalConstants.DATA);
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
    public void SetOnItemClickListener(final ProductPopopAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

}