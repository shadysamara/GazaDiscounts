package com.example.gazadiscounts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;

public class utilities {
    public static String ip = "http://192.168.1.103/api/";

    public static String dispayurl = "http://192.168.1.103/api/product_display";
    public static String createurl = "http://192.168.1.103/api/product_control";

    public static String imagetobase64(Bitmap bm){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }


    public static Bitmap base64toImage(String base64){
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;


    }
}
