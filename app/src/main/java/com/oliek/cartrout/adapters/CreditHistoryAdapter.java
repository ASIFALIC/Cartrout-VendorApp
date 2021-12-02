package com.oliek.cartrout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.oliek.cartrout.R;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.CreditModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreditHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecycleViewItemCallBack mRecycleViewItemCallBack;
    private ArrayList<CreditModel> list;
    private com.oliek.cartrout.adapters.CreditHistoryAdapter.OnItemClickListener mItemClickListener;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat myDateFormat;
    int collu,collu1,collu2;
    private Date d;
    private String date;

    public CreditHistoryAdapter(Context mContext, ArrayList<CreditModel> mList, RecycleViewItemCallBack mRecycleViewItemCallBack) {
        this.mContext = mContext;
        this.mRecycleViewItemCallBack = mRecycleViewItemCallBack;
        this.list = mList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_sl,tv_rimark,tv_cradit,tv_debit;
        private TextView status,status1,status2;
        private ImageView typeiimge,typeiimge1,typeiimge2;
        private LinearLayout llItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            llItem= (LinearLayout) itemView.findViewById(R.id.ll_Item);
            tv_sl = (TextView) itemView.findViewById(R.id.tv_sl);
            tv_rimark = (TextView) itemView.findViewById(R.id.tv_rimark);
            tv_cradit = (TextView) itemView.findViewById(R.id.tv_cradit);
            tv_debit = (TextView) itemView.findViewById(R.id.tv_debit);




        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_ceadit_history_layout, parent, false);


        return new com.oliek.cartrout.adapters.CreditHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final com.oliek.cartrout.adapters.CreditHistoryAdapter.MyViewHolder viewHolder = (com.oliek.cartrout.adapters.CreditHistoryAdapter.MyViewHolder) holder;
        CreditModel model = list.get(position);



        viewHolder.tv_sl.setText(position+1+"");
        viewHolder.tv_rimark.setText(model.getRemark() + model.getOrder()+"");
        if(model.getType().equals("credit")){
            viewHolder.tv_cradit.setText(model.getAmount()+"");
        }else {
            viewHolder.tv_debit.setText(model.getAmount()+"");

        }






    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeData(int position) {
        list.remove(position);
    }

    // for both short and long click
    public void SetOnItemClickListener(final com.oliek.cartrout.adapters.CreditHistoryAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

}