package com.app.theimperialpalace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.theimperialpalace.Network.ApiUtils;
import com.app.theimperialpalace.Network.WebApi;
import com.app.theimperialpalace.Utils.SharedPrefsUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfOrderActivity extends AppCompatActivity {

    private RecyclerView rv_ListOfProducts;
    private ImageView img_notification;
    public ArrayList<ListOfOrderResponse.Result> arrayList = new ArrayList<>();

    private ListOfOrderAdapter listOfOrderAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private ProgressDialog progressDialog;
    private EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();


        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                api();
                swipeRefresh.setRefreshing(false);
            }
        });
        listOfOrderAdapter = new ListOfOrderAdapter(ListOfOrderActivity.this,arrayList);
        onClick();
        api();

    }

    private void onClick() {

        img_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListOfOrderActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });



    }

    private void init() {

        rv_ListOfProducts = findViewById(R.id.rv_ListOfProducts);
        img_notification = findViewById(R.id.img_notification);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        et_search = findViewById(R.id.et_search);
        progressDialog = new ProgressDialog(ListOfOrderActivity.this);
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);



    }
    private void api() {

        progressDialog.show();
        WebApi webApi = ApiUtils.getClient().create(WebApi.class);
        Call<ListOfOrderResponse> call = webApi.order_List(
                SharedPrefsUtils.getSharedPreferenceString(getApplicationContext(),SharedPrefsUtils.USER_ID)
        );
        call.enqueue(new Callback<ListOfOrderResponse>() {
            @Override
            public void onResponse(Call<ListOfOrderResponse> call, Response<ListOfOrderResponse> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1)
                {

                    for (int i =0; i < response.body().getResult().size();i++){
                        arrayList.add(response.body().getResult().get(i));
                    }
                    rv_ListOfProducts.setHasFixedSize(true);

                    rv_ListOfProducts.setAdapter(listOfOrderAdapter);
                    rv_ListOfProducts.setNestedScrollingEnabled(true);
                    @SuppressLint("WrongConstant") RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
                    rv_ListOfProducts.setLayoutManager(LayoutManager);
                }
                else
                {
                    Toast.makeText(ListOfOrderActivity.this, "Order Not Found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ListOfOrderResponse> call, Throwable t) {

            }
        });

    }

    void filter(String text){
        ArrayList<ListOfOrderResponse.Result> temp = new ArrayList();
        for(ListOfOrderResponse.Result d: arrayList){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getUserName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                temp.add(d);
            }
        }
        //update recyclerview
        listOfOrderAdapter.updateList(temp);

    }

    }



