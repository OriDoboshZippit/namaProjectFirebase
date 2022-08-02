package com.example.namaprojectfirebase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.namaprojectfirebase.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Order extends Activity {
    public static DatabaseReference orderDbSnap;
    public int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
        Button getOrder = (Button) findViewById(R.id.markOrderRecieved);

        Query orderQueryCopyAllToOrders;

        orderDbSnap = FirebaseDatabase.getInstance().getReference("carts").child(HomeFragment.uniqueOfCartID);
        orderQueryCopyAllToOrders = orderDbSnap.orderByKey();








        getOrder.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                count = 0;
                orderQueryCopyAllToOrders.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> products = new ArrayList<String>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            System.out.println("The values in cart is " + postSnapshot.getKey());
                            count++;
                        }



                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


                if(count > 2){
                    Cart.dbProducts.child("orderPlaced").setValue(1);
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(Order.this).create();
                    alertDialog.setTitle("Attention");
                    alertDialog.setMessage("You need to add some products");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK, I WILL DO IT",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

                System.out.println("Place order with all info of user that order it");
            }
        });










        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.95) , (int) (height*.8));

    }
}
