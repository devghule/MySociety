package com.example.mysociety.ui.maintanance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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

public class maintananceFragment extends Fragment {
    Button payment;
    EditText edname, edflat, edwing, edcontact, edammount;

    private FragmentBookfacilityBinding binding;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintanance, container, false);


        edname = view.findViewById(R.id.edname);
        edwing = view.findViewById(R.id.edwing);
        edflat = view.findViewById(R.id.edflat);
        edcontact = view.findViewById(R.id.edcontact);
        edammount = view.findViewById(R.id.edammount);
        payment = view.findViewById(R.id.payment);


        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validatForm()) {

                    pay();

                    // showDataTOAdmin(name, Flat_No, facility ,subject);
                    // SaveDataInfirebased(name,Flat_No,facility,subject);

                }
            }

        });


        return view;
    }

    private boolean validatForm() {

        boolean isValid = true;
        String name = edname.getText().toString();
        String Flat_No = edflat.getText().toString();
        String Wing = edwing.getText().toString();
        String Contact = edcontact.getText().toString();
        String Ammount = edammount.getText().toString();
        if (name.isEmpty()) {
            isValid = false;
            edname.setError("Please enter your name");
        }

        if (Flat_No.isEmpty()) {
            isValid = false;
            edflat.setError("Please enter your flat number");
        }

        if (Wing.isEmpty()) {
            isValid = false;
            edwing.setError("Please enter the facility");
        }

        if (Contact.isEmpty()) {
            isValid = false;
            edcontact.setError("Please enter the subject");
        }

        if (Ammount.isEmpty()) {
            isValid = false;
            edammount.setError("Please enter the subject");
        }

        return isValid;
    }

    private void pay() {
        String name = edname.getText().toString();
        String Flat_No = edflat.getText().toString();
        String Wing = edwing.getText().toString();
        String Contact = edcontact.getText().toString();
        String Amount = edammount.getText().toString();

        if (!Amount.isEmpty() && !Contact.isEmpty()) {
            String upiId = "devghulejio@ybl"; // Replace with the actual UPI ID of the receiver
            String note = "Maintenance Payment for " + name + " (Flat No: " + Flat_No + ")";

            // Construct the UPI link
            String upiLink = "upi://pay?pa=" + upiId + "&pn=" + name + "&mc=&tid=&tr=" + System.currentTimeMillis() + "&tn=" + note + "&am=" + Amount + "&cu=INR";

            // Open the PhonePe app using the UPI link
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(upiLink));
            startActivity(intent);
        } else {
            // Show an error message if amount or contact is empty
            Toast.makeText(getContext(), "Please enter both amount and contact number", Toast.LENGTH_SHORT).show();
        }
    }
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







