package com.example.namaprojectfirebase;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class AddProduct extends AppCompatActivity implements View.OnClickListener {
    public TextView addProduct, DateAdding, BestBefore, getProduct;
    public EditText editID, editName, editBuyPrice, editQuantity , editDescription;
    private DatePickerDialog.OnDateSetListener AddingDateListener, BestBeforeListener;
    public int Type;
    public static String uniqueOfProducID;
    public double buyPr;
    private DatabaseReference rootDataBase;
    private StorageReference mStorageRef;
    private ImageView imImage;
    public Uri uploadUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        rootDataBase = FirebaseDatabase.getInstance().getReference().child("products");
//        rootDataBase.addListenerForSingleValueEvent(rootDataBase.addValueEventListener());
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageDB");

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

       // getProduct = (Button) findViewById(R.id.getProducts);
//        getProduct.setOnClickListener(this);


        editID = (EditText) findViewById(R.id.editID);
        editName = (EditText) findViewById(R.id.editName);
        editBuyPrice = (EditText) findViewById(R.id.editBuyPrice);
        editQuantity = (EditText) findViewById(R.id.quantity);
        editDescription = (EditText) findViewById(R.id.editDescription);
        imImage = (ImageView) findViewById(R.id.imageView);
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
    //Getting image for uploading from internal storage
    public void onClickChooseImage(View view){
        getImage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null && data.getData() != null){
            if (resultCode == RESULT_OK){
                Log.d("My log", "image URI: " + data.getData());
                imImage.setImageURI(data.getData());
                uploadImage();
            }
        }
    }
    private void uploadImage (){
        Bitmap bitmap = ((BitmapDrawable) imImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos );
        byte [] byteArray = baos.toByteArray();

        final StorageReference mRef = mStorageRef.child(System.currentTimeMillis()+ "my_image");
        UploadTask up = mRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadUri = task.getResult(); //tyt hranitsa ssilka
                System.out.println("Heyy, the pic is uploaded  " + uploadUri);
            }
        });
    }

    private void getImage(){
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private void addProduct() {

        String ID = editID.getText().toString().trim();
        String Name = editName.getText().toString().trim();
        String BuyPrice = editBuyPrice.getText().toString().trim();
//        String quantity = editQuantity.getText().toString().trim();
        String quantityString= editQuantity.getText().toString();
        double quantity = Integer.parseInt(quantityString);
        System.out.println("QUANTITY" + quantity);
//        int Quantity = 2;
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
//        if(quantity.isEmpty()){
//            addQuantity.setError("Quantity Price is required!");
//            addQuantity.requestFocus ();
//            return;
//        }
        try {
         buyPr = StoNum(BuyPrice);
//         quantity = StoNum(quantity);
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
        dataOfProduct.put("URL", "uploadUri.toString()" );
        dataOfProduct.put("buyPrice", buyPr);
        dataOfProduct.put("quantity", quantity);
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

