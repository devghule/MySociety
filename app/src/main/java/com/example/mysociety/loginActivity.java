package com.example.mysociety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class loginActivity extends AppCompatActivity {
    EditText uname,pass_word;
    Button forgot_pass,login,create_acc,google_login;

    //private static final int RC_SIGN_IN = 123;
    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uname = findViewById(R.id.uname);
        pass_word = findViewById(R.id.pass_word);
        login = findViewById(R.id.login);
        forgot_pass = findViewById(R.id.forgot_pass);
        create_acc = findViewById(R.id.create_acc);
        google_login = findViewById(R.id.google_login);
        mAuth = FirebaseAuth.getInstance();


        forgot_pass.setBackgroundColor(getResources().getColor(R.color.my_button_color));
        //forgot_pass.setTextColor(getResources().getColor(R.color.text_color)); // Change text color
        forgot_pass.setBackgroundResource(0); // Remove background resource (border)
        forgot_pass.setPadding(0, 0, 0, 0); // Remove padding
        String buttonText = forgot_pass.getText().toString();

        SpannableString spannableString = new SpannableString(buttonText);
        spannableString.setSpan(new UnderlineSpan(), 0, buttonText.length(), 0);
        forgot_pass.setText(spannableString);

        create_acc.setBackgroundColor(getResources().getColor(R.color.my_button_color));
        //create_acc.setTextColor(getResources().getColor(R.color.text_color)); // Change text color
        create_acc.setBackgroundResource(0); // Remove background resource (border)
        create_acc.setPadding(0, 0, 0, 0); // Remove padding
        String buttonText1 = create_acc.getText().toString();

        SpannableString spannableString1 = new SpannableString(buttonText1);
        spannableString1.setSpan(new UnderlineSpan(), 0, buttonText1.length(), 0);
        create_acc.setText(spannableString1);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Add your web client ID here
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> {
                    Toast.makeText(loginActivity.this, "Google Play services error.", Toast.LENGTH_SHORT).show();
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
//               Intent  intent = new Intent(loginActivity.this, userhomescreen.class);
//               startActivity(intent);
            }

        });
        create_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(loginActivity.this, , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(loginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(loginActivity.this, "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(loginActivity.this, forgotpassword.class);
                startActivity(intent);
            }
        });

        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

    }
    private void loginUser() {
        String email = uname.getText().toString().trim();
        String password = pass_word.getText().toString().trim();

        // Validate email and password

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            uname.setError("Enter a valid email address");
            pass_word.requestFocus();
            return;
        } else if(TextUtils.isEmpty(password) || password.length() < 6) {
            uname.setError("Password must be at least 6 characters");
            pass_word.requestFocus();
            return;
        }
        else {
            if (uname.getText().toString().equals("admin12@gmail.com") && pass_word.getText().toString().equals("admin123")) {
                signInWithEmailAndPassword(email, password);
                Intent intent1 = new Intent(loginActivity.this, AdminDashboardActivity.class);
                startActivity(intent1);
                Toast.makeText(loginActivity.this, " Admin login sucessfully", Toast.LENGTH_SHORT).show();
            }else {
                signInWithEmailAndPassword(email, password);
                Intent intent = new Intent(loginActivity.this, userhomescreen.class);
                intent.putExtra("email",email);
                intent.putExtra("pass",password);
                startActivity(intent);
            }
            }
            //Toast.makeText(loginActivity.this,"Login Sucessfully",Toast.LENGTH_SHORT).show();

            //signInWithEmailAndPassword(email, password);
        }
        // Call Firebase authentication method for login



    private void signInWithEmailAndPassword(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful
                            Toast.makeText(loginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
//                            String name = sharedPreferences.getString("Name", "");
//                            String contact = sharedPreferences.getString("Contact", "");
//                            String flatNo = sharedPreferences.getString("FlatNo", "");
//                            String wing = sharedPreferences.getString("Wing", "");

                            // Redirect to userhomescreen
//                            Intent intent = new Intent(loginActivity.this, userhomescreen.class);
//                            intent.putExtra("Name", name);
//                            intent.putExtra("Contact", contact);
//                            intent.putExtra("FlatNo", flatNo);
//                            intent.putExtra("Wing", wing);
//                            startActivity(intent);
                            // Redirect to the main activity or any other desired activity
                            // For example: startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            // If login fails, display a message to the user.
                           Toast.makeText(loginActivity.this, "Login failed. Check your credentials.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String userEmail = account.getEmail();

            // Use this information as needed

            // You can also use the GoogleSignInAccount to sign in with Firebase if needed
            // For example, obtain the Google ID token and sign in with Firebase
            String googleIdToken = account.getIdToken();
            //signInWithGoogleFirebase(googleIdToken);
            firebaseAuthWithGoogle(account);
        } else {
            Toast.makeText(loginActivity.this, "Google Sign-In failed : Network Error", Toast.LENGTH_SHORT).show();
        }
    }

//    private void signInWithGoogleFirebase(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        // Sign in success
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        Toast.makeText(loginActivity.this, "Google Sign-In successful", Toast.LENGTH_SHORT).show();
//                        // Redirect to the main activity or any other desired activity
//                        // For example: startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Toast.makeText(loginActivity.this, "Google Sign-In failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Toast.makeText(loginActivity.this, "Google Sign-In successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Update UI or navigate to the main activity
                        Intent intent = new Intent(loginActivity.this,userhomescreen.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(loginActivity.this, "Google Sign-In failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}