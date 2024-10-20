package com.example.mysociety.ui.manage_complaints;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class manage_comlaintsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public manage_comlaintsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

