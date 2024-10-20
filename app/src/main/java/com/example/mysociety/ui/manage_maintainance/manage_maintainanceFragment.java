package com.example.mysociety.ui.manage_maintainance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mysociety.databinding.FragmentManageMaintainanceBinding;

public class manage_maintainanceFragment extends Fragment {

    private FragmentManageMaintainanceBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        manage_maintainanceViewModel maintananceViewModel =
                new ViewModelProvider(this).get(manage_maintainanceViewModel.class);

        binding = FragmentManageMaintainanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textManagemaintainance;
        maintananceViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

