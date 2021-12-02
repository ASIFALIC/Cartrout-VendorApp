package com.oliek.cartrout.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.adapters.NewOrderListAdapter;
import com.oliek.cartrout.base.BaseActivity;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.OrderModel;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.responsemodel.OrderCountResponseModel;
import com.oliek.cartrout.model.responsemodel.OrderListResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.oliek.cartrout.GlobalConstants.TAG;


public class NewOrderActivity extends BaseActivity implements View.OnClickListener, RecycleViewItemCallBack {


    public static final String MyPref="info";
    private String key;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private String NEXT_URL ;
    private int TOTAL_PAGES ;

    private int currentPage = PAGE_START;

    LinearLayout lyt_home,lyt_orders,lyt_menu;
    TextView txt_ordercount,txt_count;

    SwipeRefreshLayout main_content;
    LinearLayout no_connection;
    Button btn_tryagain;
    NewOrderListAdapter orderListAdapter;
    Retrofit retrofit;
    TextView textView;
    int count;
    Vibrator vibe;
    MediaPlayer mp;
    TextView empty;
    private PreferenceService sh;
    private ApiInterface apiService;
    private UserModel user;
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
getOrderlist();
        getOrdercount();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        this.setTitle("New Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        SharedPreferences sp=getSharedPreferences(MyPref,MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        key=sp.getString("token",null);
        user = PreferenceService.getInstance(this).getUser();
        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        mp = MediaPlayer.create(this, R.raw.ring);
        empty=findViewById(R.id.empty_layout);
        txt_count=findViewById(R.id.txt_count);
        lyt_orders=findViewById(R.id.lyt_orders);
        lyt_home=findViewById(R.id.lyt_home);
        lyt_menu=findViewById(R.id.lyt_menu);
        txt_ordercount=findViewById(R.id.txt_ordercount);
        main_content=findViewById(R.id.main_content);
        no_connection=findViewById(R.id.no_connection);
        btn_tryagain=findViewById(R.id.btn_tryagain);
        lyt_orders.setOnClickListener(this);
        lyt_home.setOnClickListener(this);
        lyt_menu.setOnClickListener(this);
        vibe= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        txt_count.setText("New Orders");

        recyclerView = (RecyclerView) findViewById(R.id.rec_cat);
        LinearLayoutManager layoutManagerNewsTrending = new LinearLayoutManager(this);
        layoutManagerNewsTrending.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManagerNewsTrending);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManagerNewsTrending.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);



        main_content.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                main_content.setRefreshing(false);
            }
        });

        btn_tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no_connection.setVisibility(View.GONE);
            }
        });
        getOrderlist();
        getOrdercount();

    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()){
            case R.id.lyt_home:


                i=new Intent(NewOrderActivity.this, NavigationActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);
                break;

            case R.id.lyt_menu:
                i=new Intent(NewOrderActivity.this, ItemCatAcivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;

            case R.id.btn_tryagain:
              /*  newOrderPresenter.requestNewOrderData(key,resid);
                newOrderPresenter.getOrderCount(key,resid);*/
                no_connection.setVisibility(View.GONE);

                break;
        }
    }

    @Override
    protected void showPopUp() {

    }


    private void getOrderlist() {
        String url = GlobalConstants.BASE_URL + "getorder/?token="+user.getApi_tocken()+"&entity_id="+user.getEntity().getId();
        showProgressDialog(true);
        Call<OrderListResponseModel> call = apiService.getorderlist(url);
        call.enqueue(new Callback<OrderListResponseModel>() {
            @Override
            public void onResponse(Call<OrderListResponseModel> call, Response<OrderListResponseModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        initRecyclerView(response.body().getOrders());

                    }else {
                        Toast.makeText(NewOrderActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(NewOrderActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderListResponseModel> call, Throwable t) {
                Toast.makeText(NewOrderActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });



    }

    private void setOrderCount(int count ) {
        if (count>0){
            Animation shake;
            shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            lyt_orders.startAnimation(shake);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(NewOrderActivity.this, R.anim.bounce_reverse_count);
            txt_ordercount.startAnimation(myFadeInAnimation);
            txt_ordercount.setText(count+"");
            txt_count.setText("New Orders ("+ count+")");
            txt_ordercount.setVisibility(View.VISIBLE);
        }else {
            txt_ordercount.setVisibility(View.GONE);
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
                        Toast.makeText(NewOrderActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(NewOrderActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderCountResponseModel> call, Throwable t) {
                Toast.makeText(NewOrderActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });



    }


    private void initRecyclerView(ArrayList<OrderModel> arrayList) {
        if (arrayList != null&&arrayList.size()!=0) {
            orderListAdapter = new NewOrderListAdapter(this, arrayList, this);
            recyclerView.setAdapter(orderListAdapter);
            orderListAdapter.notifyDataSetChanged();
            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            showProgressDialog(false);



        }else {
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            showProgressDialog(false);

        }
    }


    private void handleError(Throwable t) {

        //Add your error here.
    }

    private void onError(Throwable throwable) {
        Toast.makeText(this, "OnError in Observable Timer",
                Toast.LENGTH_LONG).show();
    }

    private void loadNextPage() {

    }

   




    @Override
    public void onBackPressed() {


        Intent i=new Intent(NewOrderActivity.this, NavigationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

        finish();


    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.page_in,R.anim.page_out);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                // todo: goto back activity from here
                Intent i=new Intent(NewOrderActivity.this, NavigationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            /*case R.id.action_not:
                Intent i=new Intent(Orders.this, Notification.class);
                startActivity(i);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu paramMenu) {
        MenuItem action_cat,action_print;
        action_cat=paramMenu.findItem(R.id.action_cat);
        action_print=paramMenu.findItem(R.id.action_print);
        action_cat.setVisible(false);
        action_print.setVisible(false);
        return true;
    }

    @Override
    public void onItemClick(Object data, Object sender) {
        if (sender.equals(GlobalConstants.VIEW)) {

            OrderModel model = (OrderModel) data;
            Intent i=new Intent(NewOrderActivity.this, OrderDetailsActivity.class);
            i.putExtra("name", "ORDER DETAILS");
            i.putExtra(GlobalConstants.DATA, model);
            startActivity(i);
            overridePendingTransition(R.anim.page_in,R.anim.page_out);

        }



    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
