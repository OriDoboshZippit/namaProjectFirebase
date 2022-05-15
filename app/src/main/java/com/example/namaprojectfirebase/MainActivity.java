package com.example.namaprojectfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById (R.id.listview);
        ArrayList <String> arrayList=new ArrayList<> ();
        arrayList.add ("android");
        arrayList.add ("is");
        arrayList.add ("great");
        arrayList.add ("andIlove it");
        arrayList.add ("It");
        arrayList.add ("is");
        arrayList.add ("better");
        arrayList.add ("then");
        arrayList.add ("many");
        arrayList.add ("other");
        arrayList.add ("operating system. ");
        arrayList.add ("operating system. ");
        arrayList.add ("operating system. ");
        arrayList.add ("operating system. ");
        arrayList.add ("operating system. ");
        arrayList.add ("operating system. ");
        arrayList.add ("operating system. ");
        arrayList.add ("operating system. ");


        ArrayAdapter arrayAdapter = new ArrayAdapter (this, android.R.layout.simple_expandable_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
}