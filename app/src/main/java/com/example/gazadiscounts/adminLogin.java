package com.example.gazadiscounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gazadiscounts.app.AppController;

import org.json.JSONStringer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class adminLogin extends AppCompatActivity {
EditText username_tv;
EditText password_tv;
TextView error_tv;
Button login;
String username;
String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        username_tv = findViewById(R.id.txt_admin_name);
        password_tv = findViewById(R.id.txt_admin_pass);
        login = findViewById(R.id.adminlogin);
        error_tv = findViewById(R.id.errorTv);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = username_tv.getText().toString();
                password = password_tv.getText().toString();
                getJSONString();

            }
        });
    }

    public void getJSONString(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.231/api/adminlogin",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("ok")){
                            Intent intent = new Intent(adminLogin.this,add_market.class);
                            startActivity(intent);
                        }
                        else{ error_tv.setText(response);}




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
                map.put("username", username);
                map.put("password", password);
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
