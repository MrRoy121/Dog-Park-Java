package com.example.dogpark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class EntryFragment extends Fragment {

    private DogParkViewModel model;
    private TextView numEntriesTextView;
    private EditText dogs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry, container, false);

        numEntriesTextView = view.findViewById(R.id.num_entries_textview);
        dogs = view.findViewById(R.id.dogs);
        dogs.setText("1");
        Button enterButton = view.findViewById(R.id.enter_button);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isFull()) {
                    Toast.makeText(getActivity(), "Park is full!", Toast.LENGTH_SHORT).show();
                } else {
                    model.enter(Integer.parseInt(dogs.getText().toString()));
                    Toast.makeText(getActivity(), "Dog entered park!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button doubleenterButton = view.findViewById(R.id.double_enter_button);
        doubleenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isFull()) {
                    Toast.makeText(getActivity(), "Park is full!", Toast.LENGTH_SHORT).show();
                } else {
                    model.enter(2);
                    Toast.makeText(getActivity(), "Dogs entered park!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        model = new ViewModelProvider(getActivity()).get(DogParkViewModel.class);
        model.getAllEntriesString().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                numEntriesTextView.setText(s);
            }
        });

        return view;
    }
}
