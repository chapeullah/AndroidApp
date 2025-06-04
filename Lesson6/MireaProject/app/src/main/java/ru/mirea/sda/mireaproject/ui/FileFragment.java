package ru.mirea.sda.mireaproject.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

import ru.mirea.sda.mireaproject.R;

public class FileFragment extends Fragment {

    private TextView encryptedTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);

        encryptedTextView = view.findViewById(R.id.encryptedTextView);
        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            AddRecordDialog dialog = new AddRecordDialog(text -> {
                encryptedTextView.setText("Зашифрованный текст: " + text);
                saveEncrypted(text);
            });
            dialog.show(getChildFragmentManager(), "add_dialog");
        });

        try {
            String key = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            SharedPreferences prefs = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    key,
                    requireContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            String result = prefs.getString("secure", "нет данных");
            encryptedTextView.setText("Зашифрованный текст: " + result);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void saveEncrypted(String text) {
        try {
            String key = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            SharedPreferences prefs = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    key,
                    requireContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            prefs.edit().putString("secure", text).apply();
        } catch (GeneralSecurityException | IOException e) {
            Toast.makeText(requireContext(), "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        }
    }
}
