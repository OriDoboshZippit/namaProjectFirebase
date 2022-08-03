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
    public static DatabaseReference orderDbSnap,allProducts;
    public int count = 0,flagRunningCart =0,valueUpdated=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
        Button getOrder = (Button) findViewById(R.id.markOrderRecieved);

        Query orderQueryCopyAllToOrders, updateQuantityFromGlobal;

        orderDbSnap = FirebaseDatabase.getInstance().getReference("carts").child(HomeFragment.uniqueOfCartID);
        orderQueryCopyAllToOrders = orderDbSnap.orderByKey();


        allProducts = FirebaseDatabase.getInstance().getReference("products");
        updateQuantityFromGlobal = allProducts.orderByKey();

        List productsInCartNameQuantity = new ArrayList();

        getOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count = 0;
                productsInCartNameQuantity.clear();
                flagRunningCart = 0;
                orderQueryCopyAllToOrders.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        System.out.println("CLICK BUTTON START RUNNING FUNC BEFORE FOR");
                    if(flagRunningCart==0){
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //LIST FROM CART
                            productsInCartNameQuantity.add(postSnapshot.getKey());
                            productsInCartNameQuantity.add(String.valueOf(postSnapshot.child("quantity").getValue()));
//                            System.out.println("The values in list" + productsInCartNameQuantity);
//                            System.out.println("COUNT IS" + count);
                            count++;
//                            System.out.println("COUNT IS : "+ count);
                        }
                        flagRunningCart = 1;
                    }
                    else{
//                        System.out.println("the flagRunningCart is : " +flagRunningCart);
                    }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

//                if(count > 2){
                    Cart.dbProducts.child("orderPlaced").setValue(1);
//                }
//                else
//                {
//                    AlertDialog alertDialog = new AlertDialog.Builder(Order.this).create();
//                    alertDialog.setTitle("Attention");
//                    alertDialog.setMessage("You need to add some products");
//                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK, I WILL DO IT",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alertDialog.show();
//                }

//COMMENT START NEEDED FUNC OF RUNNING OVERALL PRODUCTS
//RUNNING ON ALL PRODUCTS
                valueUpdated = 0;
                updateQuantityFromGlobal.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(valueUpdated==0){
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

//                            System.out.println("Name from snap allProducts " + postSnapshot.child("nameOfProduct").getValue());
                            for(int i = 0; i < productsInCartNameQuantity.size(); i++){
//                               System.out.println("Data in list on i place :" +i +" "+productsInCartNameQuantity.get(i).toString());
                               if (postSnapshot.child("nameOfProduct").getValue().equals(productsInCartNameQuantity.get(i).toString())) {
//                                        System.out.println("the names the same in if");
                                        int overallQnty = Integer.parseInt(postSnapshot.child("quantity").getValue().toString());
                                        int productQnty = Integer.parseInt(productsInCartNameQuantity.get(i+1).toString());
//                                            System.out.println("OVERAL QNTY FROM DATABASE INT CASTED " + overallQnty);
//                                            System.out.println("QNTY OF PRODUCT FROM LIST INT CASTED " + productQnty);
//                                            System.out.println("NAME IN LIST ON THIS I PLACE IN IF " + productsInCartNameQuantity.get(i).toString());
//                                            System.out.println("QUANTITY IN LIST ON THIS I+1 PLACE IN IF " + productsInCartNameQuantity.get(i+1).toString());
                                            int result = overallQnty -productQnty;
//                                            System.out.println("RESULT" + result);


                                            postSnapshot.getRef().child("quantity").setValue(result);


                                }

                            }
                            }
                        }
                        valueUpdated = 1;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.95) , (int) (height*.8));

    }
}