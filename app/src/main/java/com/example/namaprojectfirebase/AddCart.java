package com.example.namaprojectfirebase;

import static com.example.namaprojectfirebase.Login.mAuth;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.namaprojectfirebase.ui.home.HomeFragment;
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
    public static double sum = 0;
    DatabaseReference dbCarts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        buyerEmail = user.getEmail();

        dbCarts = FirebaseDatabase.getInstance().getReference("carts");
        Toast.makeText(AddCart.this, "Your summary is "+ sum, Toast.LENGTH_LONG).show();
    }

   public static void purchaseFunc (String productName, double price, double quantity){
        Map<String, Object> dataOfCart = new HashMap<>();
        sum += Double.valueOf(price);
        dataOfCart.put("URL", "hey");
        dataOfCart.put("id", "444");
        dataOfCart.put("nameOfProduct", productName);
        dataOfCart.put("buyPrice", price);
        dataOfCart.put("quantity", quantity);
        dataOfCart.put("sum", sum);

       FirebaseDatabase.getInstance().getReference("carts")
               .child(HomeFragment.uniqueOfCartID)
               .push().setValue(dataOfCart).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    System.out.println("The cart has been added " + HomeFragment.uniqueOfCartID);
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
