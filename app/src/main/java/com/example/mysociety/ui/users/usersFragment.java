package com.example.mysociety.ui.users;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mysociety.databinding.FragmentUsersBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class usersFragment extends Fragment {

    private FragmentUsersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Find the TextView in your layout with the ID "text_users"
        TextView userInfoTextView = binding.textUsers;

        // Find the Button in your layout with the ID "viewall_members"
        Button viewAllMembersButton = binding.viewallMembers;

        // Set a click listener for the "View All Members" button
        viewAllMembersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

                usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        StringBuilder userList = new StringBuilder("\n\n\n\n\n\n\n\nUsers:\n");

                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String displayName = userSnapshot.child("displayName").getValue(String.class);
                            String email = userSnapshot.child("email").getValue(String.class);

                            // Check for null before appending to the StringBuilder
                            if (displayName != null) {
                                userList.append("Name: ").append(displayName).append("\n");
                            }

                            if (email != null) {
                                userList.append("Email: ").append(email).append("\n\n");
                            }
                        }

                        if (userList.length() > 0) {
                            // Display the list of users in your TextView
                            userInfoTextView.setText(userList.toString());
                        } else {
                            // No users found
                            userInfoTextView.setText("No users found.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors
                        userInfoTextView.setText("Error fetching data. Please try again.");
                    }
                });
            }
        });

        // Don't forget to add this return statement
        return root;
    }
}