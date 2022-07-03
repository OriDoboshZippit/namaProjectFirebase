package com.example.namaprojectfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.namaprojectfirebase.ui.ProductAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<Product> productList;
    RecyclerView recyclerView;
    ProductAdapter adapter;

    DatabaseReference dbProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.allItemsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        productList.add(
//                new Product(
//                        "1",
//                        "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
//                        12,
//                        "sjhf"
//
//                ));
//
//        productList.add(
//                new Product(
//                        "1",
//                        "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
//                        12,
//                        "sjhf"
//                ));
//
//        productList.add(
//                new Product(
//                        "1",
//                        "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
//                        12,
//                        "sjhf"
//                ));

        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);


        //SELECT * FROM Products
        dbProducts = FirebaseDatabase.getInstance().getReference("products");

        dbProducts.addListenerForSingleValueEvent(valueEventListener);

        System.out.println("HEYYYY" + dbProducts.toString());


//        productList.add(new Product("12", "The Name of Product", 1231, 311, 1414223565, 321434234,1,"lsdk", "https://firebasestorage.googleapis.com/v0/b/namaprojectfirebase.appspot.com/o/pictures%2Fuser.png?alt=media&token=d176744a-d456-4e76-b134-9a5c73949f89"));
//        listView = (ListView) findViewById(R.id.listview);
//        MyCustomListAdapter adapter = new MyCustomListAdapter(this, R.layout.my_list_item, productList);
//        listView.setAdapter(adapter);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            productList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    productList.add(product);
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