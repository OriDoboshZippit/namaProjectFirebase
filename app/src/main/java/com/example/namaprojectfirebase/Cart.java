package com.example.namaprojectfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.namaprojectfirebase.ui.ProductAdapter;
import com.example.namaprojectfirebase.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Cart extends AppCompatActivity {

    List<Product> productList;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    public ImageButton addToCart;


    DatabaseReference dbProducts,addToCartDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.allItemsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        imageView = findViewById(R.id.imageView);
//        Glide.with(this).load("").into(imageView);

        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        //SELECT * FROM Products
        dbProducts = FirebaseDatabase.getInstance().getReference("carts").child(HomeFragment.uniqueOfCartID);
        dbProducts.addListenerForSingleValueEvent(valueEventListener);





//        addToCart = (ImageButton) findViewById(R.id.addToCardRecycle);
//        String ID_Cart = addToCartDb.push().getKey();



//        addToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("HEYYY");
////                HashMap<String, String> parameters = new HashMap<>();
////                parameters.put("product_name","apple");
////                parameters.put("price", "20");
////                addToCartDb.child(ID_Cart).setValue(parameters);
//
//            }
//        });


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            productList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    productList.add(product);
                    System.out.println(" NAME " + product.toString());
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}


//take picture from URL function (don't checked);
//    public static Drawable LoadImageFromWebOperations(String url) {
//        try {
//            InputStream is = (InputStream) new URL(url).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");
//            return d;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//}