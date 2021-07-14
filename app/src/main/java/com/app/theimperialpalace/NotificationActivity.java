package com.app.theimperialpalace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.theimperialpalace.Network.ApiUtils;
import com.app.theimperialpalace.Network.WebApi;
import com.app.theimperialpalace.Utils.SharedPrefsUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {


    RecyclerView rv_notifications;
    private  ProgressDialog progressDialog;
    private ArrayList<NotificationResponse.Result> arrayList = new ArrayList<NotificationResponse.Result>();
    private NotificationAdapter notificationAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
        api();
    }

    private void init() {

        rv_notifications = findViewById(R.id.rv_notifications);
        progressDialog = new ProgressDialog(NotificationActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

    }
    private void api() {

        progressDialog.show();
        WebApi webApi = ApiUtils.getClient().create(WebApi.class);
        Call<NotificationResponse> call = webApi.notification(
                SharedPrefsUtils.getSharedPreferenceString(getApplicationContext(),SharedPrefsUtils.USER_ID)
        );
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1)
                {
                    progressDialog.dismiss();
                    for (int i =0; i < response.body().getResult().size();i++){
                        arrayList.add(response.body().getResult().get(i));
                    }
                    rv_notifications.setHasFixedSize(true);
                    notificationAdapter = new NotificationAdapter(arrayList);
                    rv_notifications.setAdapter(notificationAdapter);
                    rv_notifications.setNestedScrollingEnabled(true);
                    @SuppressLint("WrongConstant") RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL,false);
                    rv_notifications.setLayoutManager(LayoutManager);
                }

                else
                {
                    Toast.makeText(NotificationActivity.this, "Notification Not Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    progressDialog.dismiss();
                Toast.makeText(NotificationActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}