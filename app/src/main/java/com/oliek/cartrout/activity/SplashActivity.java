package com.oliek.cartrout.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.oliek.cartrout.R;
import com.oliek.cartrout.base.BaseActivity;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.preference.PreferenceService;
import com.oliek.cartrout.utill.Popup;

import static com.oliek.cartrout.GlobalConstants.SPLASH_TIME_OUT;


public class SplashActivity extends BaseActivity {

    private EditText etUserName;
    private EditText etPassword;
    private LinearLayout llLogin;
    private ConstraintLayout clSplash;
    private TextView tvSignUp;
    private ImageView ivBackground;
    private PreferenceService sh;
    private ApiInterface apiService;
    private UserModel userModel=new UserModel();
    private UserModel model=new UserModel();
    private String token;
    private String count;


    private SharedPreferences mSharedPrefs;

    private static final String TAG = "tag";
    ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        sh= PreferenceService.getInstance(this);

        model=sh.getUser();
        if (model==null||model.getApi_tocken()==null){

            initLogin();
        }else {

            initHome();

        }
    }
    public void initHome(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void initLogin(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void showPopUp() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
    private void show_popup(String message,String clear_data) {

        LayoutInflater layoutInflater=(LayoutInflater)getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popup_view=layoutInflater.inflate(R.layout.clr_cart_popup,null);
        Popup popup=new Popup();

        final View clr_cart,cancel_btn;

        final Dialog cart_popup = popup.dialog(this, popup_view);
        //cart_popup.setCancelable(true);
        cart_popup.show();
        clr_cart=popup_view.findViewById(R.id.clr_cart);
        cancel_btn=popup_view.findViewById(R.id.cancel_btn);
        TextView head=popup_view.findViewById(R.id.head);
        TextView popup_txt=popup_view.findViewById(R.id.popup_txt);

        head.setText("New updation found !");
        popup_txt.setText(message);

        if (clear_data.equalsIgnoreCase("Y")){

            sh.clearAll();
        }



        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cart_popup.dismiss();
                finish();
                System.exit(0);

            }
        });



        clr_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cart_popup.dismiss();
                final String PackageName=getPackageName();
                try{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+PackageName)));

                }catch (Exception e){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+PackageName)));

                }
            }
        });



    }

}
