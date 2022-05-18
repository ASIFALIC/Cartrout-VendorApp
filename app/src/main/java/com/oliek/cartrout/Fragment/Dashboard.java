package com.oliek.cartrout.Fragment;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.oliek.cartrout.BuildConfig;
import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.activity.ItemCatAcivity;
import com.oliek.cartrout.activity.LoginActivity;
import com.oliek.cartrout.activity.NavigationActivity;
import com.oliek.cartrout.activity.NewOrderActivity;
import com.oliek.cartrout.activity.OrderDetailsActivity;
import com.oliek.cartrout.adapters.BestSellingAdapter;
import com.oliek.cartrout.adapters.NewOrderListAdapter;
import com.oliek.cartrout.base.BaseFragment;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.DashboardModel;
import com.oliek.cartrout.model.Itam;
import com.oliek.cartrout.model.OrderModel;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.responsemodel.DashboardResponseModel;
import com.oliek.cartrout.model.responsemodel.OrderCountResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;
import com.oliek.cartrout.utill.Popup;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.oliek.cartrout.GlobalConstants.TAG;


public class Dashboard extends BaseFragment implements View.OnClickListener , RecycleViewItemCallBack {
    protected FragmentActivity mActivity;

    TextView txt_viewall;
    LinearLayout lyt_orders,lyt_menu;
    RecyclerView rec_recentorders,rec_bestsaleitems;
    TextView txt_res_name, txt_week,txt_yesterday_count,txt_week_count,txt_address;
    RelativeLayout main_layout;
    LinearLayout no_connection;
    Button btn_tryagain;

    TextView tt_res_name,txt_today,txt_today_count,txt_month,txt_ordercount;
    LinearLayout lyt_summary;
    CardView crd_recent_orders;

    private ApiInterface apiService;
    private UserModel user;
    SwipeRefreshLayout refresh;
    BestSellingAdapter bestSellingAdapter;
    NewOrderListAdapter orderListAdapter;


