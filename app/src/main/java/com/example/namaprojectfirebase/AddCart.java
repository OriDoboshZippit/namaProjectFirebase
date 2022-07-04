package com.example.namaprojectfirebase;

import static com.example.namaprojectfirebase.Login.mAuth;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddCart extends AppCompatActivity implements View.OnClickListener {
    public TextView addProduct, DateAdding, BestBefore, getProduct;
    public EditText editID, editName, editBuyPrice, editQuantity, editDescription;
    private DatePickerDialog.OnDateSetListener AddingDateListener, BestBeforeListener;
    public int Type;
    public static String buyerEmail;
    public double buyPr;
    private DatabaseReference rootDataBase;
    private StorageReference mStorageRef;
    private ImageView imImage;
    public Uri uploadUri;

    DatabaseReference dbCarts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        buyerEmail = user.getEmail();

        dbCarts = FirebaseDatabase.getInstance().getReference("carts");

    }

   public static void purchaseFunc (String productName, double price){
        Map<String, Object> dataOfCart = new HashMap<>();
        dataOfCart.put("buyer", buyerEmail);
        dataOfCart.put("productName", productName);
        dataOfCart.put("price", price);
       System.out.println("Buyer emeail isss " +buyerEmail);

       FirebaseDatabase.getInstance().getReference("carts")
               .child(Login.uniqueOfCartID)
               .push().setValue(dataOfCart).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    System.out.println("The cart has been added " + Login.uniqueOfCartID);
                } else {
//                            Toast.makeText(Register.this, " Failed to register! Try again!", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    @Override
    public void onClick(View view) {

    }
}
