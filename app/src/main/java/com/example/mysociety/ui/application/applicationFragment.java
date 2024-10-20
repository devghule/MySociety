package com.example.mysociety.ui.application;

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
import com.example.mysociety.databinding.FragmentApplicationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class applicationFragment extends Fragment {
    private TextView applicationTextView;
    private Button viewApplicationsButton, acceptButton, rejectButton;
    private FirebaseFirestore firestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application, container, false);
        firestore = FirebaseFirestore.getInstance();

        applicationTextView = view.findViewById(R.id.applicationTextView);
        viewApplicationsButton = view.findViewById(R.id.viewapplications);
        acceptButton = view.findViewById(R.id.accept);
        rejectButton = view.findViewById(R.id.reject);

        viewApplicationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveAndDisplayApplications();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle accept button click
                Toast.makeText(getContext(), "Accepted", Toast.LENGTH_SHORT).show();
            }
        });

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle reject button click
                Toast.makeText(getContext(), "Rejected", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void retrieveAndDisplayApplications() {
        CollectionReference applicationsRef = firestore.collection("Book_Facility");

        applicationsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<ApplicationModel> applicationList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ApplicationModel application = document.toObject(ApplicationModel.class);
                        applicationList.add(application);
                    }

                    displayApplications(applicationList);
                } else {
                    // Log the error message
                    Exception e = task.getException();
                    if (e != null) {
                        Log.e("FirestoreError", "Error retrieving applications", e);
                        Toast.makeText(getContext(), "Error retrieving applications: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("FirestoreError", "Unknown error retrieving applications");
                        Toast.makeText(getContext(), "Unknown error retrieving applications", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void displayApplications(List<ApplicationModel> applications) {
        StringBuilder applicationText = new StringBuilder();
        for (ApplicationModel application : applications) {
            applicationText.append("Name: ").append(application.getName()).append("\n");
            applicationText.append("Flat No: ").append(application.getFlatNo()).append("\n");
            applicationText.append("Facility: ").append(application.getFacility()).append("\n");
            applicationText.append("Subject: ").append(application.getSubject()).append("\n\n");
        }

        if (applicationText.length() > 0) {
            applicationTextView.setText(applicationText.toString());
        } else {
            applicationTextView.setText("No applications found.");
        }
    }
}