    NotificationManager notificationManager;
    List<String> data = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_dashboard, container, false);
        getActivity().setTitle("DASHBOARD");
        user = PreferenceService.getInstance(getContext()).getUser();
        apiService = ApiNetwork.getClient().create(ApiInterface.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            data.add("Cartrout Notification");
            notificationManager.deleteNotificationChannel("Cartroutrest");
            notificationManager.deleteNotificationChannel("CartroutNotification");
            createNotificationGroups();
            createNotificationChannels();
        }



        no_connection = view.findViewById(R.id.no_connection);
        main_layout = view.findViewById(R.id.main_layout);
        btn_tryagain = view.findViewById(R.id.btn_tryagain);

        txt_address = view.findViewById(R.id.txt_address);
        txt_res_name = view.findViewById(R.id.txt_res_name);

        txt_viewall = view.findViewById(R.id.txt_viewall);
        lyt_orders = view.findViewById(R.id.lyt_orders);
        lyt_menu = view.findViewById(R.id.lyt_menu);

        txt_week = view.findViewById(R.id.txt_week);
        txt_today = view.findViewById(R.id.txt_todays);
        txt_month = view.findViewById(R.id.txt_month);


        txt_today_count = view.findViewById(R.id.txt_today_count);
        txt_yesterday_count = view.findViewById(R.id.txt_yesterday_count);
        txt_week_count = view.findViewById(R.id.txt_week_count);

        rec_recentorders=view.findViewById(R.id.rec_recentorders);
        LinearLayoutManager layoutManagerNewsTrending = new LinearLayoutManager(getContext());
        layoutManagerNewsTrending.setOrientation(LinearLayoutManager.VERTICAL);
        rec_recentorders.setLayoutManager(layoutManagerNewsTrending);
        rec_recentorders.setItemAnimator(new DefaultItemAnimator());
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rec_recentorders.getContext(),
//                layoutManagerNewsTrending.getOrientation());
//        rec_recentorders.addItemDecoration(dividerItemDecoration);
        rec_bestsaleitems=view.findViewById(R.id.rec_bestsaleitems);
        LinearLayoutManager layoutManagerNewsTrending2 = new LinearLayoutManager(getContext());
        layoutManagerNewsTrending.setOrientation(LinearLayoutManager.VERTICAL);
        rec_bestsaleitems.setLayoutManager(layoutManagerNewsTrending2);
        rec_bestsaleitems.setItemAnimator(new DefaultItemAnimator());
        lyt_summary=view.findViewById(R.id.lyt_summary);
        crd_recent_orders=view.findViewById(R.id.crd_recent_orders);
        txt_ordercount=view.findViewById(R.id.txt_ordercount);
        refresh=view.findViewById(R.id.refresh);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdashboard();
                getOrdercount();
                refresh.setRefreshing(false); // Disables the refresh icon*/

            }
        });

        txt_viewall.setOnClickListener(this);
        lyt_orders.setOnClickListener(this);
        lyt_menu.setOnClickListener(this);
        btn_tryagain.setOnClickListener(this);
        getdashboard();
        getOrdercount();
        return view;
    }

    private void setOrderCount(int count ) {
        if (count>0){
            if(getContext()!=null){
                Animation shake= AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                lyt_orders.startAnimation(shake);
                Animation myFadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_reverse_count);
                txt_ordercount.startAnimation(myFadeInAnimation);
            }

            txt_ordercount.setText(count+"");
            showProgressDialog(false);

            txt_ordercount.setVisibility(View.VISIBLE);

        }else {
            txt_ordercount.setVisibility(View.GONE);
        }
    }
    private void appUpdatePopop(boolean app_update) {
        LayoutInflater layoutInflater=(LayoutInflater)getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view=layoutInflater.inflate(R.layout.fore_udate_pop,null);
        Popup popup=new Popup();
        final Dialog review_popup = popup.dialoglog(getContext(), popup_view);
        review_popup.setCancelable(true);
        Button cancel_btn=popup_view.findViewById(R.id.cancel_btn);
        Button btn_positive=popup_view.findViewById(R.id.btn_positive);
        TextView head=popup_view.findViewById(R.id.head);
        TextView popup_txt=popup_view.findViewById(R.id.popup_txt);
        cancel_btn.setText("skip");
        btn_positive.setText("update");
        head.setText("App New Update");
        popup_txt.setText("update your app for new features");

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_popup.dismiss();

            }
        });
        if (app_update) {
            review_popup.setCancelable(false);

            cancel_btn.setVisibility(View.GONE);

        }else {
            cancel_btn.setVisibility(View.VISIBLE);
        }
        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://play.google.com/store/apps/details?id=com.oliek.cartrout";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);



            }
        });
        review_popup.show();

