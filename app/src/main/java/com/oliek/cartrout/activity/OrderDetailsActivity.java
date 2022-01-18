package com.oliek.cartrout.activity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.android.gms.common.api.internal.RemoteCall;
import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.adapters.CartItamAdapter;
import com.oliek.cartrout.base.BaseActivity;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.dialogue.ItemDialog;
import com.oliek.cartrout.model.CartModel;
import com.oliek.cartrout.model.OrderModel;
import com.oliek.cartrout.model.ProductModel;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.base.BaseResponse;
import com.oliek.cartrout.model.responsemodel.OrderCountResponseModel;
import com.oliek.cartrout.model.responsemodel.OrderViewResponseModel;
import com.oliek.cartrout.model.responsemodel.ProductsResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;
import com.oliek.cartrout.utill.Popup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oliek.cartrout.GlobalConstants.TAG;
import static java.security.AccessController.getContext;

public class OrderDetailsActivity extends BaseActivity implements RecycleViewItemCallBack {

    LinearLayout lyt_orders, lyt_home, lyt_menu, lyt_status_btn;
    RecyclerView rec_orderdetails;
    private List<CartModel> menuList;
    private ProgressBar pbLoadCast;
    private TextView txt_orderno, txt_date_time, txt_name;
    private ImageView img_call;
    private TextView txt_status, txt_menu_qty, txt_total_rate;
    String staff_phone;
    Button btn_accept, btn_delivered,btn_reject;
    LinearLayout lyt_accpet_reject,ll_home_d,llcall;
    CartItamAdapter cartItamAdapter;
    ItemDialog itamdialoge;
    private int resid, userID;
    private String key;
    int orderId;
     Dialog review_popup,add_popup,edit_popup;
    TextView txt_ordercount, txt_excl_rate, txt_tax_rate, txt_packingcharge, txt_disc_amnt,txt_itam_total,txt_delivery_charge;
TextView txttv,txthd,txt_address,txt_landmarks;
    private PreferenceService sh;
    private ApiInterface apiService;
    private UserModel user;

