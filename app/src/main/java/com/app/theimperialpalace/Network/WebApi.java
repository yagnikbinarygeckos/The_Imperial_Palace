package com.app.theimperialpalace.Network;

import com.app.theimperialpalace.ListOfOrderResponse;
import com.app.theimperialpalace.LoginResponse;
import com.app.theimperialpalace.NotificationResponse;
import com.app.theimperialpalace.OrderDetailsResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface WebApi {


    @FormUrlEncoded
    @POST("imp/apis/hotel/auth/hotel_login")
    Call<LoginResponse> add_user(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("device_id") String device_id);
    @FormUrlEncoded
    @POST("imp/apis/hotel/orders/user_pending_orders")
    Call<ListOfOrderResponse> order_List(
                                        @Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("imp/apis/hotel/orders/user_pending_orders")
    Call<ListOfOrderResponse.ProductDetail> order_List_details(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("imp/apis/hotel/orders/my_notification")
    Call<NotificationResponse> notification(
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("imp/apis/hotel/orders/get_order_details")
    Call<OrderDetailsResponse> order_Details(
            @Field("order_id") String order_id);

}
