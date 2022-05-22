package com.example.namaprojectfirebase;

import android.app.DatePickerDialog;

public class Product {
    public String ID, Name, Description;
    public int Type;
    public double BuyPr, CellPr;
    public long AddingDate, BestBefore;

    public Product (String ID, String Name,double BuyPr, double CellPr, long AddingDate, long BestBefore, int Type, String Description){
        this.ID = ID;
        this.Name = Name;
        this.CellPr = CellPr;
        this.BuyPr = BuyPr;
        this.AddingDate = AddingDate;
        this.BestBefore = BestBefore;
        this.Type = Type;
        this.Description = Description;
    }
}
