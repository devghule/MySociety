package com.example.mysociety.ui.manage_complaints;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mysociety.R;
import com.example.mysociety.databinding.FragmentManageComplaintsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class manage_complaintsFragment extends Fragment {
    private TextView viewComplaintsTextView;
    private Button viewComplaintsButton;
    private FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_complaints, container, false);

        firestore = FirebaseFirestore.getInstance();
        viewComplaintsTextView = view.findViewById(R.id.view_complaints);
        viewComplaintsButton = view.findViewById(R.id.viewcomplaints);

        viewComplaintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveAndDisplayComplaints();
            }
        });

        return view;
    }

    private void retrieveAndDisplayComplaints() {
        CollectionReference complaintsRef = firestore.collection("complaints");

        complaintsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> complaintsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String name = document.getString("name");
                        String flatNo = document.getString("flat_No");
                        String complaint = document.getString("complaint");

                        String formattedComplaint = String.format("Name: %s\nFlat No: %s\nComplaint: %s\n\n",
                                name, flatNo, complaint);

                        complaintsList.add(formattedComplaint);
                    }

                    displayComplaints(complaintsList);
                } else {
                    // Log the error message
                    Exception e = task.getException();
                    if (e != null) {
                        Toast.makeText(getContext(), "Error retrieving complaints: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Unknown error retrieving complaints", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void displayComplaints(List<String> complaints) {
        StringBuilder complaintsText = new StringBuilder();
        for (String complaint : complaints) {
            complaintsText.append(complaint).append("\n");
        }

        if (complaintsText.length() > 0) {
            viewComplaintsTextView.setText(complaintsText.toString());
        } else {
            viewComplaintsTextView.setText("No complaints found.");
        }
    }
}