package com.example.namaprojectfirebase;

import android.app.DatePickerDialog;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class Product {
    public String id, nameOfProduct, description;
    public int type;
    public double buyPrice, sellPrice, quantity;
    private long AddingDate, BestBefore;
    ImageView imageView;


//    public Product(String id, String nameOfProduct, String description, int type, double buyPrice, double sellPrice, long addingDate, long bestBefore) {
//        this.id = id;
//        this.nameOfProduct = nameOfProduct;
//        this.description = description;
//        this.type = type;
//        this.buyPrice = buyPrice;
//        this.sellPrice = sellPrice;
//        AddingDate = addingDate;
//        BestBefore = bestBefore;
//    }

//    public Product(String id, String nameOfProduct, String description, String imageUrl, int type, double buyPrice, double sellPrice, long addingDate, long bestBefore) {
//
//    }
//
//    public Product (String bestBefore, int buyPrice, String dataOfAdding, String description, String id, String nameOfProduct, double sellPrice, int typeOfProduct){
//
//    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public String getDescription() {
        return description;
    }

    public double getBuyPrice() {
        return buyPrice;
    }


    public double getQuantity() {
        return quantity;
    }

    public Product (String nameOfProduct, String description, double buyPrice, double quantity){
        this.nameOfProduct = nameOfProduct;
        this.description = description;
        this.buyPrice = buyPrice;
        this.quantity = quantity;

    }

    public Product (){

    }


    ///THE STRUCTURE

//
//    public TextView addProduct, DateAdding, BestBefore, getProduct;
//    private EditText editID, editName, editBuyPrice, editCellPrice, editDescription;
//    private DatePickerDialog.OnDateSetListener AddingDateListener, BestBeforeListener;
//    public int Type;
//    public static String uniqueOfProducID;
//    public double buyPr, cellPr;

//    public Product (double sellPrice, String description, String id, String nameOfProduct ){
//        this.sellPrice = sellPrice;
//        this.description = description;
//        this.id = id;
//        this.nameOfProduct = nameOfProduct;
//    }


}


