package com.example.gazadiscounts;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;

public class CustomProgress {


    public static CustomProgress customProgress = null;
    private Dialog mDialog;
    LottieAnimationView mProgressBar;
    RelativeLayout linearLayout;

    public static CustomProgress getInstance() {
        if (customProgress == null) {
            customProgress = new CustomProgress();
        }
        return customProgress;
    }

    public void showProgress(Context context) {


        mDialog= new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.prograss_bar_dialog);
        mDialog.findViewById(R.id.progress_bar2).setVisibility(View.VISIBLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();

    }

    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
