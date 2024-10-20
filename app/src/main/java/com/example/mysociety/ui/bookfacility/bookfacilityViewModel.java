package com.example.mysociety.ui.bookfacility;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class bookfacilityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public bookfacilityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

