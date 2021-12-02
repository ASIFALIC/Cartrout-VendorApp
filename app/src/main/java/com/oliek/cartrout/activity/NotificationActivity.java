package com.oliek.cartrout.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.oliek.cartrout.R;
import com.oliek.cartrout.model.NotificationModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.preference.PreferenceService;

import java.util.ArrayList;
import java.util.List;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class NotificationActivity extends AppCompatActivity {

    SwipeRefreshLayout mySwipeRefreshLayout,refresh;
    private ApiInterface apiService;
    private PreferenceService sh;

    RecyclerView recycle_not;
    NotificationAdapter adapter;
    ArrayList<NotificationModel> list=new ArrayList<>();

    View no_connection;
    Button btn_tryagain;
    LinearLayout empty_layout;
    int staffId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("NOTIFICATIONS");


        staffId=11;


        getNotifications();
        recycle_not=findViewById(R.id.recycle_not);
        mySwipeRefreshLayout=findViewById(R.id.swiperefresh);
        no_connection=findViewById(R.id.no_connection);
        btn_tryagain=findViewById(R.id.btn_tryagain);
        empty_layout=findViewById(R.id.no_notifications);

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getNotifications();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        btn_tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNotifications();


            }
        });



    }

    private void  getNotifications() {
//
//        list.clear();
//        apiService = ApiNetwork.getClient().create(ApiInterface.class);
//        Call<NotificationsMain> call = apiService.getnotifications(111);
//        call.enqueue(new Callback<NotificationsMain>() {
//            @Override
//            public void onResponse(Call<NotificationsMain> call, Response<NotificationsMain> response) {
//                    if (response.body()!=null){
//                        NotificationsMain obj = response.body();
//                        String msg=obj.getMsg();
//
//                        if(msg.equals("success")){
//                            list=obj.getData();
//                            adapter = new NotificationAdapter(getApplicationContext(), list);
//                            recycle_not.setHasFixedSize(true);
//                            RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//                            recycle_not.setLayoutManager(lm);
//                            recycle_not.setItemAnimator(new DefaultItemAnimator());
//                            recycle_not.setAdapter(adapter);
////                            pd.dismiss();
//                            empty_layout.setVisibility(View.GONE);
//                            mySwipeRefreshLayout.setVisibility(View.VISIBLE);
//                        }
//                        else {
//                            list.clear();
////                            pd.dismiss();
//
//                            recycle_not.setAdapter(null);
//                            mySwipeRefreshLayout.setVisibility(View.GONE);
//                            empty_layout.setVisibility(View.VISIBLE);
//
//
//                        }
//                    }else {
////                        pd.dismiss();
//                        Toast.makeText(getApplicationContext(), GlobalConstants.INTERNAL_SERVER_ERROR, Toast.LENGTH_LONG).show();
//                    }
//                    no_connection.setVisibility(View.GONE);
//            }
//
//
//            @Override
//            public void onFailure(Call<NotificationsMain> call, Throwable t) {
////                pd.dismiss();
//                empty_layout.setVisibility(View.GONE);
//
//                mySwipeRefreshLayout.setVisibility(View.GONE);
//                no_connection.setVisibility(View.VISIBLE);
//            }
//        });
    }



    public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

        private Context mContext;
        private List<NotificationModel> list_addres;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txt_name,txt_time;
            public TextView txt_notfication;



            public MyViewHolder(View view) {
                super(view);
                txt_name =view.findViewById(R.id.txt_name);
                txt_time =view.findViewById(R.id.txt_time_duretion);
                txt_notfication =view.findViewById(R.id.txt_notfication);




            }
        }

        public NotificationAdapter(Context mContext, List<NotificationModel> histList) {
            this.mContext = mContext;
            this.list_addres = histList;

        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_notification, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.txt_name.setText(list.get(position).getTitle());
            holder.txt_notfication.setText(list.get(position).getMessage());
            holder.txt_time.setText(list.get(position).getEntryDate());

            holder.txt_notfication.setLineSpacing(0,1.3f);
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                holder.txt_notfication.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }






        }





        @Override
        public int getItemCount() {

            return list_addres.size();

        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // todo: goto back activity from here

                Intent i=new Intent(getApplicationContext(),NotificationActivity.class);
                i.putExtra("to","");
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
