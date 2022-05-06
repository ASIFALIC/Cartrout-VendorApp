package com.oliek.cartrout.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.adapters.CategorySpinnerAdapter;
import com.oliek.cartrout.base.BaseActivity;
import com.oliek.cartrout.model.CategoriesModel;
import com.oliek.cartrout.model.CategoryModel;
import com.oliek.cartrout.model.ProductModel;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.base.BaseResponse;
import com.oliek.cartrout.model.responsemodel.OrderCountResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;
import com.oliek.cartrout.utill.Popup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oliek.cartrout.GlobalConstants.TAG;

public class ProductActivity extends BaseActivity implements View.OnClickListener
{
    LinearLayout lyt_home, lyt_menu, lyt_orders;
    Switch sw_status, sw_busy;
    TextView txt_ordercount;
    EditText name;
    EditText description;
    EditText a_name;
    EditText a_description;
    EditText price;
    EditText offer_price;
    EditText  order;
    ScrollView main_layout;
    LinearLayout no_connection;
    Button btn_tryagain;
    ProductModel model;
    TextView date, txt_helpdesk, txt_r_busy, txt_r_status;
    String changed = "N";
    String help_desk_no;
    private PreferenceService sh;
    TextView txt_dc,txt_pc;
    private ApiInterface apiService;
    private UserModel user;
    Spinner sp_webcategory;
    CategorySpinnerAdapter categorySpinnerAdapter;
    private PreferenceService preferenceservice;
ArrayList<CategoryModel> categoryModels;
CategoriesModel categoriesModel;
ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        preferenceservice = PreferenceService.getInstance(this);
        user = preferenceservice.getUser();
        categoriesModel = preferenceservice.getcategories();
        categoryModels =categoriesModel.getCategories();
        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        model = (ProductModel) getIntent().getSerializableExtra(GlobalConstants.DATA);
        this.setTitle(model.getName());
        image= findViewById(R.id.image);
        ImageLoader.getInstance().displayImage(GlobalConstants.BASE_IMAGE_URL+model.getImage(), image);

        name= findViewById(R.id.name);
        description= findViewById(R.id.description);
        a_name= findViewById(R.id.a_name);
        a_description= findViewById(R.id.a_description);
        price= findViewById(R.id.price);
        offer_price= findViewById(R.id.offer_price);
        order= findViewById(R.id.order);
        sp_webcategory= (Spinner) findViewById(R.id.sp_webcategory);


        no_connection = findViewById(R.id.no_connection);
        main_layout = findViewById(R.id.main_layout);
        btn_tryagain = findViewById(R.id.btn_tryagain);
        sh= PreferenceService.getInstance(this);
        lyt_home = findViewById(R.id.lyt_home);
        lyt_menu = findViewById(R.id.lyt_menu);
        lyt_orders = findViewById(R.id.lyt_orders);
        txt_ordercount = findViewById(R.id.txt_ordercount);
        lyt_orders.setOnClickListener(this);
        lyt_home.setOnClickListener(this);
        lyt_menu.setOnClickListener(this);



