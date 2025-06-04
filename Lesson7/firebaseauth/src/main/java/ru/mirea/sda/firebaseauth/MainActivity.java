package ru.mirea.sda.firebaseauth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.*;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonRegister, buttonLogin, buttonLogout;
    private TextView textViewStatus;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogout = findViewById(R.id.buttonLogout);
        textViewStatus = findViewById(R.id.textViewStatus);

        updateUI(mAuth.getCurrentUser());

        buttonRegister.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String pass = editTextPassword.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            updateUI(mAuth.getCurrentUser());
                            Toast.makeText(this, "Аккаунт создан", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Ошибка: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String pass = editTextPassword.getText().toString();
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            updateUI(mAuth.getCurrentUser());

                            // Переход на InfoActivity
                            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Ошибка входа", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        buttonLogout.setOnClickListener(v -> {
            mAuth.signOut();
            updateUI(null);
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            textViewStatus.setText("Вы вошли как: " + user.getEmail());
        } else {
            textViewStatus.setText("Состояние: Не авторизован");
        }
    }
}
