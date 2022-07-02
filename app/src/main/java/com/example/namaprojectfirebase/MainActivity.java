package com.example.namaprojectfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List <Product> productList;
        ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productList = new ArrayList<>();
        productList.add(new Product("12", "The Name of Product", 1231, 311, 1414223565, 321434234,1,"lsdk", "https://firebasestorage.googleapis.com/v0/b/namaprojectfirebase.appspot.com/o/pictures%2Fuser.png?alt=media&token=d176744a-d456-4e76-b134-9a5c73949f89"));
        listView = (ListView) findViewById(R.id.listview);
        MyCustomListAdapter adapter = new MyCustomListAdapter(this, R.layout.my_list_item, productList);
        listView.setAdapter(adapter);

    }




//take picture from URL function (don't checked);
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}