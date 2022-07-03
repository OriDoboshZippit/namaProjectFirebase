package com.example.namaprojectfirebase;

import android.app.DatePickerDialog;

import java.util.HashMap;

public class Product {
    private String ID, Name, Description, imageUrl;
    private int Type;
    private double BuyPr, CellPr;
    private long AddingDate, BestBefore;

//    HashMap <String, Integer> emIds = new HashMap<>();

    public Product (String ID, String Name, double BuyPr, double CellPr, long AddingDate, long BestBefore, int Type, String Description, String imageUrl){
        this.ID = ID;
        this.Name = Name;
        this.CellPr = CellPr;
        this.BuyPr = BuyPr;
        this.AddingDate = AddingDate;
        this.BestBefore = BestBefore;
        this.Type = Type;
        this.Description = Description;
        this.imageUrl = imageUrl;
    }

    public Product (){
    }

    public Product (double BuyPr, String Description, String ID, String Name ){
        this.BuyPr = BuyPr;
        this.Description = Description;
        this.ID = ID;
        this.Name = Name;

    }


    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

//    public String getImageUrl() {
//        return imageUrl;
//    }

//    public int getType() {
//        return Type;
//    }

    public double getBuyPr() {
        return BuyPr;
    }

//    public double getCellPr() {
//        return CellPr;
//    }
//
//    public long getAddingDate() {
//        return AddingDate;
//    }
//
//    public long getBestBefore() {
//        return BestBefore;
//    }
}


