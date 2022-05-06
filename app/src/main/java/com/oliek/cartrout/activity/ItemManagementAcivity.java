package com.oliek.cartrout.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.adapters.CategoryListAdapter;
import com.oliek.cartrout.adapters.ProductListAdapter;
import com.oliek.cartrout.base.BaseActivity;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.dialogue.DialogCategoryEdit;
import com.oliek.cartrout.model.CategoryModel;
import com.oliek.cartrout.model.PendingOrderModel;
import com.oliek.cartrout.model.ProductModel;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.base.BaseResponse;
import com.oliek.cartrout.model.responsemodel.CategoriesResponseModel;
import com.oliek.cartrout.model.responsemodel.OrderCountResponseModel;
import com.oliek.cartrout.model.responsemodel.ProductsResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;
import com.oliek.cartrout.utill.PaginationScrollListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oliek.cartrout.GlobalConstants.KEY_ACTVE;
import static com.oliek.cartrout.GlobalConstants.KEY_INACTVE;
import static com.oliek.cartrout.GlobalConstants.TAG;


public class ItemManagementAcivity extends BaseActivity implements View.OnClickListener , RecycleViewItemCallBack {

    public static final String MyPref="info";
    private String key;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    DialogCategoryEdit dialogCategoryEdit;
    private String NEXT_URL ;
    private int TOTAL_PAGES ;

    private int currentPage = PAGE_START;

    LinearLayout lyt_home,lyt_orders,lyt_menu;
    TextView txt_ordercount,txt_category;

    RelativeLayout main_content;
    LinearLayout no_connection;
    Button btn_tryagain;
    private String titil;
    private Button[] btn = new Button[3];
    private Button btn_unfocus;

    String searchKey="",category,categorySlug,status="";
    EditText edt_search;
    ImageView btn_search,cat_edit;
    CategoryModel model;
    ProductListAdapter productListAdapter;
    private ArrayList<ProductModel> arrayListfull;
    private ArrayList<ProductModel> serch=new ArrayList<>();
    private PreferenceService sh;
    private ApiInterface apiService;
    private UserModel user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_management_acivity);
        user = PreferenceService.getInstance(this).getUser();
        apiService = ApiNetwork.getClient().create(ApiInterface.class);
