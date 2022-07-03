package com.example.namaprojectfirebase.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.namaprojectfirebase.AddProduct;
import com.example.namaprojectfirebase.Login;
import com.example.namaprojectfirebase.MainActivity;
import com.example.namaprojectfirebase.R;
import com.example.namaprojectfirebase.Register;
import com.example.namaprojectfirebase.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public TextView activeUserNameHomeFragment;
    public ImageButton btnPLus,btnTable, btnAdd;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        final String admin = "a@a.com";
        final String currentUser = Login.mAuth.getCurrentUser().getEmail();
        View root = binding.getRoot();
//        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        System.out.println("Home Fragment name " + Login.nameFromDB);

        btnTable = (ImageButton) root.findViewById(R.id.tableButton);
        btnPLus = (ImageButton) root.findViewById(R.id.plusButton);
        btnAdd = (ImageButton) root.findViewById(R.id.addUser);

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
        activeUserNameHomeFragment = root.findViewById(R.id.activeUserNameHomeFragment);
        activeUserNameHomeFragment.setText(Login.nameFromDB);

        return root;
    }


//    private void addProduct (View view) {
//    System.out.println("HEEYYY");
//

//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}