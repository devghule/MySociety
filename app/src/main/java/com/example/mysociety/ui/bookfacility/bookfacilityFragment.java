package com.example.mysociety.ui.bookfacility;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mysociety.R;
import com.example.mysociety.databinding.FragmentBookfacilityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class bookfacilityFragment extends Fragment {

    Button submit, uploadfile, email;
    EditText edname, edflat, edfacility, edsubject;
    private FirebaseFirestore firestore;
    private FragmentBookfacilityBinding binding;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookfacility, container, false);

        firestore = FirebaseFirestore.getInstance();

        edname = view.findViewById(R.id.edname);
        edfacility = view.findViewById(R.id.edfacility);
        edflat = view.findViewById(R.id.edflat);
        edsubject = view.findViewById(R.id.edsubject);
        submit = view.findViewById(R.id.submit);
        uploadfile = view.findViewById(R.id.uplaodfile);
        email = view.findViewById(R.id.email);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validatForm()) {

                    submit();
                    // showDataTOAdmin(name, Flat_No, facility ,subject);
                    // SaveDataInfirebased(name,Flat_No,facility,subject);

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

                 emaill();
            }
        });
        return view;
    }
        private boolean validatForm () {

            boolean isValid = true;
            String name = edname.getText().toString();
            String Flat_No = edflat.getText().toString();
            String facility = edfacility.getText().toString();
            String subject = edsubject.getText().toString();
            if (name.isEmpty()) {
                isValid = false;
                edname.setError("Please enter your name");
            }

            if (Flat_No.isEmpty()) {
                isValid = false;
                edflat.setError("Please enter your flat number");
            }

            if (facility.isEmpty()) {
                isValid = false;
                edfacility.setError("Please enter the facility");
            }

            if (subject.isEmpty()) {
                isValid = false;
                edsubject.setError("Please enter the subject");
            }

            return isValid;
        }

        private void submit() {

            String name = edname.getText().toString();
            String Flat_No = edflat.getText().toString();
            String facility = edfacility.getText().toString();
            String subject = edsubject.getText().toString();

            // Optionally, show a success message
            Map<String, String> applicationData = new HashMap<>();
            applicationData.put("name", name);
            applicationData.put("flat_No", Flat_No);
            applicationData.put("facility", facility);
            applicationData.put("subject", subject);

            // Add the data to Firestore
            firestore.collection("Book_Facility")
                    .add(applicationData)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                // Data saved successfully
                                // You can add any further actions or UI updates here
                                Toast.makeText(getContext(), "Application submitted successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle the error
                                Toast.makeText(getContext(), "Error submitting application", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            edname.setText("");
            edflat.setText("");
            edfacility.setText("");
            edsubject.setText("");
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

    private void emaill () {

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email reciever"));


    }
    }



