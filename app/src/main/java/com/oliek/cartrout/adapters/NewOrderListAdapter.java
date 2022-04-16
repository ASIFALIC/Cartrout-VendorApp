package com.oliek.cartrout.adapters;

import android.content.Context;
import android.util.Log;
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
import com.oliek.cartrout.model.OrderModel;
import com.oliek.cartrout.model.PendingOrderModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class NewOrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecycleViewItemCallBack mRecycleViewItemCallBack;
    private ArrayList<OrderModel> list;
    private OnItemClickListener mItemClickListener;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat myDateFormat;
    int collu,collu1,collu2;
    private Date d;
    private String date;

    public NewOrderListAdapter(Context mContext, ArrayList<OrderModel> mList, RecycleViewItemCallBack mRecycleViewItemCallBack) {
        this.mContext = mContext;
        this.mRecycleViewItemCallBack = mRecycleViewItemCallBack;
        this.list = mList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_orderno,txt_time,txt_order_status,tv_new;
        private LinearLayout llItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            llItem= (LinearLayout) itemView.findViewById(R.id.llItem);
            tv_new= (TextView) itemView.findViewById(R.id.tv_new);
            txt_orderno = (TextView) itemView.findViewById(R.id.txt_orderno);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            txt_order_status = (TextView) itemView.findViewById(R.id.txt_order_status);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_new_order_layout, parent, false);


        return new MyViewHolder(itemView);
    }
    private String getDate(String ourDate)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy HH:mm"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            ourDate = dateFormatter.format(value);

            //Log.d("ourDate", ourDate);
        }
        catch (Exception e)
        {
            ourDate = "00-00-0000 00:00";
        }
        return ourDate;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        OrderModel model =list.get(position);
        String time = model.getTime();

        viewHolder.txt_orderno.setText(model.getInvoiceno());
            if(model.getOpen_status()==0){
                viewHolder.tv_new.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tv_new.setVisibility(View.GONE);

            }
        viewHolder.txt_time.setText(model.getTime()+" "+model.getDiff());
        if(model.getStatus()==0){
            viewHolder.txt_order_status.setText(GlobalConstants.KEY_0);
            viewHolder.txt_order_status.setTextColor(mContext.getResources().getColor(R.color.red100));

        }
        else if(model.getStatus()==1){
            viewHolder.txt_order_status.setText(GlobalConstants.KEY_1);
            viewHolder.txt_order_status.setTextColor(mContext.getResources().getColor(R.color.confrimed));


        }
        else if(model.getStatus()==4){
            viewHolder.txt_order_status.setTextColor(mContext.getResources().getColor(R.color.redytopic));
            if(model.getDelivery_type()==0){
                viewHolder.txt_order_status.setText(GlobalConstants.KEY_4T);

            }else if(model.getDelivery_type()==1){
                viewHolder.txt_order_status.setText(GlobalConstants.KEY_4H);

            }
        }

        viewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecycleViewItemCallBack.onItemClick( model,GlobalConstants.VIEW);
            }
        });

    }
    public static String Convert24to12(String time)
    {
        String convertedTime ="";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = parseFormat.parse(time);
            convertedTime=displayFormat.format(date);
            System.out.println("convertedTime : "+convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
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