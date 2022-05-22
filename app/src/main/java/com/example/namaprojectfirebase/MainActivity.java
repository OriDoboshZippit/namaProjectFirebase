package com.example.namaprojectfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        productList.add(new Product("12", "The Name Is Working", 1231, 311, 1414223565, 321434234,1,"lsdk"));
        listView = (ListView) findViewById(R.id.listview);
        MyCustomListAdapter adapter = new MyCustomListAdapter(this, R.layout.my_list_item, productList);
        listView.setAdapter(adapter);

    }
}