package com.oliek.cartrout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.PendingOrderModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PendingOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecycleViewItemCallBack mRecycleViewItemCallBack;
    private ArrayList<PendingOrderModel> list;
    private OnItemClickListener mItemClickListener;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat myDateFormat;
    int collu,collu1,collu2;
    private Date d;
    private String date;

    public PendingOrderAdapter(Context mContext, ArrayList<PendingOrderModel> mList, RecycleViewItemCallBack mRecycleViewItemCallBack) {
        this.mContext = mContext;
        this.mRecycleViewItemCallBack = mRecycleViewItemCallBack;
        this.list = mList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id,date,name,addre,mobil,typname;
        private TextView status,status1,status2;
        private ImageView typeiimge,typeiimge1,typeiimge2;
        private LinearLayout llItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            llItem= (LinearLayout) itemView.findViewById(R.id.ll_Item);
            id = (TextView) itemView.findViewById(R.id.tv_id);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            addre = (TextView) itemView.findViewById(R.id.tv_address);
            status = (TextView) itemView.findViewById(R.id.tv_status);
            status1 = (TextView) itemView.findViewById(R.id.tv_status1);
            status2= (TextView) itemView.findViewById(R.id.tv_status2);

            typname = (TextView) itemView.findViewById(R.id.tv_type);
            typeiimge=(ImageView) itemView.findViewById(R.id.iv_typeimage);
            typeiimge1=(ImageView) itemView.findViewById(R.id.iv_typeimage1);
            typeiimge2=(ImageView) itemView.findViewById(R.id.iv_typeimage2);



        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_layout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        PendingOrderModel model = list.get(position);


//
        viewHolder.name.setText(model.getCustomer_name());
        viewHolder.addre.setText(model.getAddress());
        viewHolder.date.setText(model.getDate());
        viewHolder.id.setText("ID : "+model.getId());

        if(model.getOrder_status().equals("0")){
            viewHolder.status.setVisibility(View.VISIBLE);
            viewHolder.status1.setVisibility(View.GONE);
            viewHolder.status2.setVisibility(View.GONE);

        }
        if(model.getOrder_status().equals("1")){
            viewHolder.status.setVisibility(View.VISIBLE);
            viewHolder.status1.setVisibility(View.GONE);
            viewHolder.status2.setVisibility(View.GONE);

        }
        else if(model.getOrder_status().equals("2")){
            viewHolder.status1.setVisibility(View.VISIBLE);
            viewHolder.status.setVisibility(View.GONE);
            viewHolder.status2.setVisibility(View.GONE);
        }
        else if(model.getOrder_status().equals("3")){
            viewHolder.status2.setVisibility(View.VISIBLE);
            viewHolder.status1.setVisibility(View.GONE);
            viewHolder.status.setVisibility(View.GONE);

        }


        if(model.getType().equals("0")){
            viewHolder.typname.setText("Exprass");
            viewHolder.typeiimge.setVisibility(View.VISIBLE);
            viewHolder.typeiimge1.setVisibility(View.GONE);
            viewHolder.typeiimge2.setVisibility(View.GONE);
        }
        else if(model.getType().equals("1")){
            viewHolder.typname.setText("Normal");
            viewHolder.typeiimge.setVisibility(View.GONE);
            viewHolder.typeiimge1.setVisibility(View.VISIBLE);
            viewHolder.typeiimge2.setVisibility(View.GONE);

        }
        else if(model.getType().equals("2")){
            viewHolder.typname.setText("Schedule : "+model.getScheduled_date());
            viewHolder.typeiimge.setVisibility(View.GONE);
            viewHolder.typeiimge1.setVisibility(View.GONE);
            viewHolder.typeiimge2.setVisibility(View.VISIBLE);

        }

        viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleViewItemCallBack.onItemClick( list.get(position),GlobalConstants.VIEW);
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
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

}