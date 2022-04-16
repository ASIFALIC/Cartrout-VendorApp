package com.oliek.cartrout.network;



import com.oliek.cartrout.model.OrderImage;
import com.oliek.cartrout.model.base.BaseResponse;
import com.oliek.cartrout.model.responsemodel.CategoriesResponseModel;
import com.oliek.cartrout.model.responsemodel.ComplaintTypeResponsModel;
import com.oliek.cartrout.model.responsemodel.CreditResponseModel;
import com.oliek.cartrout.model.responsemodel.DashboardResponseModel;
import com.oliek.cartrout.model.responsemodel.EarningsResponseModel;
import com.oliek.cartrout.model.responsemodel.KmchaingeResponseModel;
import com.oliek.cartrout.model.responsemodel.LoginResponseModel;
import com.oliek.cartrout.model.responsemodel.OrderCountResponseModel;
import com.oliek.cartrout.model.responsemodel.OrderHistoryResponseModel;
import com.oliek.cartrout.model.responsemodel.OrderListResponseModel;
import com.oliek.cartrout.model.responsemodel.OrderViewResponseModel;
import com.oliek.cartrout.model.responsemodel.ProductsResponseModel;
import com.oliek.cartrout.model.responsemodel.StaffDashResponseModel;
import com.oliek.cartrout.model.responsemodel.StatusResponseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST("get-pin")
    Call<BaseResponse> getpin(@Body RequestBody requestBody);

    @GET()
    Call<LoginResponseModel> tokenupdateandstatus(@Url String url);

    @GET()
    Call<LoginResponseModel> getLogin(@Url String url);

    @GET()
    Call<DashboardResponseModel> getdashboard(@Url String url);

    @GET()
    Call<CategoriesResponseModel> getcategories(@Url String url);

    @GET()
    Call<BaseResponse> categorystatusedit(@Url String url);

    @GET()
    Call<BaseResponse> categorysedit(@Url String url);

    @GET()
    Call<ProductsResponseModel> getproduct(@Url String url);

    @GET()
    Call<BaseResponse> productstatusedit(@Url String url);

    @GET()
    Call<OrderListResponseModel> getorderlist(@Url String url);

    @GET()
    Call<OrderCountResponseModel> getordercount(@Url String url);

    @GET()
    Call<OrderViewResponseModel> getorderdetail(@Url String url);

    @GET()
    Call<OrderViewResponseModel> orderstatuschange(@Url String url);

    @GET()
    Call<LoginResponseModel> logout(@Url String url);

    @GET()
    Call<BaseResponse> updateBusyStatus(@Url String url);

    @GET()
    Call<BaseResponse> updateStatusopen(@Url String url);

    @GET()
    Call<BaseResponse> deleteApi( @Url String url);

    @GET()
    Call<BaseResponse> editcartitem( @Url String url);

    @GET()
    Call<BaseResponse> additam( @Url String url);

    @GET()
    Call<ProductsResponseModel> getproductnactiv( @Url String url);



    @POST("fb-token/")
    Call<BaseResponse> getfbtupdate(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    @GET()
    Call<OrderViewResponseModel> getOrderView(@Header("Authorization") String authorization, @Url String url);



    @GET("staff-app-dash/")
    Call<StaffDashResponseModel> getAppDash(@Header("Authorization") String authorization);

    @POST("start-service/")
    Call<BaseResponse> start_service(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    @POST("staff-location-change/")
    Call<BaseResponse> refreshLocation(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    @Multipart
    @POST("order-image/")
    Call<OrderImage> loadImage(@Header("Authorization") String authorization,
                               @Part MultipartBody.Part image,
                               @Part("order") RequestBody order);

    @POST("order-status/")
    Call<StatusResponseModel> orderstatus(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    @GET()
    Call<OrderHistoryResponseModel> getOrderhistory(@Header("Authorization") String s, @Url String s1);

    @GET()
    Call<CreditResponseModel> getCeadithistory(@Header("Authorization") String s, @Url String s1);

    @GET()
    Call<EarningsResponseModel> getstaffearnings(@Header("Authorization") String s, @Url String s1);

    @POST("kilometer-change/")
    Call<KmchaingeResponseModel> kmchainge(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    @GET("complaint-types")
    Call<ComplaintTypeResponsModel> getcomplainttypes(@Header("Authorization") String authorization);

    @POST("complaint/")
    Call<KmchaingeResponseModel> submitcomplaint(@Header("Authorization") String authorization, @Body RequestBody requestBody);


}
