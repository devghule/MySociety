package com.example.mysociety.ui.complaints;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class complaintsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public complaintsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}