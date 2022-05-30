package com.example.namaprojectfirebase;

import android.app.DatePickerDialog;

public class Product {
    public String ID, Name, Description, imageUrl;
    public int Type;
    public double BuyPr, CellPr;
    public long AddingDate, BestBefore;

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

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public int getType() {
        return Type;
    }

    public double getBuyPr() {
        return BuyPr;
    }

    public double getCellPr() {
        return CellPr;
    }

    public long getAddingDate() {
        return AddingDate;
    }

    public long getBestBefore() {
        return BestBefore;
    }

    public String getImageUrl() { return  imageUrl; }

}