        getOrdercount();
        setView();
    }
    private void setupcategoryspinnar(ArrayList<CategoryModel> categoryModels) {
        categorySpinnerAdapter = new CategorySpinnerAdapter(this, R.layout.spinner_list_taxi,categoryModels);
        sp_webcategory.setAdapter(categorySpinnerAdapter);
        categorySpinnerAdapter.notifyDataSetChanged();
    }
    public void setCategory( int id) {
        for (int i=0; i<categorySpinnerAdapter.getCount(); i++){
            if (categorySpinnerAdapter.getItem(i).getId()==id){
                sp_webcategory.setSelection(i, true);
                return;
            }
        }
    }
    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()){
            case R.id.lyt_home:
                i=new Intent(ProductActivity.this, NavigationActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);
                break;

            case R.id.lyt_menu:
                i=new Intent(ProductActivity.this, ItemCatAcivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;

            case R.id.lyt_orders:
                i=new Intent(ProductActivity.this, NewOrderActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;
        }
    }

    private void setView() {
        seteditabel(false);
        setupcategoryspinnar(categoryModels);
        setCategory(model.getWeb_product_category_id());
        name.setText(model.getName());
        description.setText(model.getDescription());
        a_name.setText(model.getA_name());
        a_description.setText(model.getA_description());
        price.setText(model.getPrice()+"");
        offer_price.setText(model.getOffer_price()+"");
        order.setText(model.getOrder()+"");
        showProgressDialog(false);
    }
    private void seteditabel(boolean a) {
        name.setFocusable(a);
        description.setFocusable(a);
        a_name.setFocusable(a);
        a_description.setFocusable(a);
        price.setFocusable(a);
        offer_price.setFocusable(a);
        order.setFocusable(a);
        sp_webcategory.setEnabled(a);
        showProgressDialog(false);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                        showProgressDialog(false);
                    }else {
                        Toast.makeText(ProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showProgressDialog(false);
                    }


                } else {
                    Toast.makeText(ProductActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                }
            }

            @Override
            public void onFailure(Call<OrderCountResponseModel> call, Throwable t) {
                Toast.makeText(ProductActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);
            }
        });



    }

    private void setOrderCount(int count) {
        if (count>0){
            Animation shake;
            shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            lyt_orders.startAnimation(shake);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_reverse_count);
            txt_ordercount.startAnimation(myFadeInAnimation);
            txt_ordercount.setText(count+"");
            txt_ordercount.setVisibility(View.VISIBLE);

        }else {
            txt_ordercount.setVisibility(View.GONE);
        }
        showProgressDialog(false);
    }

    private void sw_openpresed(final boolean isChecked) {


        LayoutInflater layoutInflater = (LayoutInflater) ProductActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view = layoutInflater.inflate(R.layout.fore_udate_pop, null);
        Popup popup = new Popup();
        final Dialog review_popup = popup.dialoglog(ProductActivity.this, popup_view);
        review_popup.setCancelable(false);
        Button cancel_btn = popup_view.findViewById(R.id.cancel_btn);
        Button btn_positive = popup_view.findViewById(R.id.btn_positive);
        TextView head = popup_view.findViewById(R.id.head);

        if (isChecked) {
            head.setText("Are you sure you want to change to open status?");

        } else {
            head.setText("Are you sure you want to change to close status?");

        }


        review_popup.show();


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int status = 0;


                if (isChecked) {
                    status = 1;
                }
                updateStatus(status);

                review_popup.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw_status.setChecked(!isChecked);


                review_popup.dismiss();
            }
        });

    }

    private void sw_busypresed(final boolean isChecked) {


        LayoutInflater layoutInflater = (LayoutInflater) ProductActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view = layoutInflater.inflate(R.layout.fore_udate_pop, null);
        Popup popup = new Popup();
        final Dialog review_popup = popup.dialoglog(ProductActivity.this, popup_view);
        review_popup.setCancelable(false);
        Button cancel_btn = popup_view.findViewById(R.id.cancel_btn);
        Button btn_positive = popup_view.findViewById(R.id.btn_positive);
        TextView head = popup_view.findViewById(R.id.head);


        head.setText("Are you sure you want to change busy status?");


        review_popup.show();

        final String c = "N";

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int status = 0;


                if (isChecked) {
                    status = 1;
                }

                updateBusyStatus(status);

                review_popup.dismiss();
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sw_busy.setChecked(!isChecked);

                review_popup.dismiss();


            }
        });
    }

    private void updateBusyStatus(int status) {
        String url = GlobalConstants.BASE_URL + "busystatusedit/?token="+user.getApi_tocken()+"&entity_id="+user.getEntity().getId()+"&is_it_busy="+status;
        showProgressDialog(true);
        Call<BaseResponse> call = apiService.updateBusyStatus(url);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                         user=sh.getUser();
                        user.getEntity().setIs_it_busy(status);
                        sh.saveUser(GlobalConstants.PREF_KEY_USER, user);
                        setView();

                    }else {
                        Toast.makeText(ProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(ProductActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(ProductActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });


    }


    @Override
    protected void showPopUp() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void updateStatus(int status) {
        String url = GlobalConstants.BASE_URL + "openstatusedit/?token="+user.getApi_tocken()+"&entity_id="+user.getEntity().getId()+"&is_it_open="+status;
        showProgressDialog(true);
        Call<BaseResponse> call = apiService.updateStatusopen(url);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                         user=sh.getUser();
                        user.getEntity().setIs_it_open(status);
                        sh.saveUser(GlobalConstants.PREF_KEY_USER, user);
                        setView();
                    }else {
                        Toast.makeText(ProductActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(ProductActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(ProductActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });


    }

    private void openlink(String link) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}