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

public class ExitFragment extends Fragment {

    private DogParkViewModel model;
    private TextView numExitsTextView;
    private EditText dogs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exit, container, false);

        numExitsTextView = view.findViewById(R.id.num_exits_textview);
        dogs = view.findViewById(R.id.dogs);
        dogs.setText("1");

        Button exitButton = view.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isEmpty()) {
                    Toast.makeText(getActivity(), "Park is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    model.exit(Integer.parseInt(dogs.getText().toString()));
                    Toast.makeText(getActivity(), "Dog exited park!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button doubleexitButton = view.findViewById(R.id.double_exit_button);
        doubleexitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isEmpty()) {
                    Toast.makeText(getActivity(), "Park is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    model.exit(2);
                    Toast.makeText(getActivity(), "Dogs exited park!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        model = new ViewModelProvider(getActivity()).get(DogParkViewModel.class);
        model.getAllExitsString().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                numExitsTextView.setText(s);
            }
        });

        return view;
    }
}
