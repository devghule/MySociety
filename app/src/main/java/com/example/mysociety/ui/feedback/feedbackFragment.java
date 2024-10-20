package com.example.mysociety.ui.feedback;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mysociety.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class feedbackFragment extends Fragment {

    TextView textFeedback;
    RatingBar ratingBar;
    EditText edsuggetion, edEventName,uname; // Add EditText for event name
    Button btnSubmit;
    TextView thankTextView;

    private FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        textFeedback = view.findViewById(R.id.text_feedback);
        ratingBar = view.findViewById(R.id.rating);
        edsuggetion = view.findViewById(R.id.edsuggetion);
        uname = view.findViewById(R.id.uname);
        edEventName = view.findViewById(R.id.edEventName); // Initialize EditText for event name
        btnSubmit = view.findViewById(R.id.submit);
        thankTextView = view.findViewById(R.id.thank);

        // Initialize Firebase
        firestore = FirebaseFirestore.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

        return view;
    }

    private void submitFeedback() {
        // Retrieve data from UI
        String Name = uname.getText().toString();
        float rating = ratingBar.getRating();
        String suggestions = edsuggetion.getText().toString();
        String eventName = edEventName.getText().toString(); // Retrieve event name

        if(Name.isEmpty()){
            Toast.makeText(getContext(), "Please provide your name", Toast.LENGTH_SHORT).show();
           return;
        }
        // Check if rating or suggestions are empty
        if (rating == 0.0f) {
            Toast.makeText(getContext(), "Please provide a rating", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if event name is empty
        if (eventName.isEmpty()) {
            Toast.makeText(getContext(), "Please provide the event name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map to store feedback data
        Map<String, Object> feedbackData = new HashMap<>();
        feedbackData.put("Name",Name);
        feedbackData.put("rating", rating);
        feedbackData.put("suggestions", suggestions);
        feedbackData.put("eventName", eventName); // Include event name in the feedback data

        // Add the data to Firestore
        firestore.collection("feedback")
                .add(feedbackData)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            // Feedback submitted successfully
                            textFeedback.setText("Feedback submitted successfully");
                            thankTextView.setVisibility(View.VISIBLE);
                            clearFeedbackForm();
                        } else {
                            // Handle the error
                            textFeedback.setText("Failed to submit feedback");
                        }
                    }
                });
    }

    private void clearFeedbackForm() {
        // Clear the feedback form
        uname.setText("");
        ratingBar.setRating(0.0f);
        edsuggetion.getText().clear();
        edEventName.getText().clear(); // Clear event name EditText
    }
}
