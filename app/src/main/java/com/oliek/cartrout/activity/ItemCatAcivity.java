package com.oliek.cartrout.activity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.adapters.CategoryListAdapter;
import com.oliek.cartrout.base.BaseActivity;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.CategoryModel;
import com.oliek.cartrout.model.PendingOrderModel;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.base.BaseResponse;
import com.oliek.cartrout.model.responsemodel.CategoriesResponseModel;
import com.oliek.cartrout.model.responsemodel.OrderCountResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;
import com.oliek.cartrout.utill.PaginationScrollListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oliek.cartrout.GlobalConstants.TAG;

public class ItemCatAcivity extends BaseActivity implements View.OnClickListener , RecycleViewItemCallBack {


    public static final String MyPref="info";
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
    private PreferenceService sh;
    private ApiInterface apiService;
    private UserModel user;
    CategoryListAdapter categoryListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_cat_acivity);

        this.setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        SharedPreferences sp=getSharedPreferences(MyPref,MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        Intent mIntent = getIntent();
        user = PreferenceService.getInstance(this).getUser();
        user.setUser_type(4);

        if(user.getUser_type()==4){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    this);
            builder.setCancelable(false);
            builder.setTitle("Authorized");
            builder.setMessage("User not Authorized to this Page");
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            gotoHome();
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        txt_count=findViewById(R.id.txt_count);

        txt_ordercount=findViewById(R.id.txt_ordercount);
        main_content=findViewById(R.id.main_content);
        no_connection=findViewById(R.id.no_connection);
        btn_tryagain=findViewById(R.id.btn_tryagain);
        lyt_orders=findViewById(R.id.lyt_orders);
        lyt_home=findViewById(R.id.lyt_home);
        lyt_menu=findViewById(R.id.lyt_menu);
        lyt_orders.setOnClickListener(this);
        lyt_home.setOnClickListener(this);
        lyt_menu.setOnClickListener(this);
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
                getdata();
                getOrdercount();
                main_content.setRefreshing(false);
            }
        });

        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        btn_tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getdata();
                getOrdercount();
                no_connection.setVisibility(View.GONE);
            }
        });

        getdata();
        getOrdercount();
    }

    private void gotoHome() {
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
        finish();
    }


    private void setOrderCount(int count ) {
        if (count>0){
            Animation shake;
            shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            lyt_orders.startAnimation(shake);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(ItemCatAcivity.this, R.anim.bounce_reverse_count);
            txt_ordercount.startAnimation(myFadeInAnimation);
            txt_ordercount.setText(count+"");
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
                        Toast.makeText(ItemCatAcivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(ItemCatAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderCountResponseModel> call, Throwable t) {
                Toast.makeText(ItemCatAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });



    }
    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()){
            case R.id.lyt_home:
                i=new Intent(ItemCatAcivity.this, NavigationActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;

            case R.id.lyt_orders:
                i=new Intent(ItemCatAcivity.this, NewOrderActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;
            /*case R.id.lyt_menu:
                i=new Intent(ItemCatAcivity.this, ItemManagementAcivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;*/
        }
    }

    @Override
    protected void showPopUp() {

    }


    private void getdata() {

        String url = GlobalConstants.BASE_URL + "getwebcategories/?token="+user.getApi_tocken()+"&entity_id="+user.getEntity().getId();
        showProgressDialog(true);
        Call<CategoriesResponseModel> call = apiService.getcategories(url);
        call.enqueue(new Callback<CategoriesResponseModel>() {
            @Override
            public void onResponse(Call<CategoriesResponseModel> call, Response<CategoriesResponseModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        initRecyclerView(response.body().getCategories());

                    }else {
                        Toast.makeText(ItemCatAcivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(ItemCatAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponseModel> call, Throwable t) {
                Toast.makeText(ItemCatAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });




    }

    private void initRecyclerView(ArrayList<CategoryModel> arrayList) {
        if (arrayList != null&&arrayList.size()!=0) {
            categoryListAdapter = new CategoryListAdapter(this, arrayList, this);
            recyclerView.setAdapter(categoryListAdapter);
            categoryListAdapter.notifyDataSetChanged();
//            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            showProgressDialog(false);



        }else {
//            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }


    private void loadNextPage() {



    }

    private void categoryStatus(String catSlug) {


    }



    private void getOrderCount() {


    }




    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.page_in,R.anim.page_out);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                // todo: goto back activity from here
                super.onBackPressed();
                return true;
           /* case R.id.action_not:
                Intent i=new Intent(OrderManagement.this, Notification.class);
                startActivity(i);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemClick(Object data, Object sender) {
        if (sender.equals(GlobalConstants.VIEW)) {

            CategoryModel model = (CategoryModel) data;
            Intent i=new Intent(ItemCatAcivity.this, ItemManagementAcivity.class);
            i.putExtra("name", "ORDER DETAILS");
            i.putExtra(GlobalConstants.DATA, model);
            startActivity(i);
            overridePendingTransition(R.anim.page_in,R.anim.page_out);

        }
        if (sender.equals(GlobalConstants.KEY_ACTVE)) {

            CategoryModel model = (CategoryModel) data;
            categorystatusedit(model.getId(),1);
        }
        if (sender.equals(GlobalConstants.KEY_INACTVE)) {

            CategoryModel model = (CategoryModel) data;
            categorystatusedit(model.getId(),0);

        }
    }

    private void categorystatusedit(int id, int i) {
        String url = GlobalConstants.BASE_URL + "categorystatusedit/?token="+user.getApi_tocken()+"&id="+id+"&status="+i;
        showProgressDialog(true);
        Call<BaseResponse> call = apiService.categorystatusedit(url);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){

                    }else {
                        Toast.makeText(ItemCatAcivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Toast.makeText(ItemCatAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
                getdata();
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(ItemCatAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                getdata();
            }
        });



    }
}