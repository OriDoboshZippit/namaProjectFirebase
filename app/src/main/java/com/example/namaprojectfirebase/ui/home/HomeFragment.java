package com.example.namaprojectfirebase.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.namaprojectfirebase.Login;
import com.example.namaprojectfirebase.R;
import com.example.namaprojectfirebase.databinding.FragmentHomeBinding;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    public TextView activeUserNameHomeFragment;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        System.out.println("Home Fragment name " + Login.nameFromDB);

        activeUserNameHomeFragment = root.findViewById(R.id.activeUserNameHomeFragment);
        activeUserNameHomeFragment.setText(Login.nameFromDB);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}