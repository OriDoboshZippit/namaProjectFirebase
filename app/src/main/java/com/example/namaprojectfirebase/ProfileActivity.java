package com.example.namaprojectfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
public class  ProfileActivity extends AppCompatActivity {

    public TextView activeUserDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        activeUserDataBase = findViewById(R.id.activeUser);
        Bundle b = getIntent().getExtras();
        String id = b.getString("id");
        activeUserDataBase.setText(id);
        System.out.println(id);
    }
}
