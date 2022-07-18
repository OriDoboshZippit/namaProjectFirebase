package com.example.namaprojectfirebase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.namaprojectfirebase.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    ImageView imageView;
    public Context mCtx;
    public List<Product> productList;
    public ImageView imageDB;
    public int quantityCounter;
    public static int valueQnty;

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
//      holder.counter.setText(quantityCounter);
        holder.textViewTitle.setText(product.getNameOfProduct());
        holder.textViewDesc.setText(product.getDescription());
        holder.textViewPrice.setText(String.valueOf(product.getBuyPrice()));
        holder.textViewRating.setText("Available Quantity: " + String.valueOf((int) product.getQuantity()));


//        Picasso.with(mCtx).load(product.getImageUrl()).into(holder.imageView);
//        imageView = imageView.findViewById(R.id.imageView);

//        Glide.with(this).load("").into(imageView);
        System.out.println("IMAGE " + product.getImageUrl());
//        Glide.with(mCtx).load("https://www.ou.org/holidays/files/Work-768x512.jpg").into(imageView);

//        holder.imageView.setImageResource();

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
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
            String qty = "Not Available";

            // ADD CARD WITH QUANTITY
            itemView.findViewById(R.id.addToCardRecycle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Product product = productList.get(position);
                    EditText text = (EditText) itemView.findViewById(R.id.textViewQuantity);
                    String value = text.getText().toString();
                    valueQnty = Integer.parseInt(value);

                    if (valueQnty > 0) {
                        purchaseFunc(product.getNameOfProduct(), product.getBuyPrice(), valueQnty);
                        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                        builder.setCancelable(true);
                        builder.setTitle("You added to cart " + valueQnty + " of " + product.getNameOfProduct() + " units");
                        builder.setMessage("For any help you can talk to the supervisor.");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(ProductAdapter.ProductViewHolder.this, "You welcome", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                        builder.setCancelable(true);
                        builder.setTitle("You need to add more than 0 items to cart");
                        builder.setMessage("For any help you can talk to the supervisor.");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(ProductAdapter.ProductViewHolder.this, "You welcome", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                    }
                }
            });

            itemView.findViewById(R.id.textViewQuantity).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText text = (EditText) itemView.findViewById(R.id.textViewQuantity);
                    String value = text.getText().toString();
                    System.out.println("TRY TO CATCH QUANTITY " + value);

//                    AddCart.purchaseFunc(product.getNameOfProduct(), product.getBuyPrice(), product.getQuantity());
                }
            });


        }




        public void purchaseFunc(String productName, double price, double quantity) {
            Map<String, Object> dataOfCart = new HashMap<>();
            dataOfCart.put("URL", "hey");
            dataOfCart.put("id", "444");
            dataOfCart.put("nameOfProduct", productName);
            dataOfCart.put("buyPrice", price);
            dataOfCart.put("quantity", quantity);
            dataOfCart.put("sum", "sokf");
            FirebaseDatabase.getInstance()
                    .getReference("carts")
                    .child(HomeFragment.uniqueOfCartID)
                    .push().setValue(dataOfCart)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                System.out.println("The product added to cart " + HomeFragment.uniqueOfCartID);

                            } else {

                            }

                        }


                    });

        }

    }
}
