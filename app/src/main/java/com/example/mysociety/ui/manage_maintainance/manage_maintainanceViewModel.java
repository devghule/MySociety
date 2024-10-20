package com.example.mysociety.ui.manage_maintainance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class manage_maintainanceViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public manage_maintainanceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

