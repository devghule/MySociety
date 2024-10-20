package com.example.mysociety.ui.view_feedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mysociety.R;
import com.example.mysociety.databinding.FragmentViewfeedbackBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class viewfeedbackFragment extends Fragment {

    private FirebaseFirestore firestore;
    private ListView feedbackListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewfeedback, container, false);

        firestore = FirebaseFirestore.getInstance();
        feedbackListView = view.findViewById(R.id.feedbackListView);

        // Fetch and display feedbacks
        fetchFeedbacks();

        return view;
    }

    private void fetchFeedbacks() {


        firestore.collection("feedback")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> feedbackList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String feedback = document.getData().toString();
                                feedbackList.add(feedback);
                            }

                            // Display feedbacks in ListView
                            displayFeedbacks(feedbackList);
                        } else {
                            Toast.makeText(getContext(), "Failed to fetch feedbacks", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void displayFeedbacks(List<String> feedbackList) {
        // Use an ArrayAdapter to display feedbacks in a ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, feedbackList);
        feedbackListView.setAdapter(adapter);
    }
}