package com.example.gazadiscounts.MarketPage;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.gazadiscounts.R;
import com.example.gazadiscounts.adapters.productAdapter;
import com.example.gazadiscounts.app.AppController;
import com.example.gazadiscounts.models.Product;
import com.example.gazadiscounts.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {
    productAdapter productAdapter;

    RecyclerView rvData;
    ArrayList<Product> data = new ArrayList<>();

    int market_id;


    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        getJSONString();
        rvData = view.findViewById(R.id.rvData);

        RecyclerView.LayoutManager manager =  new GridLayoutManager(getActivity(),2);
        rvData.setLayoutManager(manager);
        return view;
    }



    public void getJSONString(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, utilities.dispayurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // imageView2.setImageBitmap(utilities.base64toImage(response));

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            //Toast.makeText(getActivity(), jsonArray.getJSONObject(0).getString("name"), Toast.LENGTH_SHORT).show();

                           for (int i=0; i<jsonArray.length();i++){
                               JSONObject jsonObject = jsonArray.getJSONObject(i);
                               String name = jsonObject.getString("name");
                               String description = jsonObject.getString("description");
                               int image_id = Integer.parseInt(jsonObject.getString("image_id"));
                               String catagory = jsonObject.getString("catagory");
                               double old_price = Double.parseDouble(jsonObject.getString("old_price"));
                               double new_price = Double.parseDouble(jsonObject.getString("new_price"));
                               double rating = Double.parseDouble(jsonObject.getString("rating"));
                               String image_url = jsonObject.getString("image");
                               data.add(new Product(name,old_price,new_price,market_id,catagory,description,image_url,rating));
//

                           }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        productAdapter = new productAdapter(data,getActivity());
                        rvData.setAdapter(productAdapter);



                    }
                }, new Response.ErrorListener() {



            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {

            //
//
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();

                map.put("market_id", String.valueOf(0));



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
