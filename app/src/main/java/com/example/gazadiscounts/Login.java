package com.example.gazadiscounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gazadiscounts.MarketPage.MarketPage;
import com.example.gazadiscounts.app.AppController;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
TextView logtv;
TextView error_tv;
EditText market_name_txt;
EditText market_pass_txt;
Button market_login;
String market_name;
String market_password;
SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        logtv = findViewById(R.id.adminLogin);
        market_name_txt = findViewById(R.id.txt_username);
        market_pass_txt = findViewById(R.id.txt_password);
        market_login = findViewById(R.id.btn_login);
        error_tv = findViewById(R.id.errorTv);
        editor = getSharedPreferences("market",MODE_PRIVATE).edit();
        market_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                market_name = market_name_txt.getText().toString();
                market_password = market_pass_txt.getText().toString();
                getJSONString();
            }
        });
        logtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,adminLogin.class);
                startActivity(intent);
            }
        });
    }


    public void getJSONString(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.231/api/marketlogin",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(android.text.TextUtils.isDigitsOnly((response))){
                            int id = Integer.parseInt(response);
                            editor.putInt("market_id",id);
                            editor.apply();
                            Intent intent = new Intent(Login.this, MarketPage.class);

                            startActivity(intent);
                        }

                        else{error_tv.setText(response);}
//




                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(adminLogin.this, error.toString(), Toast.LENGTH_SHORT).show();
                error_tv.setText(error.toString());
            }
        }) {

            //
//
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("username", market_name);
                map.put("password", market_password);
                return map;
            }



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                return map;
            }



        };

        AppController.getInstance().addToRequestQueue(stringRequest);

    }
}

