package ru.mirea.sda.mireaproject.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.mirea.sda.mireaproject.R;

public class AddRecordDialog extends DialogFragment {

    public interface OnTextSavedListener {
        void onTextSaved(String text);
    }

    private final OnTextSavedListener listener;

    public AddRecordDialog(OnTextSavedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_record, null);
        dialog.setContentView(view);

        EditText inputText = view.findViewById(R.id.inputText);
        Button saveButton = view.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            String text = inputText.getText().toString();
            if (!text.isEmpty()) {
                listener.onTextSaved(text);
                dismiss();
            }
        });

        return dialog;
    }
}
