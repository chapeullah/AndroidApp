package ru.mirea.sda.favoritebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.second_activity);

        EditText bookInput = findViewById(R.id.editTextBookTitle);
        EditText quoteInput = findViewById(R.id.editTextQuote);
        Button submitButton = findViewById(R.id.buttonsub);

        submitButton.setOnClickListener(v -> {
            String bookTitle = bookInput.getText().toString();
            String quoteText = quoteInput.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("BOOK", bookTitle);
            resultIntent.putExtra("QUOTE", quoteText);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }
}
