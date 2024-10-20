package com.example.mysociety;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModifyProfileActivity extends AppCompatActivity {

    private EditText edModifiedUsername, edModifiedContact, edModifiedFlat, edModifiedWing;
    private Button btnSaveChanges;

    // Reference to the Firebase Realtime Database
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        edModifiedUsername = findViewById(R.id.edModifiedUsername);
        edModifiedContact = findViewById(R.id.edModifiedContact);
        edModifiedFlat = findViewById(R.id.edModifiedFlat);
        edModifiedWing = findViewById(R.id.edModifiedWing);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void saveChanges() {
        // Get modified data from EditText fields
        String modifiedUsername = edModifiedUsername.getText().toString().trim();
        String modifiedContact = edModifiedContact.getText().toString().trim();
        String modifiedFlatNo = edModifiedFlat.getText().toString().trim();
        String modifiedWingNo = edModifiedWing.getText().toString().trim();

        // Update the data in Firebase Realtime Database
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = databaseReference.child(userId);

        userRef.child("name").setValue(modifiedUsername);
        userRef.child("contact").setValue(modifiedContact);
        userRef.child("flatNo").setValue(modifiedFlatNo);
        userRef.child("wing").setValue(modifiedWingNo);

        Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity after saving changes
    }
}