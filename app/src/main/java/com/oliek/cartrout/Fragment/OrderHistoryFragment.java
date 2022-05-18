package com.oliek.cartrout.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oliek.cartrout.GlobalConstants;
import com.oliek.cartrout.R;
import com.oliek.cartrout.activity.OrderDetailsActivity;
import com.oliek.cartrout.adapters.NewOrderListAdapter;
import com.oliek.cartrout.base.BaseFragment;
import com.oliek.cartrout.callback.RecycleViewItemCallBack;
import com.oliek.cartrout.model.OrderModel;
import com.oliek.cartrout.model.PendingOrderModel;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.responsemodel.OrderHistoryResponseModel;
import com.oliek.cartrout.network.ApiInterface;
import com.oliek.cartrout.network.ApiNetwork;
import com.oliek.cartrout.preference.PreferenceService;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oliek.cartrout.GlobalConstants.TAG;

public class OrderHistoryFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener , RecycleViewItemCallBack {

    LinearLayout llschedule;
    TextView tw_date,empty;
    int day, month, year;
    int myday, myMonth, myYear;
String date;
    RecyclerView recyclerView;

    NewOrderListAdapter newOrderListAdapter;
    private UserModel user;
    private ApiInterface apiService;
    private PreferenceService sh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView=inflater.inflate(R.layout.fragment_order_history, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("");

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("History");

        user=PreferenceService.getInstance(getContext()).getUser();
        empty= (TextView) rootView.findViewById(R.id.empty_layout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
//
        LinearLayoutManager layoutManagerNewsTrending = new LinearLayoutManager(getContext());
        layoutManagerNewsTrending.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManagerNewsTrending);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
//                layoutManagerNewsTrending.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);
        tw_date = (TextView) rootView.findViewById(R.id.date);
        llschedule= (LinearLayout) rootView.findViewById(R.id.ll_date);

        Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date=""+day+"/"+ (month+1)+"/" +  year;
        tw_date.setText(date);
        llschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setShaduldate(year, month, day );
            }
        });
        tw_date.setText(date);
        getorderhistory(date);

        return rootView;
    }

    private void setShaduldate(int year, int month, int day) {


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int syear, int smonth, int sdayOfMonth) {
        myYear = syear;
        myday = sdayOfMonth;
        myMonth = smonth+1;
        Calendar c = Calendar.getInstance();
        date=""+myday+"/"+ myMonth+"/" +  myYear;
        year =myYear;
        month=myMonth-1;
        day =myday;
        tw_date.setText(date);
        getorderhistory(date);
    }

    private void getorderhistory(String date) {
//        user = PreferenceService.getInstance(getActivity()).getUser();
//        apiService = ApiNetwork.getClient().create(ApiInterface.class);
//
//        String url = GlobalConstants.BASE_URL + "order-history/?date="+date+"&user_type=0";
//        showProgressDialog(true);
//
//        Call<OrderHistoryResponseModel> call = apiService.getOrderhistory("Token "+user.getTokan()+"",url+"");
//        call.enqueue(new Callback<OrderHistoryResponseModel>() {
//            @Override
//            public void onResponse(Call<OrderHistoryResponseModel> call, Response<OrderHistoryResponseModel> response) {
//                if (response.isSuccessful()&&response.body()!=null) {
//                    initRecyclerView(response.body().getOrder_list());
//                } else {
//                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
//
//                }
//                showProgressDialog(false);
//
//            }
//            @Override
//            public void onFailure(Call<OrderHistoryResponseModel> call, Throwable t) {
//                Toast.makeText(getContext(), GlobalConstants.NO_INTERNET, Toast.LENGTH_SHORT).show();
//                Log.e(TAG, t.toString());
//                showProgressDialog(false);
//
//            }
//        });

    }
    private void initRecyclerView(ArrayList<OrderModel> arrayList) {
        if (arrayList != null&&arrayList.size()!=0) {
            newOrderListAdapter = new NewOrderListAdapter(getContext(), arrayList, this);
            recyclerView.setAdapter(newOrderListAdapter);
            newOrderListAdapter.notifyDataSetChanged();
            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);


        }else {
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(Object data, Object sender) {
        if (sender.equals(GlobalConstants.VIEW)) {
            OrderModel model = (OrderModel) data;
            Intent mIntent = new Intent(getContext(), OrderDetailsActivity.class);
            mIntent.putExtra("name", "ORDER DETAILS");
            mIntent.putExtra(GlobalConstants.DATA, model);
            startActivity(mIntent);
        }
    }
}