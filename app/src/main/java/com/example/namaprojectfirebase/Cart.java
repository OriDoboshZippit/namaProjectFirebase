package com.example.namaprojectfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.ImageButton;

import com.example.namaprojectfirebase.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Cart extends AppCompatActivity {


    RecyclerView recyclerView;
    CartProductAdapter adapter;
    List<Product> productList;
    DatabaseReference dbProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        productList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.allItemsRecyclerViewCart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        imageView = findViewById(R.id.imageView);
//        Glide.with(this).load("").into(imageView);

        adapter = new CartProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);


        //SELECT * FROM Products
        dbProducts = FirebaseDatabase.getInstance().getReference("carts").child(HomeFragment.uniqueOfCartID);
        dbProducts.addListenerForSingleValueEvent(valueEventListener);

        System.out.println("THE CARD IS NUM" + HomeFragment.uniqueOfCartID);



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
                    System.out.println("THE PRODUCTS" + snapshot);
                    if (snapshot.getKey().equals("currentUserEmail") ||snapshot.getKey().equals("orderPlaced")){
                        System.out.println("IT IS THE WRON KEY");
                    }
                    else{
                    Product product = snapshot.getValue(Product.class);
                    productList.add(product);
                    System.out.println(" NAME " + product.toString());
                    }
//
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