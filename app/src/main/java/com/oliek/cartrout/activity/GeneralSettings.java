package com.oliek.cartrout.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.oliek.cartrout.BuildConfig;
import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.base.BaseActivity;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.base.BaseResponse;
import com.oliek.cartrout.model.responsemodel.OrderCountResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;
import com.oliek.cartrout.utill.Popup;
import com.oliek.cartrout.utill.ProgressShow;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oliek.cartrout.GlobalConstants.TAG;

public class GeneralSettings extends BaseActivity implements View.OnClickListener
{
    LinearLayout lyt_home, lyt_menu, lyt_orders;
    Switch sw_status, sw_busy;
    TextView txt_ordercount;
    LinearLayout ll_whatsup, ll_mail, ll_web;


    ScrollView main_layout;
    LinearLayout no_connection;
    Button btn_tryagain;

    TextView txt_version,date, txt_helpdesk, txt_r_busy, txt_r_status;
    String changed = "N";
    String help_desk_no;
    private PreferenceService sh;
    TextView txt_dc,txt_pc;
    private ApiInterface apiService;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_settings);
        this.setTitle("General Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        user = PreferenceService.getInstance(this).getUser();
        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        no_connection = findViewById(R.id.no_connection);
        main_layout = findViewById(R.id.main_layout);
        btn_tryagain = findViewById(R.id.btn_tryagain);
        sh= PreferenceService.getInstance(this);
        txt_dc = findViewById(R.id.delivery_charge);
        date= findViewById(R.id.date);
        txt_dc.setText(user.getEntity().getDelivery_charge());

        txt_pc = findViewById(R.id.packing_charge);
        txt_pc.setText(user.getEntity().getPacking_charge());
        sw_status = findViewById(R.id.sw_status);
        sw_busy = findViewById(R.id.sw_busy);
        sw_status.setOnCheckedChangeListener(null);
        lyt_orders=findViewById(R.id.lyt_orders);
        lyt_home=findViewById(R.id.lyt_home);
        lyt_menu=findViewById(R.id.lyt_menu);
        lyt_orders.setOnClickListener(this);
        lyt_home.setOnClickListener(this);
        lyt_menu.setOnClickListener(this);
        sw_busy.setOnCheckedChangeListener(null);
        sw_busy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sw_busypresed(sw_busy.isChecked());


            }
        });
        sw_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sw_openpresed(sw_status.isChecked());


            }
        });
        lyt_home = findViewById(R.id.lyt_home);
        lyt_menu = findViewById(R.id.lyt_menu);
        lyt_orders = findViewById(R.id.lyt_orders);
        txt_version = findViewById(R.id.txt_version);
        txt_ordercount = findViewById(R.id.txt_ordercount);
        ll_whatsup = findViewById(R.id.ll_whadsap);
        ll_whatsup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openlink("https://wa.me/message/DZME2F7HQ77FO1");
            }
        });

        ll_mail = findViewById(R.id.ll_mail);
        ll_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openlink("mailto:info@cartrout.com");
            }
        });

        ll_web = findViewById(R.id.ll_web);
        ll_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openlink("https://cartrout.com/");
            }
        });
        txt_version.setText("Version : " + BuildConfig.VERSION_NAME);
        getOrdercount();
        setView();
    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()){
            case R.id.lyt_home:
                i=new Intent(GeneralSettings.this, NavigationActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);
                break;

            case R.id.lyt_menu:
                i=new Intent(GeneralSettings.this, ItemCatAcivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;

            case R.id.lyt_orders:
                i=new Intent(GeneralSettings.this, NewOrderActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;
        }
    }

    private void setView() {
        if (user.getEntity().getIs_it_busy() == 0) {
            sw_busy.setChecked(false);
        } else {
            sw_busy.setChecked(true);

        }
        if (user.getEntity().getIs_it_open() == 0) {
            sw_status.setChecked(false);
        } else {
            sw_status.setChecked(true);

        }
        date.setText(user.getEntity().getExpiry_date());
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
                        Toast.makeText(GeneralSettings.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showProgressDialog(false);
                    }


                } else {
                    Toast.makeText(GeneralSettings.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                }
            }

            @Override
            public void onFailure(Call<OrderCountResponseModel> call, Throwable t) {
                Toast.makeText(GeneralSettings.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
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


        LayoutInflater layoutInflater = (LayoutInflater) GeneralSettings.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view = layoutInflater.inflate(R.layout.fore_udate_pop, null);
        Popup popup = new Popup();
        final Dialog review_popup = popup.dialoglog(GeneralSettings.this, popup_view);
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

    }

    private void sw_busypresed(final boolean isChecked) {


        LayoutInflater layoutInflater = (LayoutInflater) GeneralSettings.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view = layoutInflater.inflate(R.layout.fore_udate_pop, null);
        Popup popup = new Popup();
        final Dialog review_popup = popup.dialoglog(GeneralSettings.this, popup_view);
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
        String url = GlobalConstants.BASE_URL + "busystatusedit/?token="+user.getApi_tocken()+"&entity_id="+user.getEntity().getId()+"&is_it_open="+status;
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
                        Toast.makeText(GeneralSettings.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(GeneralSettings.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(GeneralSettings.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(GeneralSettings.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(GeneralSettings.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(GeneralSettings.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
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