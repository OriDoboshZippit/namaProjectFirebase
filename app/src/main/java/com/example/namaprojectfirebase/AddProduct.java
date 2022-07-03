package com.example.namaprojectfirebase;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaParser;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddProduct extends AppCompatActivity implements View.OnClickListener {
    public TextView addProduct, DateAdding, BestBefore, getProduct;
    private EditText editID, editName, editBuyPrice, editCellPrice, editDescription;
    private DatePickerDialog.OnDateSetListener AddingDateListener, BestBeforeListener;
    public int Type;
    public static String uniqueOfProducID;
    public double buyPr, cellPr;

    private DatabaseReference rootDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        rootDataBase = FirebaseDatabase.getInstance().getReference().child("products");
//        rootDataBase.addListenerForSingleValueEvent(rootDataBase.addValueEventListener());


        DateAdding = (TextView) findViewById(R.id.editDateAdd);
        BestBefore = (TextView) findViewById(R.id.editBestBefore);

        DateAdding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar addDate = Calendar.getInstance();
                int year = addDate.get(Calendar.YEAR);
                int month = addDate.get(Calendar.MONTH);
                int day = addDate.get(Calendar.DAY_OF_MONTH);

                addDate.set(year,month,day);
                Long dateInMillies = addDate.getTimeInMillis();
                String date = Long.toString(dateInMillies);
                System.out.println("date is dkjfkejf" + date);
                DatePickerDialog dialog = new DatePickerDialog(
                        AddProduct.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        AddingDateListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        BestBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar addDate = Calendar.getInstance();
                int year = addDate.get(Calendar.YEAR);
                int month = addDate.get(Calendar.MONTH);
                int day = addDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddProduct.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        BestBeforeListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                System.out.println("date out");
            }
        });

        AddingDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                System.out.println("the date is " + day + "/" + month + "/" + year + "/");

                String date = day + "/" + month + "/" + year;
                DateAdding.setText(date);
            }
        };


        BestBeforeListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                System.out.println("the date is " + day + "/" + month + "/" + year + "/");
                String date = day + "/" + month + "/" + year;
                BestBefore.setText(date);

            }
        };

        addProduct = (Button) findViewById(R.id.addProductButton);
        addProduct.setOnClickListener(this);



        editID = (EditText) findViewById(R.id.editID);
        editName = (EditText) findViewById(R.id.editName);
        editBuyPrice = (EditText) findViewById(R.id.editBuyPrice);
        editCellPrice = (EditText) findViewById(R.id.editCellPrice);
        editDescription = (EditText) findViewById(R.id.editDescription);
    }

    public static double StoNum (@NonNull String s) throws ParsingException {
        try {
            double num = Double.parseDouble(s.trim());
            System.out.println("the num is " + num);
            return num;
        }
        catch (NumberFormatException nfe) {
            throw new ParsingException("NumberFormatException: " + nfe.getMessage());
        }
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
        String CellPrice = editCellPrice.getText().toString().trim();
        String addingDate = DateAdding.getText().toString().trim();
        String bestBefore = BestBefore.getText().toString().trim();

        String description = editDescription.getText().toString().trim();


//      String Description = editDescription.getText().toString().trim();



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
            editBuyPrice.setError("Purchase Price is required!");
            editBuyPrice.requestFocus ();
            return;
        }
        if(CellPrice.isEmpty()){
            editCellPrice.setError("Cell Price is required!");
            editCellPrice.requestFocus ();
            return;
        }
        try {
         buyPr = StoNum(BuyPrice);
         cellPr = StoNum(CellPrice);
        } catch (ParsingException e) {
            System.out.println(e.getMessage());
        }
//
//       if(BestBefore.getText().toString().isEmpty()){
//           BestBefore.setError("Best Before is required!");
//           BestBefore.requestFocus ();
//            return;
//        }
//        if(DateAdding.getText().toString().isEmpty()){
//            editCellPrice.setError("Date of Adding is required!");
//            editCellPrice.requestFocus ();
//            return;
//        }
//        if(Description.isEmpty()){
//            editCellPrice.setError("Description is required!");
//            editCellPrice.requestFocus ();
//            return;
//        }


//         Product product = new Product("2341322222214","1",1342545.4,131331.3,1391320420,1397320420,2,"ksdjk", "okdoskdo");


        uniqueOfProducID = UUID.randomUUID().toString();
        Map<String, Object> dataOfProduct = new HashMap<>();
        dataOfProduct.put("id", ID);
        dataOfProduct.put("nameOfProduct", Name);
        dataOfProduct.put("buyPrice", buyPr);
        dataOfProduct.put("sellPrice", cellPr);
        dataOfProduct.put("dataOfAdding", addingDate);
        dataOfProduct.put("bestBefore", bestBefore);
        dataOfProduct.put("typeOfProduct", Type);
        dataOfProduct.put("description", description);



                System.out.println("After builder new product");

                FirebaseDatabase.getInstance().getReference("products")
                        .child(uniqueOfProducID)
                        .setValue(dataOfProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            System.out.println("The product has been added with UNIQUE ID " + uniqueOfProducID);
                        } else {
//                            Toast.makeText(Register.this, " Failed to register! Try again!", Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }

}

