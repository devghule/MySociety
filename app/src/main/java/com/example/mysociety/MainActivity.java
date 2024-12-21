package com.example.mysociety;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // Declare DatabaseReference to interact with Firebase Realtime Database
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance().getReference();

        // Run Firebase operation in the background to avoid UI block
        new Thread(() -> {
            try {
                database.child("message").setValue("Hello from MainActivity!");
            } catch (Exception e) {
                e.printStackTrace();  // Log the exception in case of error
            }
        }).start();

        // Store user data in Firebase
        database.child("users").child("user1").setValue("John Doe");

        // Use Handler with Looper to switch to LoginActivity after 3 seconds
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(intent);
            finish(); // Finish to prevent going back to this activity
        }, 3000); // 3-second delay

        // Call the function to read user data from Firebase
        readUserData();
    }

    // Function to read data from Firebase
    private void readUserData() {
        database.child("users").child("user1").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String value = task.getResult().getValue(String.class);
                Log.d("Firebase", "User: " + value);  // Check the logs for output
            } else {
                Log.e("Firebase", "Failed to read data", task.getException());
            }
        });
    }
}