//        titil = Objects.requireNonNull(getIntent().getExtras()).getString("name");
        model = (CategoryModel) getIntent().getSerializableExtra(GlobalConstants.DATA);
        this.setTitle("Item Management");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        if(user.getUser_type()==4){
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    getApplicationContext());
            builder.setCancelable(true);
            builder.setTitle("user not authorized to this page");
            builder.setInverseBackgroundForced(true);
            builder.setPositiveButton("ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            onBackPressed();
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        lyt_orders=findViewById(R.id.lyt_orders);
        lyt_home=findViewById(R.id.lyt_home);
        lyt_menu=findViewById(R.id.lyt_menu);
        txt_ordercount=findViewById(R.id.txt_ordercount);
        main_content=findViewById(R.id.main_content);
        no_connection=findViewById(R.id.no_connection);
        btn_tryagain=findViewById(R.id.btn_tryagain);
        btn_search=findViewById(R.id.btn_search);
        cat_edit=findViewById(R.id.cat_edit);

        edt_search=findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                serch.clear();
                serch.addAll(arrayListfull);
                if (s.toString().length()==0) {
                    serch.clear();
                    serch.addAll(arrayListfull);
                }else {
                    serch.clear();
                    for (int i = 0; i < arrayListfull.size(); i++) {

                        String name= arrayListfull.get(i).getName();
                        String dd= arrayListfull.get(i).getPrice()+"";
                        String pris= arrayListfull.get(i).getOffer_price()+"";

                        if (name.toLowerCase().contains(s.toString().toLowerCase())||dd.toLowerCase().contains(s.toString().toLowerCase())||pris.toLowerCase().contains(s.toString().toLowerCase())) {
                            serch.add(arrayListfull.get(i));
                        }

                    }
                }
                initRecyclerView(serch);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txt_category=findViewById(R.id.txt_category);
        btn_search.setOnClickListener(this);
        lyt_orders.setOnClickListener(this);
        lyt_home.setOnClickListener(this);
        lyt_menu.setOnClickListener(this);
        cat_edit.setOnClickListener(this);
        txt_category.setText(model.getName());

        recyclerView = (RecyclerView) findViewById(R.id.rec_menumanage);
        LinearLayoutManager layoutManagerNewsTrending = new LinearLayoutManager(this);
        layoutManagerNewsTrending.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManagerNewsTrending);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                layoutManagerNewsTrending.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);



        btn_tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrderCount();
                no_connection.setVisibility(View.GONE);
            }
        });



        getItemList();
        getOrdercount();
    }
    private void setOrderCount(int count ) {
        if (count>0){
            Animation shake;
            shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
            lyt_orders.startAnimation(shake);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(ItemManagementAcivity.this, R.anim.bounce_reverse_count);
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
                        Toast.makeText(ItemManagementAcivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(ItemManagementAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderCountResponseModel> call, Throwable t) {
                Toast.makeText(ItemManagementAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });



    }
    private void getItemList() {

        String url = GlobalConstants.BASE_URL + "getproduct/?token="+user.getApi_tocken()+"&web_category_id="+model.getId();
        showProgressDialog(true);
        Call<ProductsResponseModel> call = apiService.getproduct(url);
        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call, Response<ProductsResponseModel> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){
                    arrayListfull=response.body().getProducts();
                        initRecyclerView(response.body().getProducts());

                    }else {
                        Toast.makeText(ItemManagementAcivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        showProgressDialog(false);
                    }


                } else {
                    Toast.makeText(ItemManagementAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                }
            }

            @Override
            public void onFailure(Call<ProductsResponseModel> call, Throwable t) {
                Toast.makeText(ItemManagementAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                showProgressDialog(false);

            }
        });

    }
    private void initRecyclerView(ArrayList<ProductModel> arrayList) {
        if (arrayList != null&&arrayList.size()!=0) {
            productListAdapter = new ProductListAdapter(this, arrayList, this);
            recyclerView.setAdapter(productListAdapter);
            productListAdapter.notifyDataSetChanged();
//            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            showProgressDialog(false);



        }else {
//            empty.setVisibility(View.VISIBLE);
            showProgressDialog(false);
            recyclerView.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()){
            case R.id.lyt_home:
                i=new Intent(ItemManagementAcivity.this, NavigationActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;

            case R.id.lyt_orders:
                i=new Intent(ItemManagementAcivity.this, NewOrderActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;
            /*case R.id.lyt_menu:
                i=new Intent(ItemManagementAcivity.this, MenuManagement.class);
                startActivity(i);
                overridePendingTransition(R.anim.page_in,R.anim.page_out);

                break;*/

            case R.id.cat_edit:
                showcat_editpopup();
                break;
        }
    }
    private void showcat_editpopup() {
        dialogCategoryEdit = DialogCategoryEdit.newInstance(this,model);
        dialogCategoryEdit.setCancelable(false);
        dialogCategoryEdit.show(getActivity().getFragmentManager(), "terms");

    }
    @Override
    protected void showPopUp() {

    }






    private void getOrderCount() {
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
                        Toast.makeText(ItemManagementAcivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(ItemManagementAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderCountResponseModel> call, Throwable t) {
                Toast.makeText(ItemManagementAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });
    }

    private void itemStatus(int itemID) {

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
    public void onItemClick(Object data, Object sender) {
        if (sender.equals(GlobalConstants.DATA)) {
            CategoryModel model = (CategoryModel) data;
            updatecatagory(model);
        }
        else if (sender.equals("Closs")) {
            dialogCategoryEdit.dismiss();
        }


        if (sender.equals(GlobalConstants.VIEW)) {

            ProductModel model = (ProductModel) data;
            Intent i=new Intent(ItemManagementAcivity.this, ProductActivity.class);
            i.putExtra("name", "ORDER DETAILS");
            i.putExtra(GlobalConstants.DATA, model);
            startActivity(i);
            overridePendingTransition(R.anim.page_in,R.anim.page_out);

        }
    if (sender.equals(GlobalConstants.KEY_ACTVE)) {

        ProductModel model = (ProductModel) data;
        productstatusedit(model.getId(),1);
    }
        if (sender.equals(GlobalConstants.KEY_INACTVE)) {

            ProductModel model = (ProductModel) data;
            productstatusedit(model.getId(),0);

    }
}

    private void updatecatagory(CategoryModel categoryModel) {

            String url = GlobalConstants.BASE_URL + "categoryedit/?token="+user.getApi_tocken()+"&id="+categoryModel.getId()+"&name="+categoryModel.getName()+"&order="+categoryModel.getOrder()+"&a_name="+categoryModel.getA_name();
            showProgressDialog(true);
            Call<BaseResponse> call = apiService.categorysedit(url);
            call.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.isSuccessful()&&response.body()!=null) {
                        if(response.body().isSuccess()){
                            model=categoryModel;
                            txt_category.setText(model.getName());

                        }else {
                            Toast.makeText(ItemManagementAcivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    } else {
                        Toast.makeText(ItemManagementAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    }
                    dialogCategoryEdit.dismiss();
                    showProgressDialog(false);

                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toast.makeText(ItemManagementAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, t.toString());
                    dialogCategoryEdit.dismiss();
                    showProgressDialog(false);

                }
            });


    }

    private void productstatusedit(int id, int i) {
        String url = GlobalConstants.BASE_URL + "productstatusedit/?token="+user.getApi_tocken()+"&id="+id+"&status="+i;
        showProgressDialog(true);
        Call<BaseResponse> call = apiService.productstatusedit(url);
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()&&response.body()!=null) {
                    if(response.body().isSuccess()){

                    }else {
                        Toast.makeText(ItemManagementAcivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Toast.makeText(ItemManagementAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                }
                getItemList();

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(ItemManagementAcivity.this, GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
                getItemList();

            }
        });



    }
}