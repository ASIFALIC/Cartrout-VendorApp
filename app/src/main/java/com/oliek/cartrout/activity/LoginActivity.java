package com.oliek.cartrout.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.base.BaseActivity;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.base.BaseResponse;
import com.oliek.cartrout.model.requestmodel.LoginrequestModel;
import com.oliek.cartrout.model.responsemodel.LoginResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oliek.cartrout.GlobalConstants.SPLASH_TIME_OUT;


public class LoginActivity extends BaseActivity {

    private EditText etUserName, etPassword;
    private Button llLogin;
    private TextView tvSignUp,fp;
    //private ImageView ivBackground;

    private SharedPreferences mSharedPrefs;
    private PreferenceService sh;
    private ApiInterface apiService;
    private UserModel userModel;
    private String token;
    private String count;

    private LoginrequestModel loginrequestModel=new LoginrequestModel();
    private ProgressDialog progressDialog;
    ProgressBar progressBar;
    private static final String TAG = "tag";
    TextInputLayout textInputUsername;
    public LoginActivity() {
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mSharedPrefs = getSharedPreferences(GlobalConstants.USER_PREFS, Context.MODE_PRIVATE);
        sh= PreferenceService.getInstance(this);
        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        loginrequestModel = new LoginrequestModel();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textInputUsername= (TextInputLayout) findViewById(R.id.textInputUsername);
        etUserName = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        fp = (TextView) findViewById(R.id.fp);
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validatefb()) {
                    forgotpassword();
                }else{
                    Toast.makeText(getActivity(), "please enter a valid mobile number", Toast.LENGTH_SHORT).show();

                }
            }
        });
        progressBar.setVisibility(View.GONE);
        llLogin = (Button) findViewById(R.id.ll_login);
        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateLogin()) {
                    login();

                }else {
                    Toast.makeText(getActivity(), "please enter a valid mobile number", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private boolean validatefb() {
        if (etUserName.getText().toString().isEmpty() || etUserName.getText().toString().equals("")|| etUserName.getText().toString().length()!=10) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validateLogin() {
        if (etUserName.getText().toString().isEmpty() || etUserName.getText().toString().equals("")) {
            return false;
        } else if (etPassword.getText().toString().isEmpty() || etPassword.getText().toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public void lodeHome() {
        showProgressDialog(false);
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    public void saveUser(String key, String value) {
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    */


    public void showProgressDialog(boolean isShowing) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (isShowing) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }

    @Override
    protected void showPopUp() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }



    public void login() {

        Log.d(TAG, new Gson().toJson(loginrequestModel));
        String url = GlobalConstants.BASE_URL + "login/?username="+etUserName.getText().toString()+"&password="+etPassword.getText().toString();


        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        Call<LoginResponseModel> call = apiService.getLogin(url);
        call.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){

                        UserModel userModel =response.body().getUser();
                        sh.saveUser(GlobalConstants.PREF_KEY_USER, userModel);
                        initHome();
                    }else {
                        textInputUsername.setError(response.body().getMessage());

                    }


                } else {
                    Toast.makeText(getActivity(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void forgotpassword() {
        progressBar.setVisibility(View.VISIBLE);

        RequestBody requestBody =new FormBody.Builder()
                .add("mobile","+91"+etUserName.getText().toString())
                .add("user_type","1")
                .build();

        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        Call<BaseResponse> call = apiService.getpin(requestBody);

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(getActivity(), response.body().getPin(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    progressBar.setVisibility(View.GONE);


                } else {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(getActivity(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public void setProgressBar(final int a) {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(a);
            }
        }, SPLASH_TIME_OUT);
    }


    public void initHome(){
        setProgressBar(80);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                startActivity(intent);
                setProgressBar(100);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(a);

        finish();

    }
}
