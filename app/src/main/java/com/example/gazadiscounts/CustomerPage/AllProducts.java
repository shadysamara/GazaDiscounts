package com.example.gazadiscounts.CustomerPage;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.gazadiscounts.CustomProgress;
import com.example.gazadiscounts.R;
import com.example.gazadiscounts.adapters.productAdapter;
import com.example.gazadiscounts.app.AppController;
import com.example.gazadiscounts.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllProducts extends Fragment {

    productAdapter productAdapter;

    RecyclerView rvData;
    ArrayList<Product> data = new ArrayList<>();


    Product product;
    public AllProducts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_all_products, container, false);
        getJSONString();
        rvData = view.findViewById(R.id.rvData);

        RecyclerView.LayoutManager manager =  new GridLayoutManager(getActivity(),2);
        rvData.setLayoutManager(manager);


         return view;
    }


    public void getJSONString() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://192.168.1.103/api/products_display", null, new Response.Listener<JSONArray>() {


            @Override

            public void onResponse(JSONArray response) {

//                Toast.makeText(getActivity(), response.length()+"", Toast.LENGTH_SHORT).show();
                for(int i = 0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String description = jsonObject.getString("description");
                       int market_id = Integer.parseInt(jsonObject.getString("market_id"));
                        String catagory = jsonObject.getString("catagory");
                        double old_price = Double.parseDouble(jsonObject.getString("old_price"));
                        double new_price = Double.parseDouble(jsonObject.getString("new_price"));
                        double rating = Double.parseDouble(jsonObject.getString("rating"));
                        String image_url = jsonObject.getString("image");
                        data.add(new Product(name,old_price,new_price,market_id,catagory,description,image_url,rating));
//


                        CustomProgress.getInstance();
                        //Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                productAdapter = new productAdapter(data,getActivity());
                rvData.setAdapter(productAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }
}