//        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
//        builder1.setTitle("App New Update");
//        builder1.setMessage("update your app for new features");
//        builder1.setCancelable(false);
//
//        builder1.setPositiveButton(
//                "update",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                        String url = "https://play.google.com/store/apps/details?id=com.oliek.cartrout";
//                        Intent i = new Intent(Intent.ACTION_VIEW);
//                        i.setData(Uri.parse(url));
//                        startActivity(i);
//
//                    }
//                });
//        if (!app_update) {
//            builder1.setNegativeButton(
//                    "No",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//        }
//
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
    }
    private void getdashboard(){
        String url = GlobalConstants.BASE_URL + "getdashboard/?token="+user.getApi_tocken()+"&entity_id="+user.getEntity().getId();
        showProgressDialog(true);
        Call<DashboardResponseModel> call = apiService.getdashboard(url);
        call.enqueue(new Callback<DashboardResponseModel>() {
            @Override
            public void onResponse(Call<DashboardResponseModel> call, Response<DashboardResponseModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        setView(response.body().getDashboard());
                    }else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        PreferenceService.getInstance(getContext()).clearAll();
                        Intent i=new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                    }


                } else {
                    Toast.makeText(getContext(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
                showProgressDialog(false);

            }

            @Override
            public void onFailure(Call<DashboardResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });


    }

    private void setView(DashboardModel body) {
        txt_res_name.setText(user.getEntity().getName());
        txt_address.setText(user.getEntity().getAddress());
        txt_week.setText(body.getWeekorders()+"");
        txt_today.setText(body.getTodayorders()+"");
        txt_month.setText(body.getMonthorders()+"");
        setBestSelling(body.getItam());
        setNewOrders(body.getOrders());
        if(body.getApp_vertion()> BuildConfig.VERSION_CODE){
            appUpdatePopop(body.isApp_update());
        }
    }

    private void setNewOrders(ArrayList<OrderModel> orders) {
        if (orders != null&&orders.size()!=0) {
            orderListAdapter = new NewOrderListAdapter(getContext(), orders, this);
            rec_recentorders.setAdapter(orderListAdapter);
            orderListAdapter.notifyDataSetChanged();
            rec_recentorders.setVisibility(View.VISIBLE);
            showProgressDialog(false);



        }
    }

    private void setBestSelling(ArrayList<Itam> itam) {
        if (itam != null&&itam.size()!=0) {
            bestSellingAdapter = new BestSellingAdapter(getContext(), itam);
            rec_bestsaleitems.setAdapter(bestSellingAdapter);
            bestSellingAdapter.notifyDataSetChanged();
            showProgressDialog(false);
        }
    }

    private void getOrdercount() {
        String url = GlobalConstants.BASE_URL + "getordercount/?token="+user.getApi_tocken()+"&entity_id="+user.getEntity().getId();
        showProgressDialog(true);
        Call<OrderCountResponseModel> call = apiService.getordercount(url);
        call.enqueue(new Callback<OrderCountResponseModel>() {
            @Override
            public void onResponse(Call<OrderCountResponseModel> call, Response<OrderCountResponseModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        setOrderCount(response.body().getCount());

                    }else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getContext(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
                showProgressDialog(false);

            }

            @Override
            public void onFailure(Call<OrderCountResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });



    }


    private void createNotificationGroups() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            List<NotificationChannelGroup> list = new ArrayList<>();
            list.add(new NotificationChannelGroup("Cartrout_Notifications", "Cartrout Notifications"));

            notificationManager.createNotificationChannelGroups(list);

        }
    }

    private void createNotificationChannels() {
        for (String s : data) {
            Uri sound = Uri.parse("android.resource://com.oliek.cartrout"+ "/" + R.raw.ring);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                NotificationChannel notificationChannel = new NotificationChannel("Cartrout_Notification" , s, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.enableVibration(true);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.YELLOW);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                notificationChannel.canShowBadge();
                notificationChannel.setSound(sound,att);
                notificationChannel.setGroup("Cartrout_Notifications");
                notificationChannel.setVibrationPattern(new long[]{500, 500, 500, 500, 500, 500, 500, 500, 500});

                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(notificationChannel);
                }
            }
        }
    }



    @Override
    public void onClick(View v) {
        Intent i;
        switch(v.getId()){
            case R.id.lyt_menu:
                i=new Intent(getActivity(), ItemCatAcivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;

            case R.id.lyt_orders:
                i=new Intent(getActivity(), NewOrderActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;
            case R.id.txt_viewall:
                i=new Intent(getActivity(), NewOrderActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;
            case R.id.btn_tryagain:
                main_layout.setVisibility(View.VISIBLE);
                no_connection.setVisibility(View.GONE);
                getdashboard();
                break;
        }
    }

    @Override
    public void onItemClick(Object data, Object sender) {
        if (sender.equals(GlobalConstants.VIEW)) {

            OrderModel model = (OrderModel) data;
            Intent i=new Intent(getContext(), OrderDetailsActivity.class);
            i.putExtra("name", "ORDER DETAILS");
            i.putExtra(GlobalConstants.DATA, model);
            startActivity(i);
            getActivity().overridePendingTransition(R.anim.page_in,R.anim.page_out);

        }
    }
}
