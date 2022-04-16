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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oliek.cartrout.GlobalConstants.TAG;

public class HelpActivity extends BaseActivity implements View.OnClickListener
{
    LinearLayout lyt_home, lyt_menu, lyt_orders;
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
        setContentView(R.layout.activity_help);
        this.setTitle("HelpActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        user = PreferenceService.getInstance(this).getUser();
        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        no_connection = findViewById(R.id.no_connection);
        main_layout = findViewById(R.id.main_layout);
        btn_tryagain = findViewById(R.id.btn_tryagain);
        sh= PreferenceService.getInstance(this);

        lyt_orders=findViewById(R.id.lyt_orders);
        lyt_home=findViewById(R.id.lyt_home);
        lyt_menu=findViewById(R.id.lyt_menu);
        lyt_orders.setOnClickListener(this);
        lyt_home.setOnClickListener(this);
        lyt_menu.setOnClickListener(this);


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
    }


    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()){
            case R.id.lyt_home:
                i=new Intent(HelpActivity.this, NavigationActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);
                break;

            case R.id.lyt_menu:
                i=new Intent(HelpActivity.this, ItemCatAcivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;

            case R.id.lyt_orders:
                i=new Intent(HelpActivity.this, NewOrderActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;
        }
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
                        Toast.makeText(HelpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showProgressDialog(false);
                    }


                } else {
                    Toast.makeText(HelpActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                }
            }

            @Override
            public void onFailure(Call<OrderCountResponseModel> call, Throwable t) {
                Toast.makeText(HelpActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
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





    @Override
    protected void showPopUp() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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