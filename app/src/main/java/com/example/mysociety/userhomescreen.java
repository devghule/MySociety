package com.example.mysociety;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.mysociety.ui.profile.profileFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mysociety.databinding.ActivityUserhomescreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userhomescreen extends AppCompatActivity {
    private TextView navUserEmailTextView;
    private TextView navUserNameTextView;

    TextView header_title,header_subtitle;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityUserhomescreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserhomescreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarUserhomescreen.toolbar);
        //header_title = findViewById(R.id.header_title);
        //header_subtitle = findViewById(R.id.header_subtitle);


        navUserEmailTextView = findViewById(R.id.nav_user_email);
        navUserNameTextView = findViewById(R.id.nav_user_name);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();

            // Retrieve user's name from Firebase Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String userName = snapshot.child("name").getValue(String.class);

                        // Update the TextViews in the navigation header
                        View headerView = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
                        navUserEmailTextView = headerView.findViewById(R.id.nav_user_email);
                        navUserNameTextView = headerView.findViewById(R.id.nav_user_name);

                        navUserEmailTextView.setText(userEmail);
                        navUserNameTextView.setText(userName);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error, if any
                    Log.e("UserHomeScreenActivity", "Error retrieving user data: " + error.getMessage());
                }
            });
        }


        // Update the TextViews in the navigation header
//            View headerView = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
//            navUserEmailTextView = headerView.findViewById(R.id.nav_user_email);
//            navUserNameTextView = headerView.findViewById(R.id.nav_user_name);
//
//            navUserEmailTextView.setText(userEmail);
//            navUserNameTextView.setText(userName);



        binding.appBarUserhomescreen.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_complaints, R.id.nav_bookfacility)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_userhomescreen);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        Intent intent = getIntent();
//        String username = intent.getStringExtra("Name");
//        String contact = intent.getStringExtra("Contact");
//        String flatNo = intent.getStringExtra("Flat No");
//        String wingNo = intent.getStringExtra("Wing No");
//
//        profileFragment fragment = new profileFragment();
//        // Create a new instance of profileFragment
//        Bundle bundle = new Bundle();
//        bundle.putString("Name", username);
//        bundle.putString("Contact", contact);
//        bundle.putString("FlatNo", flatNo);
//        bundle.putString("Wing", wingNo);
//
//        fragment.setArguments(bundle);

        // Display the profileFragment in the fragment container
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.nav_host_fragment_content_userhomescreen,fragment)
//                .commit();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.userhomescreen, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            // Perform logout
            FirebaseAuth.getInstance().signOut();

            // Navigate to LoginActivity
            Intent intent = new Intent(this, loginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();  // Close the current activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_userhomescreen);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}