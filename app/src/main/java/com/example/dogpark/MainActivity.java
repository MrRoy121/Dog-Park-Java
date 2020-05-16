package com.example.dogpark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment currentFragment;

    private DogParkViewModel dogParkViewModel;
    private Button twoActionsButton,setMax,dogmovement;
    private EditText numDogsEditText;


    private TextView numDogsTextView;
    private EditText maxEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button resetButton = findViewById(R.id.reset_button);
        maxEditText = findViewById(R.id.etmax);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogParkViewModel.reset();
                Toast.makeText(MainActivity.this, "Dog park reset", Toast.LENGTH_SHORT).show();

            }
        });

        Button setMax = findViewById(R.id.setmax);
        setMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dogParkViewModel.setMax(Integer.parseInt(maxEditText.getText().toString()))){
                    Toast.makeText(MainActivity.this, "Max Value Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Max Value Update Failed!!", Toast.LENGTH_SHORT).show();
                }

            }
        });



                fragmentManager = getSupportFragmentManager();
        currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (currentFragment == null) {
            currentFragment = new EntryFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, currentFragment)
                    .commit();
        }



        dogParkViewModel = new ViewModelProvider(this).get(DogParkViewModel.class);
        if (savedInstanceState == null) {
            dogParkViewModel.reset();
        }

        numDogsTextView = findViewById(R.id.num_dogs_textview);
        updateNumDogsTextView();

        Button changeModeButton = findViewById(R.id.change_mode_button);
        changeModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapFragments();
            }
        });

        dogParkViewModel.getInParkString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                updateNumDogsTextView();
            }
        });
    }


    private void swapFragments() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (currentFragment instanceof EntryFragment) {
            currentFragment = new ExitFragment();
        } else {
            currentFragment = new EntryFragment();
        }

        transaction.replace(R.id.fragment_container, currentFragment);
        transaction.commit();
    }

    private void updateNumDogsTextView() {
        int numDogs = dogParkViewModel.getInPark().getValue();
        String numDogsString = "Dogs in park currently: " + numDogs;
        numDogsTextView.setText(numDogsString);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void performTwoActions(Fragment currentFragment) {
        int numDogs = 1;
        try {
            numDogs = Integer.parseInt(numDogsEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number of dogs", Toast.LENGTH_SHORT).show();
        }

        if (currentFragment instanceof EntryFragment) {
            dogParkViewModel.enter(numDogs);
        } else if (currentFragment instanceof ExitFragment) {
            dogParkViewModel.exit(numDogs);
        }
    }
}
