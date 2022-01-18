package com.oliek.cartrout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.CartModel;
import com.oliek.cartrout.model.CategoryModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CartItamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecycleViewItemCallBack mRecycleViewItemCallBack;
    private ArrayList<CartModel> list;
    private CartItamAdapter.OnItemClickListener mItemClickListener;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat myDateFormat;
    int collu,collu1,collu2;
    private Date d;
    private String date;

    public CartItamAdapter(Context mContext, ArrayList<CartModel> mList , RecycleViewItemCallBack recycleViewItemCallBack ) {
        this.mContext = mContext;
        this.list = mList;
        this.mRecycleViewItemCallBack=recycleViewItemCallBack;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_menu,txt_rate,txt_qty,txt_total,txt_pref,txt_portion,txt_cat;
ImageButton bt_edit,bt_delete;
        public MyViewHolder(View itemView) {
            super(itemView);
            txt_menu=itemView.findViewById(R.id.txt_menu);
            txt_qty=itemView.findViewById(R.id.txt_qty);
            txt_total=itemView.findViewById(R.id.txt_total);
            bt_edit=itemView.findViewById(R.id.bt_edit);
            bt_delete=itemView.findViewById(R.id.bt_delete);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cat_itamlist_item, parent, false);

        return new CartItamAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final CartItamAdapter.MyViewHolder viewHolder = (CartItamAdapter.MyViewHolder) holder;
        CartModel model = list.get(position);


        // holder.txt_cat.setText(String.valueOf(list.get(position).getItem().getCategory()));
        viewHolder.txt_menu.setText(list.get(position).getProduct_name());
        viewHolder.txt_qty.setText(list.get(position).getPrice()+"*"+list.get(position).getQuantity()+"");
        viewHolder.txt_total.setText(list.get(position).getAmount()+"");
        viewHolder.bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mRecycleViewItemCallBack.onItemClick( list.get(position),GlobalConstants.EDIT);

            }
        });
        if(list.size()==1) {
            viewHolder.bt_delete.setVisibility(View.GONE);
        }
            viewHolder.bt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mRecycleViewItemCallBack.onItemClick(list.get(position), GlobalConstants.DELETE);

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
    public void SetOnItemClickListener(final CartItamAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

}