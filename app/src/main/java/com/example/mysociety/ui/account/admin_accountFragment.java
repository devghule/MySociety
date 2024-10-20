package com.example.mysociety.ui.account;

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

import com.example.mysociety.ModifyAdminActivity;
import com.example.mysociety.ModifyProfileActivity;
import com.example.mysociety.R;
import com.example.mysociety.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class admin_accountFragment extends Fragment {

    private TextView usernameTextView,  nameTextView;
    private Button modifyButton;

    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_account, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        usernameTextView = view.findViewById(R.id.email);
       // passwordTextView = view.findViewById(R.id.pass);
        nameTextView = view.findViewById(R.id.name);

        modifyButton = view.findViewById(R.id.modify);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Attach a listener to retrieve data when it changes
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Retrieve user data
                    String name = snapshot.child("name").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);


                    // Update TextViews with user data
                    nameTextView.setText("Name: " + name);
                    usernameTextView.setText("Email: " + email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error, if any
                Toast.makeText(requireContext(), "Data retrieval failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //btnModifyProfile = view.findViewById(R.id.btnModifyProfile);
        modifyButton.setOnClickListener(new View.OnClickListener() {
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
        Intent intent = new Intent(getContext(), ModifyAdminActivity.class);
        startActivity(intent);
    }
}

