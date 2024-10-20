package com.example.mysociety.ui.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mysociety.databinding.FragmentNotificationBinding;

public class notificationFragment extends Fragment {

    private FragmentNotificationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationViewModel notViewModel =
                new ViewModelProvider(this).get(notificationViewModel.class);

        binding =FragmentNotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotification;
       notViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}