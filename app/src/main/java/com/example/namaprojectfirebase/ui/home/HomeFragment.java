package com.example.namaprojectfirebase.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.namaprojectfirebase.AddProduct;
import com.example.namaprojectfirebase.Cart;
import com.example.namaprojectfirebase.Login;
import com.example.namaprojectfirebase.MainActivity;
import com.example.namaprojectfirebase.R;
import com.example.namaprojectfirebase.Register;
import com.example.namaprojectfirebase.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public TextView activeUserNameHomeFragment;
    public ImageButton btnPLus,btnTable, btnAdd,  ordrButton;
    public TextView iRemember;
    public EditText iForget;
    public Button rememberMe, rememberYou;
    public Switch switch1;
    public static String uniqueOfCartID;
    public String text;
    private boolean switchOnOff;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    DatabaseReference dbCarts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dbCarts = FirebaseDatabase.getInstance().getReference().child("carts");
        boolean try1 = dbCarts.equals("a@a.com");
        System.out.println("BOOLEAN" + try1);


        System.out.println("DB CARTS"  + dbCarts);
        uniqueOfCartID = UUID.randomUUID().toString();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        final String admin = "a@a.com";
        final String currentUser = Login.mAuth.getCurrentUser().getEmail();
        System.out.println("THE USER IS " + currentUser);


        createCartFunc(currentUser);
        View root = binding.getRoot();
//        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        System.out.println("Home Fragment name " + Login.nameFromDB);
        btnTable = (ImageButton) root.findViewById(R.id.tableButton);
        btnPLus = (ImageButton) root.findViewById(R.id.plusButton);
        btnAdd = (ImageButton) root.findViewById(R.id.addUser);
        ordrButton = (ImageButton) root.findViewById(R.id.orderButton);
//    @Override
//    public void onClick(View view) {
//
//    }

        //sharedPreferences
//        iRemember = (TextView) root.findViewById(R.id.iRemember);
//        iForget = (EditText) root.findViewById(R.id.iForget);
//        rememberMe = (Button) root.findViewById(R.id.rememberMe);
//        rememberYou = (Button) root.findViewById(R.id.rememberYou);
//        switch1 = (Switch) root.findViewById(R.id.switch1);

//        rememberMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                iRemember.setText(iForget.getText().toString());
//            }
//        });
//
//        rememberYou.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rememberData();
//            }
//        });
//        uploadData();
//        updateViews();

        if (!currentUser.matches(admin)){
            btnPLus.setVisibility(View.INVISIBLE);
            btnAdd.setVisibility(View.INVISIBLE);
        }


        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("Going to Register");
                Intent i = new Intent(getActivity(), Register.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
        ordrButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("Going to Register");
                Intent i = new Intent(getActivity(), Cart.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        btnPLus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("Going to new Product");
                Intent i = new Intent(getActivity(), AddProduct.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        btnTable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("Going to Table");
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
//        activeUserNameHomeFragment = root.findViewById(R.id.activeUserNameHomeFragment);
//        activeUserNameHomeFragment.setText(Login.nameFromDB);

        return root;
    }
    public void rememberData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, iRemember.getText().toString());
        editor.putBoolean(SWITCH1, switch1.isChecked());

        editor.apply();
       // Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }
    public void uploadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
        switchOnOff = sharedPreferences.getBoolean(SWITCH1, false);

    }

    public void updateViews(){
        iRemember.setText(text);
        switch1.setChecked(switchOnOff);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static void createCartFunc (String currentUser){
        Map<String, Object> dataOfCart = new HashMap<>();
        dataOfCart.put("curentUserEmail", currentUser);
        dataOfCart.put("orderPlaced", 0);
        FirebaseDatabase.getInstance().getReference("carts")
                .child(HomeFragment.uniqueOfCartID)
                .setValue(dataOfCart).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    System.out.println("The cart has been added " + HomeFragment.uniqueOfCartID);
                } else {
//                            Toast.makeText(Register.this, " Failed to register! Try again!", Toast.LENGTH_LONG).show();


                }
            }
        });

    }
}