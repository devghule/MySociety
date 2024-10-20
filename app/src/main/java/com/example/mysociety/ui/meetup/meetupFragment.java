package com.example.mysociety.ui.meetup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mysociety.R;
import com.example.mysociety.databinding.FragmentMeetupBinding;

import java.util.Calendar;

public class meetupFragment  extends Fragment {
    private int hour, minute;
    TextView dateTextView, timeTextView;
    Button time, date, send;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetup, container, false);



        dateTextView = view.findViewById(R.id.dateTextView);
        timeTextView = view.findViewById(R.id.timeTextView);
        time = view.findViewById(R.id.time);
        date = view.findViewById(R.id.date);
        send = view.findViewById(R.id.send); // Add this line to initialize the 'send' button


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
                updateSelectedTime();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Notification sent successfully", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }



    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                dateTextView.setText("Selected Date: " + selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }



    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                        // Update the selected time in the TextView
                        hour = hourOfDay;
                        minute = minuteOfHour;
                        updateSelectedTime();
                    }
                },
                hour, // Initial hour
                minute, // Initial minute
                false // 24-hour format
        );

        timePickerDialog.show();
    }

    // Method to update the selected time in the TextView
    private void updateSelectedTime() {
        timeTextView.setText("Selected time: " + String.format("%02d:%02d", hour, minute));
    }
}

