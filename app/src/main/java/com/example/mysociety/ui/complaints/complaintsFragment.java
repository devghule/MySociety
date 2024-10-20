package com.example.mysociety.ui.complaints;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mysociety.userhomescreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.storage.FirebaseStorage;
import com.example.mysociety.R;
import com.example.mysociety.databinding.FragmentComplaintsBinding;

import java.util.HashMap;
import java.util.Map;

public class complaintsFragment extends Fragment {

    Button submit, uploadfile, email;
    EditText ediname, ediflat, edcomplaint;

    private FirebaseFirestore firestore;


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaints, container, false);

        FirebaseApp.initializeApp(getContext());
       // FirebaseStorage storage = FirebaseStorage.getInstance();

        firestore = FirebaseFirestore.getInstance();
        ediname = view.findViewById(R.id.ediname);
        ediflat = view.findViewById(R.id.ediflat);
        edcomplaint = view.findViewById(R.id.edcomplaint);
        submit = view.findViewById(R.id.submit);
        uploadfile = view.findViewById(R.id.uploadfile);
        email = view.findViewById(R.id.email);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatform()){

                    submit();
                }
            }
        });

        uploadfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle profile upload logic, e.g., show file chooser
                // showFiletAdmin();
                uploadfile();

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email();
            }
        });
        return view;
    }
    private boolean validatform() {

        boolean isValid = true;

        String name = ediname.getText().toString();
        String Flat_No = ediflat.getText().toString();
        String Complaints = edcomplaint.getText().toString();

        if (name.isEmpty()) {
            isValid = false;
            ediname.setError("Please enter your name");
        }

        if (Flat_No.isEmpty()) {
            isValid = false;
            ediflat.setError("Please enter your flat number");
        }

        if (Complaints.isEmpty()) {
            isValid = false;
            edcomplaint.setError("Please enter your complaint");
        }

        return isValid;
    }

    private void submit() {

        String name = ediname.getText().toString().trim();
        String Flat_No = ediflat.getText().toString().trim();
        String Complaints = edcomplaint.getText().toString().trim();

        Map<String, String> complaintData = new HashMap<>();
        complaintData.put("name", name);
        complaintData.put("flat_No", Flat_No);
        complaintData.put("complaint", Complaints);

        firestore.collection("complaints")
                .add(complaintData)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d("Firestore", "DocumentSnapshot written with ID: " + task.getResult().getId());
                            Intent intent = new Intent(getContext(), userhomescreen.class);
                            startActivity(intent);
                        } else {
                            Log.e("Firestore", "Error adding document", task.getException());
                        }
                    }
                });

        ediname.setText("");
        ediflat.setText("");
        edcomplaint.setText("");

        Toast.makeText(getContext(), "Complaint submitted successfully", Toast.LENGTH_SHORT).show();
    }




    //private void showDataTOAdmin(String name, String Flat_No, String facility ,String subject) {
    // Display the data to the admin (e.g., using a Toast message)
    //String message = "Name: " + name + "Flat No: " + Flat_No + "facility: " + facility +  "\nSubject: " + subject ;

    // }
    //   private void SaveDataInfirebased(String name, String Flat_No, String facility ,String subject){
    // FirebaseDatabase database = FirebaseDatabase.getInstance();
    // DatabaseReference myRef = database.getReference("applications");
    // String applicationId = myRef.push().getKey();

    // Create an Application object with the provided data
    // Application application = new Application();

    // Save the application data to the database under the generated key
    //  myRef.child(applicationId).setValue(application);


    // }
    // private void showFiletAdmin(){

    // }

    // }

    private void uploadfile () {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // You can set a specific MIME type if needed, e.g., "image/*" for images
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivity(Intent.createChooser(intent, "Select a File"));


    }

    private void email () {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email reciever"));


    }
}



