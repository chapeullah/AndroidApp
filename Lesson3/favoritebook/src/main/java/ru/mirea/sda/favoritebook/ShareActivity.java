package ru.mirea.sda.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    static final String QUOTES_KEY = "quotes_name";

    private TextView textView;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_activity);

        textView = findViewById(R.id.textTitle);
        EditText bookInput = findViewById(R.id.editTitle);
        EditText quoteInput = findViewById(R.id.editQuote);
        Button sendButton = findViewById(R.id.button3);

        Bundle data = getIntent().getExtras();
        if (data != null) {
            String book = data.getString(MainActivity.BOOK_NAME_KEY);
            String quote = data.getString(MainActivity.QUOTES_KEY);
            String formatted = String.format("Вы ранее выбрали: %s\nЦитата: %s", book, quote);
            textView.setText(formatted);
        }

        sendButton.setOnClickListener(v -> {
            String title = bookInput.getText().toString();
            String quotes = quoteInput.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra(MainActivity.USER_MESSAGE, title);
            resultIntent.putExtra(ShareActivity.QUOTES_KEY, quotes);

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}
