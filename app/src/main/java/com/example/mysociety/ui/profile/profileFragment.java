package com.example.mysociety.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mysociety.ModifyProfileActivity;
import com.example.mysociety.R;
import com.example.mysociety.userhomescreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileFragment extends Fragment {
    Button btnModifyProfile;
    TextView tvUsername, tvContact, tvFlat, tvWing,tvEmail;

    // Reference to the Firebase Realtime Database
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvContact = view.findViewById(R.id.tvContact);
        tvFlat = view.findViewById(R.id.tvFlat);
        tvWing = view.findViewById(R.id.tvWing);
        btnModifyProfile = view.findViewById(R.id.btnModifyProfile);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Attach a listener to retrieve data when it changes
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Retrieve user data
                    String username = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String contact = snapshot.child("contact").getValue(String.class);
                    String flatNo = snapshot.child("flatNo").getValue(String.class);
                    String wing = snapshot.child("wing").getValue(String.class);

                    // Update TextViews with user data
                    tvUsername.setText("Name: " + username);
                    tvEmail.setText("Email: " + email);
                    tvContact.setText("Contact: " + contact);
                    tvFlat.setText("Flat No: " + flatNo);
                    tvWing.setText("Wing No: " + wing);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error, if any
                Toast.makeText(requireContext(), "Data retrieval failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //btnModifyProfile = view.findViewById(R.id.btnModifyProfile);
        btnModifyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Modify button click
                openModifyProfileActivity();
            }
        });

        return view;
    }
    private void openModifyProfileActivity() {
        // You can start a new activity or show a dialog to modify profile details.
        // Example: Start a new activity for profile modification
        Intent intent = new Intent(getContext(), ModifyProfileActivity.class);
        startActivity(intent);
    }
}
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get the text from EditText fields
//                String username = edUsername.getText().toString();
//                String contact = edContact.getText().toString();
//                String flatNo = edFlat.getText().toString();
//                String wingNo = edWing.getText().toString();
//
//                // Check if any field is empty
//                if (username.isEmpty() || contact.isEmpty() || flatNo.isEmpty() || wingNo.isEmpty()) {
//                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Create a User object
//                User user = new User(username, contact, flatNo, wingNo);
//
//                // Save data to Firebase
//                String userId = databaseReference.child("users").push().getKey();
//                databaseReference.child("users").child(userId).setValue(user);
//
//                // Show a message indicating success
//                Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
//
//                // Start the new activity
//                //.setText("username");
//
//
//            }
//        });
//
//        return view;
//    }
//}
//
