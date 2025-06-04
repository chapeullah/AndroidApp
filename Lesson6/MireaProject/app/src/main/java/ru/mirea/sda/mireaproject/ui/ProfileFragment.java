package ru.mirea.sda.mireaproject.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import ru.mirea.sda.mireaproject.R;

public class ProfileFragment extends Fragment {

    private EditText groupEditText, numberEditText, filmEditText;
    private Button saveButton;

    private static final String PREF_NAME = "profile_data";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        groupEditText = view.findViewById(R.id.editGroup);
        numberEditText = view.findViewById(R.id.editNumber);
        filmEditText = view.findViewById(R.id.editFilm);
        saveButton = view.findViewById(R.id.buttonSave);

        SharedPreferences preferences = requireActivity()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        groupEditText.setText(preferences.getString("group", ""));
        numberEditText.setText(preferences.getString("number", ""));
        filmEditText.setText(preferences.getString("film", ""));

        saveButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("group", groupEditText.getText().toString());
            editor.putString("number", numberEditText.getText().toString());
            editor.putString("film", filmEditText.getText().toString());
            editor.apply();
        });

        return view;
    }
}