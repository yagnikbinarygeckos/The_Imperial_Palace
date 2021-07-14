package com.app.theimperialpalace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.app.theimperialpalace.Network.ApiUtils;
import com.app.theimperialpalace.Network.WebApi;
import com.app.theimperialpalace.OrderDetailsResponse;
import com.app.theimperialpalace.R;
import com.app.theimperialpalace.Utils.SharedPrefsUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

    String order_id;
    private SwipeRefreshLayout swipeRefresh;
    private ProgressDialog progressDialog;
    private RecyclerView rv_orderDetails;
    private TextView tv_orderId,tv_name,tv_orderDate,tv_orderTime,
            tv_orderNo,tv_total,tv_subTotal,tv_offerAmount,tv_address1,tv_address2,tv_landmark,tv_status;
    private ArrayList<OrderDetailsResponse> arrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);




        order_id = getIntent().getStringExtra("order_id");
        init();
        api();
    }
    private void init() {

        swipeRefresh = findViewById(R.id.swipeRefresh);
        rv_orderDetails = findViewById(R.id.rv_orderDetails);

        tv_orderId = findViewById(R.id.tv_orderId);
        tv_name = findViewById(R.id.tv_name);
        tv_orderDate = findViewById(R.id.tv_orderDate);
        tv_orderTime = findViewById(R.id.tv_orderTime);
        tv_orderNo = findViewById(R.id.tv_orderNo);
        tv_total = findViewById(R.id.tv_total);
        tv_subTotal = findViewById(R.id.tv_subTotal);
        tv_offerAmount = findViewById(R.id.tv_offerAmount);
        tv_address1 = findViewById(R.id.tv_address1);
        tv_address2 = findViewById(R.id.tv_address2);
        tv_landmark = findViewById(R.id.tv_landmark);
        tv_status = findViewById(R.id.tv_status);

        progressDialog = new ProgressDialog(OrderDetailsActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
    }
    private void api() {

        progressDialog.show();
        WebApi webApi = ApiUtils.getClient().create(WebApi.class);
        Call<OrderDetailsResponse> call = webApi.order_Details(
                order_id

        );
        call.enqueue(new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1)
                {
                    tv_orderId.setText(response.body().getId());
                    tv_name.setText(response.body().getUserName());
                    tv_orderDate.setText(response.body().getOrderDate());
                    tv_orderTime.setText(response.body().getOrderTime());
                    tv_orderNo.setText(response.body().getOrderNo());
                    tv_total.setText(response.body().getTotalAmount());
                    tv_subTotal.setText(response.body().getSubTotal());
                    tv_offerAmount.setText(response.body().getOfferAmount());
                    tv_address1.setText(response.body().getAddress1());
                    tv_address2.setText(response.body().getAddress2());
                    tv_landmark.setText(response.body().getLandmark());
                    tv_status.setText(response.body().getStatus());
                }
                else
                {
                    Toast.makeText(OrderDetailsActivity.this, "Order Details Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OrderDetailsActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });



    }
}