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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    ImageView imageView;
    public Context mCtx;
    public List<Product> productList;
    public ImageView imageDB;
    public int foundedProductFlag,finishedSnapRun = 0 ;
    public static int valueQnty;
    DatabaseReference cartDb;
    Query cartQuery;

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
        cartDb = FirebaseDatabase.getInstance().getReference("carts").child(HomeFragment.uniqueOfCartID);
        cartQuery = cartDb.orderByKey();
        System.out.println("CART" + cartQuery.get());
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Picasso.get().load(product.getImageUrl()).into(imageDB);
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

            // ADD CARD BUTTON WITH QUANTITY
            itemView.findViewById(R.id.addToCardRecycle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foundedProductFlag = 0;
                    int position = getAdapterPosition();

                    dataReadFunc(position);
                    System.out.println("DATA READ FUNC RUN" );
                    Product product = productList.get(position);
                    EditText text = (EditText) itemView.findViewById(R.id.textViewQuantity);
                    String value = text.getText().toString();
                    valueQnty = Integer.parseInt(value);


                    if (valueQnty > 0) {
                        if (foundedProductFlag == 0) {
                            System.out.println("I purchaseFunc NEW PRODUCT START WITH FLAG !!!!" + foundedProductFlag );
                            purchaseFunc(product.getNameOfProduct(), product.getBuyPrice(), valueQnty);
                        }
                        else
                        {
                            System.out.println("I purchaseFuncUpdateQuantity UPDATE QUANTITY WITH FLAG !!!!" + foundedProductFlag );
                            purchaseFuncUpdateQuantity(product.getNameOfProduct(), product.getBuyPrice(), valueQnty);
                        }

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
        }


        public void dataReadFunc(int position) {
            cartQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> products = new ArrayList<String>();
                    System.out.println("BEFORE ADDING SNAPSHOT RUN  ");
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        System.out.println("NAME OF PRODUCT " + postSnapshot.child("nameOfProduct").getValue());
                        if(postSnapshot.child("nameOfProduct").getValue() == productList.get(position).getNameOfProduct()){
                            System.out.println("FOUNDED PRODUCT WITH SAME NAME NEED TO UPDATE HERE QUANTITY");
                            System.out.println("FOUNDED PRODUCT WITH KEY " + postSnapshot.getKey()  );
                            foundedProductFlag = 1;
                        }
                        products.add(postSnapshot.getValue().toString());
                        System.out.println(products);
                        if(foundedProductFlag == 1){
                            break;
                        }
                    }

                    finishedSnapRun = 1;
                    System.out.println("AFTER RUNNING" + finishedSnapRun);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        public void purchaseFunc(String productName, double price, double quantity) {
            System.out.println("I purchaseFunc NEW PRODUCT!!!!");
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

        public void purchaseFuncUpdateQuantity(String productName, double price, double quantity) {

//            FirebaseDatabase.getInstance()
//                    .getReference("carts")
//                    .child(HomeFragment.uniqueOfCartID).child()


            System.out.println("I purchaseFuncUpdateQuantity UPDATE QUANTITY!!!!");
            Map<String, Object> dataOfCart = new HashMap<>();
            dataOfCart.put("URL", "hey");
            dataOfCart.put("id", "444");
            dataOfCart.put("nameOfProduct", productName);
            dataOfCart.put("buyPrice", price);
            dataOfCart.put("quantity", 2323);
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


