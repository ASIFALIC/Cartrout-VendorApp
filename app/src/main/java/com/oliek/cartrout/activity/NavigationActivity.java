package com.oliek.cartrout.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oliek.cartrout.BuildConfig;
import com.oliek.cartrout.Fragment.Dashboard;
import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.base.BaseActivity;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.base.BaseResponse;
import com.oliek.cartrout.model.responsemodel.CategoriesResponseModel;
import com.oliek.cartrout.model.responsemodel.LoginResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;
import com.oliek.cartrout.utill.Popup;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oliek.cartrout.GlobalConstants.TAG;

public class NavigationActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MyPref="info";
    private ApiInterface apiService;
    private UserModel user;
    private PreferenceService sh;
    TextView user_name,entity_add,entity_name;
    ImageView logo;
    Dialog review_popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        sh= PreferenceService.getInstance(this);
        user = PreferenceService.getInstance(this).getUser();
        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        TextView txt_ver_no=findViewById(R.id.txt_version_name);
        txt_ver_no.setText("Version."+ BuildConfig.VERSION_NAME);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.toggle_ico, NavigationActivity.this.getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        display_screens(R.id.nav_home);
        entity_name=navigationView.getHeaderView(0).findViewById(R.id.entity_name);
        user_name=(TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
        entity_add=navigationView.getHeaderView(0).findViewById(R.id.entity_add);
        logo=navigationView.getHeaderView(0).findViewById(R.id.logo);
        tokenupdateandstatus();
    }

    private void tokenupdateandstatus() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d(TAG, token);
                        tokenupdateandstatusapi(token);
                    }
                });
    }

    private void tokenupdateandstatusapi(String token) {
        String url = GlobalConstants.BASE_URL + "usertokenstatus/?token="+user.getApi_tocken()+"&user_id="+user.getId()+"&device_token="+token+"&device_type="+0;
        showProgressDialog(true);

        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        Call<LoginResponseModel> call = apiService.tokenupdateandstatus(url);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        UserModel userModel =response.body().getUser();

                        sh.saveUser(GlobalConstants.PREF_KEY_USER, userModel);
                        user_name.setText(userModel.getName());
                        entity_add.setText(userModel.getEntity().getName());
                        entity_name.setText(userModel.getEntity().getAddress());
                        ImageLoader.getInstance().displayImage(GlobalConstants.BASE_IMAGE_URL+userModel.getEntity().getLogo(), logo);
                        showProgressDialog(false);

                    }else {
                        Toast.makeText(NavigationActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        sh.clearAll();
                        Intent i=new Intent(NavigationActivity.this, LoginActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.page_in,R.anim.page_out);
                        showProgressDialog(false);


                    }

                } else {

                    Toast.makeText(NavigationActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);

                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        display_screens(item.getItemId());

        return true;
    }

    private void display_screens(int id) {

        if (id == R.id.nav_home) {
            Fragment f=new Dashboard();
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_home,f);
            ft.commit();
        }
        if (id == R.id.nav_gallery) {
            Intent i=new Intent(NavigationActivity.this, ItemCatAcivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.page_in,R.anim.page_out);


        }
        if (id == R.id.nav_tools) {
            Intent i=new Intent(NavigationActivity.this, GeneralSettings.class);
            startActivity(i);
            overridePendingTransition(R.anim.page_in,R.anim.page_out);

        }

        if (id == R.id.nav_order_manage) {
            Intent i=new Intent(NavigationActivity.this, NewOrderActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.page_in,R.anim.page_out);


        }
//        if (id == R.id.nav_review) {
////            Intent i=new Intent(NavigationActivity.this, Review_Actvity.class);
////            startActivity(i);
////            overridePendingTransition(R.anim.page_in,R.anim.page_out);
//
//
//        }
//        if (id == R.id.nav_not) {
//            Intent i=new Intent(NavigationActivity.this, NotificationActivity.class);
//            startActivity(i);
//            overridePendingTransition(R.anim.page_in,R.anim.page_out);
//
//
//        }
        /*if (id == R.id.nav_category) {
            Intent i=new Intent(NavigationActivity.this, CatergoryActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.page_in,R.anim.page_out);


        }*/
        if (id == R.id.nav_logout) {
            Logout();

        }
        /*if (id == R.id.nav_send) {
            Intent i=new Intent(Navigation_Activity.this,FeedbackActivity.class);
            startActivity(i);

        }if (id == R.id.nav_share) {
            Intent shareintent =new Intent();
            shareintent.setAction(Intent.ACTION_SEND);
            shareintent.putExtra(Intent.EXTRA_TEXT,"Hello! I'am using CUK for accessing offline syllabus and info in an App. Download it here. (Link Here)");
            shareintent.setType("text/plain");
            startActivity(Intent.createChooser(shareintent,"share via"));

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

        exit();

        }


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


            /*case R.id.action_not:
                Intent i=new Intent(NavigationActivity.this, Notification.class);
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


    private void Logout(){

        LayoutInflater layoutInflater=(LayoutInflater)NavigationActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view=layoutInflater.inflate(R.layout.fore_udate_pop,null);
        Popup popup=new Popup();
        review_popup = popup.dialoglog(NavigationActivity.this, popup_view);
        review_popup.setCancelable(true);
        Button cancel_btn=popup_view.findViewById(R.id.cancel_btn);
        Button btn_positive=popup_view.findViewById(R.id.btn_positive);
        TextView head=popup_view.findViewById(R.id.head);


        head.setText("Are you sure you want to logout now?");



        review_popup.show();
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_popup.dismiss();

            }
        });


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            logoutapi();



            }
        });
    }

    private void logoutapi() {
        String url = GlobalConstants.BASE_URL + "logoutapp/?token="+user.getApi_tocken()+"&user_id="+user.getId();
        showProgressDialog(true);

        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        Call<LoginResponseModel> call = apiService.logout(url);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        sh.clearAll();
                        Intent i=new Intent(NavigationActivity.this, LoginActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.page_in,R.anim.page_out);

                        review_popup.dismiss();
                    }
                } else {

                    Toast.makeText(NavigationActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);

                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }

    private void exit(){

        LayoutInflater layoutInflater=(LayoutInflater)NavigationActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view=layoutInflater.inflate(R.layout.fore_udate_pop,null);
        Popup popup=new Popup();
        final Dialog review_popup = popup.dialoglog(NavigationActivity.this, popup_view);
        review_popup.setCancelable(true);
        Button cancel_btn=popup_view.findViewById(R.id.cancel_btn);
        Button btn_positive=popup_view.findViewById(R.id.btn_positive);
        TextView head=popup_view.findViewById(R.id.head);


        head.setText("Are you sure you want to Exit now?");



        review_popup.show();
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review_popup.dismiss();

            }
        });


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(a);

                finish();


            }
        });
    }

    public void logout_updateFCM(String res_id, String key, int userid, String ftoken) {


    }


    @Override
    protected void showPopUp() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
