package com.oliek.cartrout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.adapters.ComplaintSpenerAdpter;
import com.oliek.cartrout.base.BaseActivity;
import com.oliek.cartrout.model.ComplaintTypeModel;
import com.oliek.cartrout.model.PendingOrderModel;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.responsemodel.ComplaintTypeResponsModel;
import com.oliek.cartrout.model.responsemodel.KmchaingeResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oliek.cartrout.GlobalConstants.TAG;

public class ComplaintActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner spnr_cmplnt;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button btn_submit;
    PendingOrderModel model;
    TextView txt_OrderId;
    EditText edt_complaint;
    private UserModel user;
    private ApiInterface apiService;
    private PreferenceService sh;
    Button btn_history;
    int view_type =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        Intent intent =getIntent();
        if(intent!=null) {
            Bundle bundle =(Bundle)getIntent().getExtras();
            if(bundle!=null){
                 bundle=(Bundle)getIntent().getExtras().get(GlobalConstants.DATA);
                model= (PendingOrderModel) bundle.getSerializable(GlobalConstants.DATA);
                view_type=1;
            }
        }
        sh = PreferenceService.getInstance(this);

        user = sh.getUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Complaint");
        spnr_cmplnt=findViewById(R.id.spnr_cmplnt);
        btn_submit=findViewById(R.id.btn_submit);
        txt_OrderId=findViewById(R.id.txt_OrderId);
        btn_history=findViewById(R.id.btn_history);
        edt_complaint=findViewById(R.id.edt_complaint);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ComplaintActivity.this, ComplaintHistoryActivity.class));
            }
        });
        getData();
        spnr_cmplnt.setOnItemSelectedListener(this);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComplaint();
            }
        });
        setWiew();
    }

    private void submitComplaint() {
            RequestBody requestBody = new FormBody.Builder()
                    .add("order_id", model.getId() + "")
                    .add("complaint_message", edt_complaint.getText().toString().trim())
                    .build();

            apiService = ApiNetwork.getClient().create(ApiInterface.class);
            showProgressDialog(true);
            Call<KmchaingeResponseModel> call = apiService.submitcomplaint("Token " + user.getApi_tocken(), requestBody);
            call.enqueue(new Callback<KmchaingeResponseModel>() {
                @Override
                public void onResponse(Call<KmchaingeResponseModel> call, Response<KmchaingeResponseModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else {
                        Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                    showProgressDialog(false);

                }

                @Override
                public void onFailure(Call<KmchaingeResponseModel> call, Throwable t) {
                    Toast.makeText(getActivity(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                    getData();


                }
            });


    }


    private void setWiew(){
        if(view_type==1){
            txt_OrderId.setText("Order Id : "+model.getId());
        }
    }
    private void getData() {
        showProgressDialog(true);
        user = PreferenceService.getInstance(this).getUser();

//        vendorDashRequest.setId(sh.getUser().getVendorModel().getId()+"");
        apiService = ApiNetwork.getClient().create(ApiInterface.class);
//        Call<VendorDashResponseModel> call = apiService.getVendorAppDash(vendorDashRequest);
        Call<ComplaintTypeResponsModel> call = apiService.getcomplainttypes("Token " + user.getApi_tocken());
        call.enqueue(new Callback<ComplaintTypeResponsModel>() {
            @Override
            public void onResponse(Call<ComplaintTypeResponsModel> call, Response<ComplaintTypeResponsModel> response) {
                if (response.isSuccessful()) {
                    setView(response.body().getArrayList());
                    showProgressDialog(false);

                } else {
                    Toast.makeText(ComplaintActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                    Toast.makeText(ComplaintActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ComplaintTypeResponsModel> call, Throwable t) {
                Toast.makeText(ComplaintActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });

    }

    private void setView( ArrayList<ComplaintTypeModel> arrayList) {
        ArrayList<ComplaintTypeModel> complaintModelArrayList = new ArrayList<ComplaintTypeModel>();
        complaintModelArrayList.add(new ComplaintTypeModel(1,"Long Distance Order"));
        complaintModelArrayList.add(new ComplaintTypeModel(2,"Delivery Charge"));
        complaintModelArrayList.add(new ComplaintTypeModel(3,"Salary Issue"));
        complaintModelArrayList.add(new ComplaintTypeModel(4,"No Orders"));
        complaintModelArrayList.add(new ComplaintTypeModel(5,"Other"));
        ComplaintSpenerAdpter spenerAdpter=new ComplaintSpenerAdpter(this, R.layout.spinner_head, complaintModelArrayList);
        spenerAdpter.setDropDownViewResource(R.layout.spinner_background);
        spnr_cmplnt.setAdapter(spenerAdpter);
    }

    private void spinnerData() {

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        ComplaintTypeModel complaintModel =(ComplaintTypeModel)adapterView.getSelectedItem();
        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + complaintModel.getComplaint_type(), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // todo: goto back activity from here

                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_submit:
                int selectedId=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedId);
                Toast.makeText(ComplaintActivity.this,radioButton.getText(),Toast.LENGTH_SHORT).show();

                break;
        }

    }

    @Override
    protected void showPopUp() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}