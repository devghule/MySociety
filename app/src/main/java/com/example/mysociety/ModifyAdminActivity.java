package com.example.mysociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

public class ModifyAdminActivity extends AppCompatActivity {

    private EditText modified_name, modified_username;
    private Button save_Button;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_admin);

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        modified_name = findViewById(R.id.modified_name);
        modified_username = findViewById(R.id.modified_username);
        save_Button = findViewById(R.id.save_button);

        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void saveChanges() {
        // Get modified data from EditText fields
        String modifiedName = modified_name.getText().toString().trim();
        String modifiedUsername = modified_username.getText().toString().trim();

        // Log for debugging
        //Log.d("ModifyAdminActivity", "User ID: " + userId);
        Log.d("ModifyAdminActivity", "Modified Name: " + modifiedName);
        Log.d("ModifyAdminActivity", "Modified Username: " + modifiedUsername);

        // Update the data in Firebase Realtime Database
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = databaseReference.child(userId);

        userRef.child("name").setValue(modifiedName);
        userRef.child("Email").setValue(modifiedUsername);

        Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity after saving changes
    }
}