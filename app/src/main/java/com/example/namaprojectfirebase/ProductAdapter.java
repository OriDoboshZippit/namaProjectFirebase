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
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    ImageView imageView;
    public Context mCtx;
    public List<Product> productList;
    public ImageView imageDB;
    public int foundedProductFlag,finishedSnapRun = 0,theFoundedQuantity=0,finishedSnapRunAll = 0 ;
    public static String theFoundedProductKey,keyOfProductFromProfucts,theFoundedProductName;
    public static int valueQnty;
    DatabaseReference cartDb,allProducts;
    Query cartQuery,allProductsQuery;
    Handler handlerDataBase;

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


        allProducts = FirebaseDatabase.getInstance().getReference("products");
        allProductsQuery = allProducts.orderByKey();



        //System.out.println("CART" + cartQuery.get());
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
        //System.out.println("IMAGE " + product.getImageUrl());
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
                    //System.out.println("HEY I CLICKABLE TOO " + product.getNameOfProduct());
                }
            });


            // ADD CARD BUTTON WITH QUANTITY
            itemView.findViewById(R.id.addToCardRecycle).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Product product = productList.get(position);
                    EditText text = (EditText) itemView.findViewById(R.id.textViewQuantity);
                    String value = text.getText().toString();
                    valueQnty = Integer.parseInt(value);

                    //Start running on exist card
                    if (valueQnty > 0) {
                        //System.out.println(position + " THE NAME IS " + product.getNameOfProduct());

                        dataReadFunc(position);
                        allDataReadFunc();
                        System.out.println("DATA READ FUNC RUN FLAG" + foundedProductFlag );
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    // this code will be executed after 1 seconds
                                    if(foundedProductFlag == 0){
                                        if(finishedSnapRunAll==1){
                                            purchaseFunc(product.getNameOfProduct(), product.getBuyPrice(), valueQnty, keyOfProductFromProfucts);
                                            //System.out.println("I purchaseFunc NEW PRODUCT START WITH FLAG  AND TIMER 2000!!!!" + foundedProductFlag );
                                        }

                                    }
                                    else{

                                        purchaseFuncUpdateQuantity(product.getNameOfProduct(), product.getBuyPrice(), valueQnty);
                                        //System.out.println("I purchaseFuncUpdateQuantity NEW PRODUCT START WITH FLAG  AND TIMER 2000!!!!" + foundedProductFlag );
                                    }


                                }
                            }, 1000);

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

        //Start running on exist card
        public int dataReadFunc(int position) {
            cartQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    foundedProductFlag = 0;
                    List<String> products = new ArrayList<String>();
                    //System.out.println("BEFORE ADDING SNAPSHOT RUN  and NAME THAT CAME IS  ");
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //System.out.println("NAME OF PRODUCT " + postSnapshot.child("nameOfProduct").getValue());

                        keyOfProductFromProfucts = postSnapshot.getKey();
                        //System.out.println("THE REAL KEY IS " + keyOfProductFromProfucts);

                        if (postSnapshot.child("nameOfProduct").getValue() == null){
                           //System.out.println("IS NULLL");
                            break;
                        }


                        if(postSnapshot.child("nameOfProduct").getValue().equals(productList.get(position).getNameOfProduct())){
                            foundedProductFlag = 1;
//                           System.out.println("FOUNDED PRODUCT WITH SAME NAME NEED TO UPDATE HERE QUANTITY");
                            System.out.println("FOUNDED PRODUCT WITH KEY " + postSnapshot.getKey() + " AND FLAG IS " + foundedProductFlag);
                            theFoundedProductKey = postSnapshot.getKey();
                            //System.out.println("@@@@" + "The key is of product: " + theFoundedProductKey + " " + postSnapshot.child("quantity").getValue().getClass());
                            Long k = (Long) postSnapshot.child("quantity").getValue();
                            theFoundedQuantity = Math.toIntExact(k);

                            theFoundedProductName = (String) postSnapshot.child("nameOfProduct").getValue();
                            //System.out.println("THE NAME IS  : " + theFoundedProductName);
                            //System.out.println("The quantity is : " + theFoundedQuantity);

                            break;

                        }


                        products.add(postSnapshot.getValue().toString());
                        //System.out.println(products);
                        if(foundedProductFlag == 1){
                            break;
                        }
                    }

                    finishedSnapRun = 1;
                    //System.out.println("AFTER RUNNING" + finishedSnapRun);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
//
            return foundedProductFlag;
        }



        // GET ALL PRODUCTS
        public int allDataReadFunc() {
            allProductsQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //System.out.println("KEY OF PRODUCT " + postSnapshot.getKey());
                        //System.out.println("NAME OF PRODUCT " +postSnapshot.child("nameOfProduct").getValue());
                        if(postSnapshot.child("nameOfProduct").getValue().equals(theFoundedProductName)){
                            //System.out.println("IM JELLLYY " + postSnapshot.getKey());
                            keyOfProductFromProfucts = postSnapshot.getKey();
                        }
                    }
                    finishedSnapRunAll = 1;
                    //System.out.println("AFTER RUNNING" + finishedSnapRunAll);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
//
            return foundedProductFlag;
        }









        public void purchaseFunc(String productName, double price, double quantity, String keyOfProduct ) {

            //System.out.println("I purchaseFunc NEW PRODUCT!!!!");
            Map<String, Object> dataOfCart = new HashMap<>();
            dataOfCart.put("URL", "hey");
            dataOfCart.put("id", "444");
            dataOfCart.put("nameOfProduct", productName);
            dataOfCart.put("buyPrice", price);
            dataOfCart.put("quantity", quantity);
            dataOfCart.put("sum", "sokf");

            //TODO child need to be the same KEY THAT IS IN PRODUCTS
            FirebaseDatabase.getInstance()
                    .getReference("carts")
                    .child(HomeFragment.uniqueOfCartID)
                    .child(keyOfProduct)
                    .setValue(dataOfCart)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //System.out.println("The product added to cart " + HomeFragment.uniqueOfCartID);
                            } else {

                            }

                        }


                    });

        }

        public void purchaseFuncUpdateQuantity(String productName, double price, double quantity) {
            //System.out.println("I purchaseFuncUpdateQuantity UPDATE QUANTITY!!!!");
            Map<String, Object> dataOfCart = new HashMap<>();
            dataOfCart.put("URL", "hey");
            dataOfCart.put("id", "444");
            dataOfCart.put("nameOfProduct", productName);
            dataOfCart.put("buyPrice", price);
            dataOfCart.put("quantity", theFoundedQuantity + valueQnty);
            dataOfCart.put("sum", "sokf");
            FirebaseDatabase.getInstance()
                    .getReference("carts")
                    .child(HomeFragment.uniqueOfCartID).child(theFoundedProductKey)
                    .setValue(dataOfCart)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //System.out.println("The product added to cart " + HomeFragment.uniqueOfCartID);

                            } else {

                            }

                        }


                    });

        }

    }
}


