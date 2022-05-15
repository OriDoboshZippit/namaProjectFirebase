package com.example.namaprojectfirebase.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.namaprojectfirebase.Login;
import com.example.namaprojectfirebase.R;
import com.example.namaprojectfirebase.Register;
import com.example.namaprojectfirebase.databinding.FragmentHomeBinding;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public TextView activeUserNameHomeFragment;
    public ImageButton btnPLus;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
//        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        System.out.println("Home Fragment name " + Login.nameFromDB);
        btnPLus = (ImageButton) root.findViewById(R.id.plusButton);

        btnPLus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              System.out.println("HEYYY");
        Intent i = new Intent(getActivity(), Register.class);
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