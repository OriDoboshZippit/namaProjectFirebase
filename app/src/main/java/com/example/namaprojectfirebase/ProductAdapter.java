package com.example.namaprojectfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    ImageView imageView;
    public Context mCtx;
    public List <Product> productList;
    public  ImageView imageDB;


    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recyclerlist, null);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Picasso.get().load(product.getImageUrl()).into(imageDB);
//        holder.counter.setText(productList.size());
        holder.textViewTitle.setText(product.getNameOfProduct());
        holder.textViewDesc.setText(product.getDescription());
        holder.textViewPrice.setText(String.valueOf(product.getBuyPrice()));
        holder.textViewRating.setText("Quantity: " + String.valueOf((int)product.getQuantity()));





//        Picasso.with(mCtx).load(product.getImageUrl()).into(holder.imageView);
//        imageView = imageView.findViewById(R.id.imageView);

//        Glide.with(this).load("").into(imageView);
        System.out.println( "IMAGE " + product.getImageUrl());
//        Glide.with(mCtx).load("https://www.ou.org/holidays/files/Work-768x512.jpg").into(imageView);

//        holder.imageView.setImageResource();

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewTitle, textViewDesc, textViewRating, textViewPrice, counter;
        ImageButton addToCardRecycle;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
//            counter = itemView.findViewById(R.id.counter);
            imageDB = itemView.findViewById(R.id.imageDB);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            addToCardRecycle = itemView.findViewById(R.id.addToCardRecycle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Product product = productList.get(position);
                    System.out.println("HEY I CLICKABLE TOO " + product.getNameOfProduct());
                }
            });


            itemView.findViewById(R.id.addToCardRecycle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Product product = productList.get(position);
                    System.out.println("HEYYY ADD TO CARD THIS " + product.getQuantity());
                    AddCart.purchaseFunc(product.getNameOfProduct(), product.getBuyPrice(), product.getQuantity());
                }
            });

        }
    }

}
