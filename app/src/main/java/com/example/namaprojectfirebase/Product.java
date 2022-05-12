package com.example.namaprojectfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Product extends AppCompatActivity implements View.OnClickListener {
    public TextView addProduct;
    private EditText editID, editName, editBuyPrice, editCellPrice, editBestBefore, editDateAdd, editDescription;
    public int Type;

    public Product(String id, String name, double buyPr, double cellPr, String bestBefore, String dateAdding, String description) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        addProduct = (Button) findViewById(R.id.addProductButton);
        addProduct.setOnClickListener(this);

        editID = (EditText) findViewById(R.id.editID);
        editName = (EditText) findViewById(R.id.editName);
        editBuyPrice = (EditText) findViewById(R.id.editBuyPrice);
        editCellPrice = (EditText) findViewById(R.id.editCellPrice);
        editBestBefore = (EditText) findViewById(R.id.editBestBefore);
        editDateAdd = (EditText) findViewById(R.id.editDateAdd);
        editDescription = (EditText) findViewById(R.id.editDescription);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    public static double StoNum (@NonNull String s) {
        try {
            double num = Integer.parseInt(s.trim());
            return num;
        }
        catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
        return 000;
    }

    public void onRadioButtonClickedDrink(View view) {
        Type = 1;
        System.out.println("Drink");
    }
    public void onRadioButtonClickedFood(View view) {
        Type = 2;
        System.out.println("Food");
    }
    public void onRadioButtonClickedGrocery(View view) {
        Type = 3;
        System.out.println("Grocery");
    }
    public void onRadioButtonClickedFruitsVegetables(View view) {
        Type = 4;
        System.out.println("Fruits and Vegetables");
    }
    
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addProductButton:
                System.out.println("Product is added");
                addProduct();
                break;
            case R.id.backButton:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
        

    }

    private void addProduct() {

        String ID = editID.getText().toString().trim();
        String Name = editName.getText().toString().trim();
        String BuyPrice = editBuyPrice.getText().toString().trim();
       // int BuyPr = Integer.parseInt(BuyPrice.trim()); // from sting to int
        String CellPrice = editCellPrice.getText().toString().trim();
       // int CellPr = Integer.parseInt(CellPrice.trim());
        String BestBefore = editBestBefore.getText().toString().trim();
        String DateAdding = editDateAdd.getText().toString().trim();
        String Description = editDescription.getText().toString().trim();
        double buyPr = StoNum(BuyPrice);
        double cellPr = StoNum(CellPrice);


        if (ID.isEmpty()) {
            editID.setError("ID is required!");
            editID.requestFocus();
            return;
        }

        if (Name.isEmpty()) {
            editName.setError("Name is required!");
            editName.requestFocus();
            return;
        }
        if(BuyPrice.isEmpty()){
            editCellPrice.setError("Purchase Price is required!");
            editCellPrice.requestFocus ();
            return;
        }
        if(CellPrice.isEmpty()){
            editCellPrice.setError("Cell Price is required!");
            editCellPrice.requestFocus ();
            return;
        }
        if(BestBefore.isEmpty()){
            editCellPrice.setError("Best Before is required!");
            editCellPrice.requestFocus ();
            return;
        }
        if(DateAdding.isEmpty()){
            editCellPrice.setError("Date of Adding is required!");
            editCellPrice.requestFocus ();
            return;
        }
        if(Description.isEmpty()){
            editCellPrice.setError("Description is required!");
            editCellPrice.requestFocus ();
            return;
        }
                Product product = new Product(ID, Name, buyPr, cellPr, BestBefore, DateAdding, Description);
                System.out.println("After builder new user");
/*
                FirebaseDatabase.getInstance().getReference("products")
                        .child(FirebaseAuth.getInstance().getCurrentProduct().getUid())
                        .setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
*/

                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, " User has been registered", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(Register.this, " Failed to register! Try again!", Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
            else{
                Toast.makeText(Register.this, " Failed to register! Try again!", Toast.LENGTH_LONG).show();
            }
        }

    });
}



    
    
}}
