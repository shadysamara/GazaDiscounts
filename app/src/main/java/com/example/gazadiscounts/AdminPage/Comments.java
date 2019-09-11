package com.example.gazadiscounts.AdminPage;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.gazadiscounts.R;
import com.example.gazadiscounts.adapters.commentAdapter;
import com.example.gazadiscounts.adapters.marketAdapter;
import com.example.gazadiscounts.adapters.productAdapter;
import com.example.gazadiscounts.app.AppController;
import com.example.gazadiscounts.models.Comment;
import com.example.gazadiscounts.models.Product;
import com.example.gazadiscounts.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Comments extends Fragment {

    commentAdapter commentAdapter;

    RecyclerView rvData;
    ArrayList<Comment> data = new ArrayList<>();


    public Comments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments, container, false);
        unApprovedComments();
        rvData = view.findViewById(R.id.rvData);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        rvData.setLayoutManager(manager);
        return view;
    }


    public void unApprovedComments(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, utilities.ip + "unApproved_comment", null, new Response.Listener<JSONArray>() {
            JSONObject jsonObject;
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        int isActive = jsonObject.getInt("isActive");
                        int product_id = jsonObject.getInt("product_id");
                        String content = jsonObject.getString("content");
                        String product_name = jsonObject.getString("product_name");
                        double rating = jsonObject.getDouble("rating");
                        data.add(new Comment(id,content,isActive,rating,product_id,product_name));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                commentAdapter = new commentAdapter(data,getActivity());
                rvData.setAdapter(commentAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

}
