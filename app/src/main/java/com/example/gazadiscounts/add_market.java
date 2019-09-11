package com.example.gazadiscounts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gazadiscounts.app.AppController;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.HashMap;
import java.util.Map;

public class add_market extends AppCompatActivity implements IPickResult {
    ImageView market_image;
EditText market_name_tv;
EditText market_city_tv;
EditText market_address_tv;
EditText market_phone_tv;
EditText market_pass_tv;
Button add_market;
Spinner catagories;
    Bitmap bitmap;
    String image64;
    String market_name;
    String market_pass;
    String market_city;
    String market_category;
    String market_phone;
    String market_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_market);

        market_image = findViewById(R.id.img_add_market);
        market_name_tv = findViewById(R.id.txt_add_market_name);
        market_pass_tv = findViewById(R.id.txt_add_market_password);
        market_city_tv = findViewById(R.id.txt_add_market_city);
        market_address_tv = findViewById(R.id.txt_add_market_address);
        market_phone_tv = findViewById(R.id.txt_add_market_phone);
        add_market = findViewById(R.id.btn_add_market);
        catagories = findViewById(R.id.txt_add_market_catagory);


        market_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup()).show(add_market.this);

            }
        });

        add_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 market_name = market_name_tv.getText().toString();
                market_pass = market_pass_tv.getText().toString();
                 market_city=market_city_tv.getText().toString();
                 market_category=catagories.getSelectedItem().toString();
                 market_phone=market_phone_tv.getText().toString();
                 market_address=market_address_tv.getText().toString();
                bitmap = ((BitmapDrawable) market_image.getDrawable()).getBitmap();
                image64 = utilities.imagetobase64(bitmap);
                getJSONString();
            }
        });

    }

        public void getJSONString(){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.103/api/market_control",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(add_market.this, response, Toast.LENGTH_SHORT).show();
                            // imageView2.setImageBitmap(utilities.base64toImage(response));



                        }
                    }, new Response.ErrorListener() {



                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(add_market.this, error.toString(), Toast.LENGTH_SHORT).show();

                }
            }) {

                //
//
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("name", market_name);
                    map.put("password", market_pass);
                    map.put("address", market_address);
                    map.put("city", market_city);
                    map.put("phone", market_phone);
                    map.put("category", market_category);
                    map.put("image_base64",image64);


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

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {

            market_image.setImageBitmap(r.getBitmap());

        }else{
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
