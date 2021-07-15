package com.app.theimperialpalace;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.theimperialpalace.Network.ApiUtils;
import com.app.theimperialpalace.Network.WebApi;
import com.app.theimperialpalace.Utils.SharedPrefsUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

        private TextInputLayout ett_EmailPhone,ett_Password;
        private TextInputEditText et_EmailPhone,et_Password;
        private Button btn_login;
         private ProgressDialog progressDialog;
    String device_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        device_id = SharedPrefsUtils.getSharedPreferenceString(getApplicationContext(),"token_new");


        init();
        onClick();
    }

    private void init() {

        et_EmailPhone = findViewById(R.id.et_EmailPhone);
        et_Password = findViewById(R.id.et_Password);
        ett_EmailPhone = findViewById(R.id.ett_EmailPhone);
        ett_Password = findViewById(R.id.ett_Password);
        btn_login = findViewById(R.id.btn_login);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");


    }
    private void onClick() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    validation();
            }
        });

    }

    private void validation() {

        if(et_EmailPhone.getText().toString().isEmpty())
        {
            ett_EmailPhone.setError("Enter Your Emaill Address / Phone Number");
            ett_EmailPhone.setFocusable(true);
        }
        else if(et_Password.getText().toString().isEmpty())
        {
            ett_Password.setError("Enter Your Password");
            ett_Password.setFocusable(true);
        }
        else
        {
            api();
        }

    }

    private void api() {

        progressDialog.show();
        WebApi webApi = ApiUtils.getClient().create(WebApi.class);
        Call<LoginResponse> call = webApi.add_user(
                et_EmailPhone.getText().toString(),
                et_Password.getText().toString(),
                device_id
        );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1)
                {

                    Intent intent = new Intent(LoginActivity.this,ListOfOrderActivity.class);
                    SharedPrefsUtils.setSharedPreferenceString(getApplicationContext(), SharedPrefsUtils.USER_ID,response.body().getUserId().toString());
                    SharedPrefsUtils.setSharedPreferenceString(getApplicationContext(),SharedPrefsUtils.EMAIL,response.body().getEmail().toString());
                    SharedPrefsUtils.setSharedPreferenceString(getApplicationContext(),SharedPrefsUtils.PASSWORD,et_Password.getText().toString());
                    SharedPrefsUtils.setSharedPreferenceString(getApplicationContext(),"login","1");
                    intent.putExtra("t_profile","0");
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Check Your Email Address And Password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}