package com.example.gazadiscounts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gazadiscounts.adapters.productAdapter;
import com.example.gazadiscounts.app.AppController;
import com.example.gazadiscounts.models.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class productDetails extends AppCompatActivity {
    String name;
    String description;
    String market_name;
    String catagory;
    String old_price;
    String new_price;
    String image_url;
    String rating;
    ImageView imageView;

    TextView txtoldprice;
    TextView txtratio;
    TextView txtnewprice;
    TextView txtdescription;
    EditText commenttxt;
    Button add_comment;
    Button cart_btn;
    String comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
         imageView = findViewById(R.id.img_detail);

         txtoldprice = findViewById(R.id.product_old_price);
         txtratio = findViewById(R.id.product_ratio);
         txtnewprice = findViewById(R.id.product_new_price);
         txtdescription = findViewById(R.id.product_discription);
        commenttxt = findViewById(R.id.txt_comment);
        add_comment = findViewById(R.id.add_comment);
        cart_btn = findViewById(R.id.add_to_cart);
        getJSONString();
        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 comment = commenttxt.getText().toString();
                addComment();
            }
        });
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    public void getJSONString(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.103/api/product_details",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                             name = jsonObject.getString("name");
                             description= jsonObject.getString("description");
                             market_name= jsonObject.getString("market_name");
                             catagory= jsonObject.getString("catagory");
                             old_price= jsonObject.getString("old_price");
                             new_price= jsonObject.getString("new_price");
                             image_url= jsonObject.getString("image");
                             rating= jsonObject.getString("rating");
                             double ratio = (Double.parseDouble(new_price)/Double.parseDouble(old_price))*100;

                             txtoldprice.setText(old_price);
                             txtratio.setText(ratio+"");
                             txtnewprice.setText(new_price);
                             txtdescription.setText(description);
                            Picasso.get().load(image_url).into(imageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(productDetails.this, response, Toast.LENGTH_SHORT).show();
                        // imageView2.setImageBitmap(utilities.base64toImage(response));



                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productDetails.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            //
//
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", "1");



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


    public void addComment(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.103/api/add_comment",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(productDetails.this, response, Toast.LENGTH_SHORT).show();
                        // imageView2.setImageBitmap(utilities.base64toImage(response));



                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(productDetails.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            //
//
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
//                'content'=>$input['content'],
//                        'product_id'=>$input['product_id'],
//                        'rating'=>$input['rating']
                map.put("content", comment);
                map.put("product_id", "1");
                map.put("rating", "2.5");



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
