package com.example.namaprojectfirebase;

import static com.example.namaprojectfirebase.Login.mAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.namaprojectfirebase.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Cart extends AppCompatActivity {


    RecyclerView recyclerView;
    CartProductAdapter adapter;
    List<Product> productList;
    static DatabaseReference dbProducts;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    Button removeOrderBtn,placeOrderBtn;
    public static int sum, inCartFlag;
    public TextView sumTotal;


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


        //SELECT * FROM Carts
        dbProducts = FirebaseDatabase.getInstance().getReference("carts").child(HomeFragment.uniqueOfCartID);
        dbProducts.addListenerForSingleValueEvent(valueEventListener);

        //DELETE CART * From Products
        System.out.println("THE CARD IS NUM" + HomeFragment.uniqueOfCartID);
        removeOrderBtn = (Button) findViewById(R.id.removeOrderBtn);
        placeOrderBtn = (Button) findViewById(R.id.placeOrderBtn);
        sumTotal = findViewById(R.id.sumTotal);
        removeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("HEYYY REMOVE");
                System.out.println("TRY TO REMOVE" + dbProducts.child("orderPlaced").setValue(2));

                // create cart
                HomeFragment.createCartFuncUnique(mAuth.getCurrentUser().getEmail());
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("HEYYY I PLACE THE ORDER");
                System.out.println("TRY TO PLACE" + dbProducts.child("orderPlaced").setValue(1));

                // create cart
                HomeFragment.createCartFuncUnique(mAuth.getCurrentUser().getEmail());
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }
///
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            productList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    inCartFlag = 0;
                    System.out.println("THE PRODUCTS" + snapshot);
                    if (snapshot.getKey().equals("currentUserEmail") || snapshot.getKey().equals("orderPlaced")){
                        System.out.println("IT IS THE WRONG KEY");
                    }
                    else{
                    Product product = snapshot.getValue(Product.class);
//UPDATE QUANTITY//
                    for(int i = 0; i < productList.size(); i ++){
                        System.out.println("RUN ON " +   productList.get(i).getNameOfProduct());
                        if (productList.get(i).getNameOfProduct().equals(product.getNameOfProduct())){
                                productList.get(i).setQuantity(productList.get(i).getQuantity() + ProductAdapter.valueQnty);

                                System.out.println("THE NAME IS SAME " +  dataSnapshot.getValue());
//                                product.setQuantity(productList.get(i).getQuantity() + ProductAdapter.valueQnty);
                            inCartFlag = 1;
                        }
                    }
                    if(inCartFlag == 0){
                        System.out.println("I add product to list!!!");
                        productList.add(product);
                    }
                    else {
//                        product.setQuantity(productList.get(i).getQuantity() + ProductAdapter.valueQnty);
                        System.out.println("Quantity Updated " + ProductAdapter.valueQnty);
                    }
//                    System.out.println(productList.get(0).getNameOfProduct());

                    System.out.println(" PRODUCTS LIST " + product.getNameOfProduct());
                    }
                }
// THE TOTAL
                sum = 0;
                for(int i = 0; i < productList.size(); i ++){
                    System.out.println("Product name: " + productList.get(i).getNameOfProduct() + " The sum price of this product " + productList.get(i).getQuantity()*productList.get(i).getBuyPrice());
                    sum += productList.get(i).getQuantity()*productList.get(i).getBuyPrice();
                    System.out.println(sum + "the sum");

                    sumTotal.setText("TOTAL FOR THIS ORDER: " + sum);
                }
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}

