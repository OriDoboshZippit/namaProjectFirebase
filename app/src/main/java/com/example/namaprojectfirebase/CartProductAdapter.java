package com.example.namaprojectfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.CartProductAdapterViewHolder>{

    private Context mCtx;
    private List<Product> productList;

    public CartProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public CartProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recyclerlistcart,null);
        CartProductAdapterViewHolder holder = new CartProductAdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductAdapterViewHolder holder, int position) {
         Product product = productList.get(position);
         System.out.println("QNTY" + holder.textViewQuantity.getText());
         holder.textViewTitle.setText(product.getNameOfProduct());
         holder.textViewQuantity.setText(String.valueOf(product.getQuantity()));
         holder.textViewPrice.setText(String.valueOf("Price per unit: " +product.getBuyPrice()));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CartProductAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewTitle, textViewPrice, textViewQuantity;

        public CartProductAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitleCart);
            textViewPrice = itemView.findViewById(R.id.textViewPriceCart);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantityCart);

        }
    }
}
