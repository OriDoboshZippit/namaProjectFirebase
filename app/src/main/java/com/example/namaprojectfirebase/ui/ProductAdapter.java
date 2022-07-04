package com.example.namaprojectfirebase.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.namaprojectfirebase.MainActivity;
import com.example.namaprojectfirebase.Product;
import com.example.namaprojectfirebase.R;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    ImageView imageView;
    public Context mCtx;
    private List<Product> productList;
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
        holder.textViewTitle.setText(product.getNameOfProduct());
        holder.textViewDesc.setText(product.getDescription());
        holder.textViewPrice.setText(String.valueOf(product.getBuyPrice()));
        holder.textViewRating.setText(String.valueOf(product.getQuantity()));



    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle, textViewDesc, textViewRating, textViewPrice;
        ImageButton addToCardRecycle;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDB = itemView.findViewById(R.id.imageDB);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            addToCardRecycle = itemView.findViewById(R.id.addToCardRecycle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("HEYYY I CLICKABLE");
                }
            });


            itemView.findViewById(R.id.addToCardRecycle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("HEYY" );

                }
            });

        }
    }

}
