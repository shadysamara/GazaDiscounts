package com.example.gazadiscounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class producrActivity extends AppCompatActivity implements IPickResult {
    ImageView product_image;
    EditText product_name_tv;
    EditText product_desc_tv;

    EditText product_oprice_tv;
    EditText product_nprice_tv;
    EditText product_rating_tv;
    Button add_product;
    Spinner catagories;
    Bitmap bitmap;
    String image64;
    SharedPreferences prefs;
    int market_id;


    String product_name;
    String product_desc;
    String old_price;
    String new_price;
    String product_category;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producr);
        product_image = findViewById(R.id.product_image);
        product_name_tv = findViewById(R.id.product_name);
        product_oprice_tv = findViewById(R.id.product_oprice);
        product_nprice_tv = findViewById(R.id.product_nprice);
        add_product = findViewById(R.id.btn_add_discount);
        catagories = findViewById(R.id.product_category);
        product_desc_tv= findViewById(R.id.product_description);
        prefs = getSharedPreferences("market",MODE_PRIVATE);

        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup()).show(producrActivity.this);

            }
        });

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product_name = product_name_tv.getText().toString();
                product_desc = product_desc_tv.getText().toString();
                old_price = product_oprice_tv.getText().toString();
                new_price=product_nprice_tv.getText().toString();
                product_category=catagories.getSelectedItem().toString();
                bitmap = ((BitmapDrawable) product_image.getDrawable()).getBitmap();
                image64 = utilities.imagetobase64(bitmap);
                market_id = prefs.getInt("market_id",0);
                getJSONString();
            }
        });
    }



    public void getJSONString(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.103/api/product_control",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(producrActivity.this, response, Toast.LENGTH_SHORT).show();
                        // imageView2.setImageBitmap(utilities.base64toImage(response));



                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(producrActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            //
//
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", product_name);
                map.put("old_price", old_price);
                map.put("new_price", new_price);
                map.put("market_id", String.valueOf(market_id));
                map.put("category", product_category);
                map.put("description", product_desc);
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

            product_image.setImageBitmap(r.getBitmap());

        }else{
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
