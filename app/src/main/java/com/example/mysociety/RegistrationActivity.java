package com.example.mysociety;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.auth.User;
import com.example.mysociety.User;


public class RegistrationActivity extends AppCompatActivity {

    private EditText e1, e2, e3, e4, e5, e6;
    private Button btn1, btn2;

    private FirebaseAuth mAuth;
    // Initialize Firebase in your Application class or MainActivity


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        e3 = findViewById(R.id.e3);
        e4 = findViewById(R.id.e4);
        e5 = findViewById(R.id.e5);
        e6 = findViewById(R.id.e6);
        FirebaseApp.initializeApp(this);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    registerUser();

                } else {
                    Toast.makeText(RegistrationActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Update UI here (e.g., show Toast)
                Toast.makeText(RegistrationActivity.this, "you are on registration page", Toast.LENGTH_SHORT).show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateForm() {
        String email = e2.getText().toString().trim();
        String password = e6.getText().toString().trim();
        String contact = e3.getText().toString().trim();
        String flatNo = e4.getText().toString().trim();
        String wing = e5.getText().toString().trim();
        String name = e1.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || password.length() < 6 || name.isEmpty() || contact.isEmpty() || flatNo.isEmpty() || wing.isEmpty()) {
            // Check if any of the fields is empty
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Check if the email is valid
            e2.setError("Enter a valid email address");
            e2.requestFocus();
            return false;
        }

        // Uncomment the following lines if you want to validate the contact number

    if (!Patterns.PHONE.matcher(contact).matches()) {
        e3.setError("Enter a valid Contact number");
        e3.requestFocus();
        return false;
    }

        // Uncomment the following lines if you want to validate the flat number

    if (flatNo.isEmpty()) {
        e4.setError("Enter your flat number");
        e4.requestFocus();
        return false;
    }


        // Uncomment the following lines if you want to validate the wing

    if (wing.isEmpty()) {
        e5.setError("Enter your wing");
        e5.requestFocus();
        return false;
    }


        return true;
    }


    private void registerUser() {
        String name = e1.getText().toString().trim(); // Get the username
        String email = e2.getText().toString().trim();
        String contact = e3.getText().toString().trim();
        String flatNo = e4.getText().toString().trim();
        String wing = e5.getText().toString().trim();
        String password = e6.getText().toString().trim();



        Log.d("UserData", "Contact: " + contact);
        Log.d("UserData", "FlatNo: " + flatNo);
        Log.d("UserData", "Wing: " + wing);

        User newUser = new User(name, contact, flatNo, wing, email);


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful
                            Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            // Save user data to Firebase Realtime Database
                            if (user != null) {
                                String userId = user.getUid();
                                //User newUser = new User(name, contact, flatNo, wing, email);

                                Log.d("UserData", "User to be saved: " + newUser.toString());

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                                databaseReference.child(userId).setValue(newUser);


                                // Clear the input fields
                                e1.setText("");
                                e2.setText("");
                                e3.setText("");
                                e4.setText("");
                                e5.setText("");
                                e6.setText("");

                                // Log user registration details for debugging
                                Log.d("Registration", "User ID: " + userId);
                                Log.d("Registration", "User Data: " + newUser.toString());
                            }
                        } else {
                            // Registration failed
                            Toast.makeText(RegistrationActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Registration", "Error: " + task.getException().getMessage());
                        }
                    }
                });
    }
}