    private UsbManager mUsbManager;
    private UsbDevice mDevice;
    private UsbDeviceConnection mConnection;
    private UsbInterface mInterface;
    private UsbEndpoint mEndPoint;
    private PendingIntent mPermissionIntent;
ImageButton add_itam;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static final Boolean forceCLaim = true;
    OrderModel model;
    HashMap<String, UsbDevice> mDeviceList;
    Iterator<UsbDevice> mDeviceIterator;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        this.setTitle("ORDER DETAILS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        model = (OrderModel) getIntent().getSerializableExtra(GlobalConstants.DATA);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManagerNewsTrending = new LinearLayoutManager(this);
        layoutManagerNewsTrending.setOrientation(LinearLayoutManager.VERTICAL);

        user = PreferenceService.getInstance(this).getUser();
        apiService = ApiNetwork.getClient().create(ApiInterface.class);
        ll_home_d = findViewById(R.id.ll_home_d);
        lyt_status_btn = findViewById(R.id.lyt_status_btn);
        txt_landmarks = findViewById(R.id.txt_landmarks);
        txt_address= findViewById(R.id.txt_address);
        txttv= findViewById(R.id.txt_tk);
        txthd= findViewById(R.id.txt_hd);
        lyt_accpet_reject = findViewById(R.id.lyt_accpet_reject);
        lyt_orders = findViewById(R.id.lyt_orders);
        lyt_home = findViewById(R.id.lyt_home);
        lyt_menu = findViewById(R.id.lyt_menu);
        llcall = findViewById(R.id.ll_call);
        txt_orderno = findViewById(R.id.txt_orderno);
        txt_date_time = findViewById(R.id.txt_date_time);
        txt_name = findViewById(R.id.txt_name);
        img_call = findViewById(R.id.img_call);
        txt_status = findViewById(R.id.txt_status);
        txt_menu_qty = findViewById(R.id.txt_menu_qty);
        txt_total_rate = findViewById(R.id.txt_total_rate);
        btn_accept = findViewById(R.id.btn_accept);
        btn_reject= findViewById(R.id.btn_reject);
        btn_delivered = findViewById(R.id.btn_delivered);
        txt_ordercount = findViewById(R.id.txt_ordercount);
        txt_itam_total = findViewById(R.id.txt_itam_total);
        txt_delivery_charge = findViewById(R.id.txt_delivery_charge);
        txt_packingcharge = findViewById(R.id.txt_packingcharge);
        txt_disc_amnt = findViewById(R.id.txt_disc_amnt);

        menuList = new ArrayList<>();

        pbLoadCast = findViewById(R.id.pb_cast_loading);
        orderId =model.getId();
        recyclerView.setLayoutManager(layoutManagerNewsTrending);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManagerNewsTrending.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        lyt_orders.setOnClickListener(this);
        lyt_menu.setOnClickListener(this);
        lyt_home.setOnClickListener(this);
        btn_accept.setOnClickListener(this);
        btn_reject.setOnClickListener(this);
        btn_delivered.setOnClickListener(this);
        add_itam= findViewById(R.id.add_itam);
        add_itam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getProductLit();

            }
        });


        llcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!staff_phone.equals("")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + staff_phone));
                    startActivity(intent);
                    overridePendingTransition(R.anim.page_in, R.anim.page_out);

                }

            }
        });


        getdata();
        getOrdercount();


    }

    private void getProductLit() {

        String url = GlobalConstants.BASE_URL + "getproductnactiv/?token="+user.getApi_tocken()+"&entity_id="+user.getEntity().getId();
        showProgressDialog(true);
        Call<ProductsResponseModel> call = apiService.getproductnactiv(url);
        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call, Response<ProductsResponseModel> response) {
                if (response.body()!=null) {
                    if(response.body().isSuccess()){

                        showpopupProduvt(response.body().getProducts());

                    }else {
                        Toast.makeText(OrderDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showProgressDialog(false);
                    }


                } else {
                    Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                }
            }

            @Override
            public void onFailure(Call<ProductsResponseModel> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });
    }


    private void initRecyclerView(ArrayList<CartModel> arrayList) {
        if (arrayList != null&&arrayList.size()!=0) {
            cartItamAdapter = new CartItamAdapter(this, arrayList,this);
            recyclerView.setAdapter(cartItamAdapter);
            cartItamAdapter.notifyDataSetChanged();
//            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            showProgressDialog(false);



        }else {
//            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            showProgressDialog(false);

        }
    }

    private void getdata() {
        String url = GlobalConstants.BASE_URL + "getorderdetail/?token="+user.getApi_tocken()+"&order_id="+model.getId();
        showProgressDialog(true);
        Call<OrderViewResponseModel> call = apiService.getorderdetail(url);
        call.enqueue(new Callback<OrderViewResponseModel>() {
            @Override
            public void onResponse(Call<OrderViewResponseModel> call, Response<OrderViewResponseModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        model=response.body().getOrder();
                        setDataToView(model);

                        initRecyclerView(response.body().getOrder().getCarts());
                    }else {
                        Toast.makeText(OrderDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showProgressDialog(false);

                    }


                } else {
                    showProgressDialog(false);

                    Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderViewResponseModel> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });

    }

    private void setOrderCount(int count ) {
        if (count>0){
            Animation shake;
            shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            lyt_orders.startAnimation(shake);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(OrderDetailsActivity.this, R.anim.bounce_reverse_count);
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
                        showProgressDialog(false);

                    }else {
                        Toast.makeText(OrderDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showProgressDialog(false);

                    }


                } else {
                    Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);

                }
            }

            @Override
            public void onFailure(Call<OrderCountResponseModel> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });



    }








    public void setDataToView(OrderModel model) {

        txt_orderno.setText(model.getInvoiceno() + "");
        txt_date_time.setText(model.getTime() + model.getDiff());
        txt_name.setText("name : " + model.getName());

        if (model.getStatus() == 0) {
            txt_status.setTextColor(getResources().getColor(R.color.confrimed));
            txt_status.setText("Placed");
            lyt_accpet_reject.setVisibility(View.VISIBLE);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(OrderDetailsActivity.this, R.anim.bounce_reverse);
            btn_accept.startAnimation(myFadeInAnimation);
            Animation myFadeInAnimation2 = AnimationUtils.loadAnimation(OrderDetailsActivity.this, R.anim.bounce_reverse);

            btn_reject.startAnimation(myFadeInAnimation2);

        } else if (model.getStatus() == 1) {
            txt_status.setTextColor(getResources().getColor(R.color.picked));
            txt_status.setText("Confirmed");
            btn_delivered.setVisibility(View.VISIBLE);
            lyt_accpet_reject.setVisibility(View.VISIBLE);
            btn_reject.clearAnimation();
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(OrderDetailsActivity.this, R.anim.bounce_reverse);
            btn_accept.setVisibility(View.GONE);
            btn_accept.clearAnimation();

            btn_delivered.startAnimation(myFadeInAnimation);

            Animation myFadeInAnimation2 = AnimationUtils.loadAnimation(OrderDetailsActivity.this, R.anim.bounce_reverse);

            btn_reject.startAnimation(myFadeInAnimation2);
        } else if (model.getStatus() == 3) {
            txt_status.setTextColor(getResources().getColor(R.color.cancelled));
            txt_status.setText("Cancelled");
            lyt_accpet_reject.setVisibility(View.GONE);
            btn_delivered.setVisibility(View.GONE);
            btn_delivered.clearAnimation();
            btn_reject.clearAnimation();

            lyt_status_btn.setVisibility(View.GONE);
            img_call.setVisibility(View.GONE);
        } else if (model.getStatus() == 2) {
            txt_status.setTextColor(getResources().getColor(R.color.delivered));
            txt_status.setText("Delivered");
            img_call.setVisibility(View.VISIBLE);
            lyt_accpet_reject.setVisibility(View.GONE);
            btn_delivered.setVisibility(View.GONE);
            btn_delivered.clearAnimation();
            btn_reject.clearAnimation();

            lyt_status_btn.setVisibility(View.GONE);

        }
        if (model.getDelivery_type() == 0) {
            txttv.setVisibility(View.VISIBLE);
            txthd.setVisibility(View.GONE);
        } else if (model.getDelivery_type() == 1) {
            txttv.setVisibility(View.GONE);
            txthd.setVisibility(View.VISIBLE);
            ll_home_d.setVisibility(View.VISIBLE);
            txt_address.setText(model.getAddress());
            txt_landmarks.setText(model.getLandmarks());
        }
        txt_itam_total.setText(model.getTotal());
        if (model.getDelivery_charge().equals("0")) {
            txt_delivery_charge.setVisibility(View.GONE);
        }
        if (model.getPacking_charge().equals("0")) {
            txt_packingcharge.setVisibility(View.GONE);
        }

        txt_delivery_charge.setText(model.getDelivery_charge());
        txt_packingcharge.setText(model.getPacking_charge());
        txt_disc_amnt.setText("0");
        txt_total_rate.setText(model.getSubtotal());
        showProgressDialog(false);
    }





    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.lyt_home:
                i = new Intent(OrderDetailsActivity.this, NavigationActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in, R.anim.page_out);

                break;

            case R.id.lyt_orders:
                i = new Intent(OrderDetailsActivity.this, NewOrderActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in, R.anim.page_out);

                break;
            case R.id.lyt_menu:
                i = new Intent(OrderDetailsActivity.this, ItemCatAcivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in, R.anim.page_out);

                break;
            case R.id.btn_accept:


                orderstatuschange(1);


                break;
            case R.id.btn_delivered:

                orderstatuschange(2);

                break;
            case R.id.btn_reject:


                RejectPopup();

                break;


        }
    }



    private void orderstatuschange(int status) {
        String url = GlobalConstants.BASE_URL + "changeorderstatus/?token="+user.getApi_tocken()+"&order_id="+model.getId()+"&order_status="+status;
        showProgressDialog(true);
        Call<OrderViewResponseModel> call = apiService.orderstatuschange(url);
        call.enqueue(new Callback<OrderViewResponseModel>() {
            @Override
            public void onResponse(Call<OrderViewResponseModel> call, Response<OrderViewResponseModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        model=response.body().getOrder();
                        setDataToView(model );

                    }else {
                        Toast.makeText(OrderDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showProgressDialog(false);

                    }


                } else {
                    showProgressDialog(false);

                    Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderViewResponseModel> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });

    }

    @Override
    protected void showPopUp() {

    }

    private void RejectPopup() {

        LayoutInflater layoutInflater = (LayoutInflater) OrderDetailsActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view = layoutInflater.inflate(R.layout.reject_popup, null);
        Popup popup = new Popup();
        review_popup = popup.dialoglog(OrderDetailsActivity.this, popup_view);
        review_popup.setCancelable(true);
        Button cancel_btn = popup_view.findViewById(R.id.cancel_btn);
        Button btn_positive = popup_view.findViewById(R.id.btn_positive);
        TextView head = popup_view.findViewById(R.id.head);
        TextView content = popup_view.findViewById(R.id.popup_txt);


        head.setText("Are you sure to reject the order");
        content.setText("ID : "+model.getInvoiceno()) ;
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

                orderstatuschange(3);
                review_popup.dismiss();

            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }




    @Override
    public void onBackPressed() {


        Intent i=new Intent(OrderDetailsActivity.this, NewOrderActivity.class);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onPrepareOptionsMenu(android.view.Menu paramMenu) {
        MenuItem action_cat,action_print;
        action_cat=paramMenu.findItem(R.id.action_cat);
        action_cat.setVisible(false);
        action_print=paramMenu.findItem(R.id.action_print);
        action_print.setVisible(false);
        return true;
    }


    @Override
    public void onItemClick(Object data, Object sender) {
        if (sender.equals(GlobalConstants.EDIT)) {

            CartModel model = (CartModel) data;
           editCartItam(model);

        }else if (sender.equals(GlobalConstants.DELETE)) {

            CartModel model = (CartModel) data;
            deleteCartItam(model);

        }else if (sender.equals(GlobalConstants.DATA)) {

            ProductModel model = (ProductModel) data;
            addCartItam(model);

        }
    }

    private void addCartItam(ProductModel model) {
        LayoutInflater layoutInflater = (LayoutInflater) OrderDetailsActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view = layoutInflater.inflate(R.layout.edit_item_popup, null);
        Popup popup = new Popup();
        add_popup = popup.dialoglog(OrderDetailsActivity.this, popup_view);
        add_popup.setCancelable(true);
        Button cancel_btn = popup_view.findViewById(R.id.cancel_btn);
        Button btn_positive = popup_view.findViewById(R.id.btn_positive);
        TextView head = popup_view.findViewById(R.id.head);
        EditText content = popup_view.findViewById(R.id.popup_txt);


        head.setText(model.getName());
        content.setText("1") ;
        add_popup.show();
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_popup.dismiss();

            }
        });


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content.getText().toString().equals("0")){
                    Toast.makeText(OrderDetailsActivity.this, "Quantity cannot be zero", Toast.LENGTH_SHORT).show();

                }else {
                    addApi(model,content.getText().toString());
                }

            }
        });
    }

    private void addApi(ProductModel model, String toString) {
        String url = GlobalConstants.BASE_URL + "additam/?token="+user.getApi_tocken()+"&add_product_id="+model.getId()+"&order_id="+orderId+"&newcartquantity="+toString;
        showProgressDialog(true);
        Call<BaseResponse> call = apiService.additam(url);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        add_popup.dismiss();
                        itamdialoge.dismiss();

                        getdata();

                    }else {
                        showProgressDialog(false);

                    }


                } else {
                    showProgressDialog(false);

                    Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });
    }

    private void deleteCartItam(CartModel model) {
        LayoutInflater layoutInflater = (LayoutInflater) OrderDetailsActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view = layoutInflater.inflate(R.layout.reject_popup, null);
        Popup popup = new Popup();
        review_popup = popup.dialoglog(OrderDetailsActivity.this, popup_view);
        review_popup.setCancelable(true);
        Button cancel_btn = popup_view.findViewById(R.id.cancel_btn);
        Button btn_positive = popup_view.findViewById(R.id.btn_positive);
        TextView head = popup_view.findViewById(R.id.head);
        TextView content = popup_view.findViewById(R.id.popup_txt);


        head.setText("Are you sure to DELETE the item");
        content.setText(model.getProduct_name()) ;
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
            ditletApi(model);
            }
        });
    }

    private void ditletApi(CartModel model) {
        String url = GlobalConstants.BASE_URL + "deletecart/?token="+user.getApi_tocken()+"&id="+model.getId();
        showProgressDialog(true);
        Call<BaseResponse> call = apiService.deleteApi(url);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        review_popup.dismiss();
                    getdata();

                    }else {
                        showProgressDialog(false);

                    }


                } else {
                    showProgressDialog(false);

                    Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });
    }

    private void editCartItam(CartModel model) {
        LayoutInflater layoutInflater = (LayoutInflater) OrderDetailsActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

        View popup_view = layoutInflater.inflate(R.layout.edit_item_popup, null);
        Popup popup = new Popup();
        edit_popup = popup.dialoglog(OrderDetailsActivity.this, popup_view);
        edit_popup.setCancelable(true);
        Button cancel_btn = popup_view.findViewById(R.id.cancel_btn);
        Button btn_positive = popup_view.findViewById(R.id.btn_positive);
        TextView head = popup_view.findViewById(R.id.head);
        EditText content = popup_view.findViewById(R.id.popup_txt);


        head.setText(model.getProduct_name());
        content.setText(model.getQuantity()+"") ;
        edit_popup.show();
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_popup.dismiss();

            }
        });


        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content.getText().toString().equals("0")){
                    Toast.makeText(OrderDetailsActivity.this, "Quantity cannot be zero", Toast.LENGTH_SHORT).show();

                }else {
                    model.setQuantity(Integer.parseInt(content.getText().toString()));
                    editApi(model);
                }

            }
        });
    }

    private void editApi(CartModel model) {
        String url = GlobalConstants.BASE_URL + "editcartitam/?token="+user.getApi_tocken()+"&cartid="+model.getId()+"&quantity="+model.getQuantity();
        showProgressDialog(true);
        Call<BaseResponse> call = apiService.editcartitem(url);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                        edit_popup.dismiss();
                        getdata();

                    }else {
                        showProgressDialog(false);

                    }


                } else {
                    showProgressDialog(false);

                    Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(OrderDetailsActivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });
    }
    private void showpopupProduvt(ArrayList<ProductModel> productModels) {
        itamdialoge = ItemDialog.newInstance(this, this,productModels);
        itamdialoge.show(getActivity().getFragmentManager(), "terms");
        showProgressDialog(false);

    }

}
