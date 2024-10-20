package com.example.mysociety.ui.maintanance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class maintananceViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public maintananceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

