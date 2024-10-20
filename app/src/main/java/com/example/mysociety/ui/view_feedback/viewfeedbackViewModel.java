package com.example.mysociety.ui.view_feedback;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class viewfeedbackViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public viewfeedbackViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

