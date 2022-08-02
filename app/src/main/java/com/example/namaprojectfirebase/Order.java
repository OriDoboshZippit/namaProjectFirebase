package com.example.namaprojectfirebase;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;

public class Order extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.9) , (int) (height*.6));

    }
}
