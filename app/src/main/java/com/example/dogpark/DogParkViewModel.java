package com.example.dogpark;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DogParkViewModel extends ViewModel {

    private MutableLiveData<Integer> allEntries;
    private MutableLiveData<Integer> allExits;
    private MutableLiveData<Integer> max;
    private MutableLiveData<Integer> inPark;

    private MutableLiveData<String> allEntriesString;
    private MutableLiveData<String> allExitsString;
    private MutableLiveData<String> inParkString;

    private int numHumans;

    public DogParkViewModel() {
        allEntries = new MutableLiveData<>(0);
        allExits = new MutableLiveData<>(0);
        max = new MutableLiveData<>(10);
        inPark = new MutableLiveData<>(0);

        allEntriesString = new MutableLiveData<>("");
        allExitsString = new MutableLiveData<>("");
        inParkString = new MutableLiveData<>("");

        numHumans = 0;

        updateAllEntriesString();
        updateAllExitsString();
        updateInParkString();
    }

    public LiveData<Integer> getAllEntries() {
        return allEntries;
    }

    public LiveData<Integer> getAllExits() {
        return allExits;
    }

    public LiveData<Integer> getMax() {
        return max;
    }

    public LiveData<Integer> getInPark() {
        return inPark;
    }

    public LiveData<String> getAllEntriesString() {
        return allEntriesString;
    }

    public LiveData<String> getAllExitsString() {
        return allExitsString;
    }

    public LiveData<String> getInParkString() {
        return inParkString;
    }

    public boolean isFull() {
        return inPark.getValue() >= max.getValue();
    }

    public boolean isEmpty() {
        return inPark.getValue() <= 0;
    }

    public void enter(int numDogs) {
        if (!isFull()) {
            inPark.setValue(inPark.getValue() + numDogs);
            allEntries.setValue(allEntries.getValue() + numDogs);
            updateAllEntriesString();
            updateInParkString();
        }
    }

    public void enter() {
        if (!isFull()) {
            int entries = allEntries.getValue() != null ? allEntries.getValue() : 0;
            allEntries.setValue(entries + 1);

            int park = inPark.getValue() != null ? inPark.getValue() : 0;
            inPark.setValue(park + 1);
        }
    }

    public void exit(int numDogs) {
        if (!isEmpty()) {
            inPark.setValue(inPark.getValue() - numDogs);
            allExits.setValue(allExits.getValue() + numDogs);
            updateAllExitsString();
            updateInParkString();
        }
    }



    public boolean setMax(int newMax) {
        if(newMax<=0){
            return false;
        }else{
            max.setValue(newMax);
            return true;
        }
    }



    public void reset() {
        max.setValue(1000);
        allEntries.setValue(0);
        allExits.setValue(0);
        inPark.setValue(0);
        numHumans = 0;
        updateAllEntriesString();
        updateAllExitsString();
        updateInParkString();
    }

    private void updateAllEntriesString() {
        allEntriesString.setValue("Total entries " + allEntries.getValue());
    }

    private void updateAllExitsString() {
        allExitsString.setValue("Total exits " + allExits.getValue());
    }

    private void updateInParkString() {
        inParkString.setValue("Dogs in park currently " + inPark.getValue());
    }

    public void performTwoActions(Fragment currentFragment) {
        if (currentFragment instanceof EntryFragment) {
            enter(2);
        } else if (currentFragment instanceof ExitFragment) {
            exit(2);
        }
    }

}
