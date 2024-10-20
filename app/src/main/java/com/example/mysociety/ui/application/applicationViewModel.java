package com.example.mysociety.ui.application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class applicationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public applicationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

