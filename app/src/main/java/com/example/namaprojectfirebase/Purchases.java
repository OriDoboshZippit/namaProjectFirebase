package com.example.namaprojectfirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Purchases extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference rootDataBase;

    public EditText editClientName, editAddress, editPhone, editComments ;
    public TextView addPurchases;
    DatabaseReference dataPurchases;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_form);
        rootDataBase = FirebaseDatabase.getInstance().getReference().child("purchases");

        editClientName = (EditText) findViewById(R.id.nameOfTheClient);
        editAddress = (EditText) findViewById(R.id.editAddressClient);
        editPhone = (EditText) findViewById(R.id.phoneOfClient);
        editComments = (EditText) findViewById(R.id.editComments);

        addPurchases = (Button) findViewById(R.id.markOrderRecieved);

        dataPurchases = FirebaseDatabase.getInstance().getReference("cart");

        dataPurchases.addListenerForSingleValueEvent(valueEventListener);



    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.markOrderRecieved:
                System.out.println("Purchase is logged");

                break;

        }
    }

    private void logPurchase () {

        String name = editClientName.getText().toString().trim();
        String address = editAddress.getText().toString().trim();
        String phone= editPhone.getText().toString().trim();
        String comments = editComments.getText().toString().trim();





    }

}
