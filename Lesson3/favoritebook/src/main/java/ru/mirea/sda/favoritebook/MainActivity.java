package ru.mirea.sda.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static final String QUOTES_KEY = "quotes_name";
    static final String BOOK_NAME_KEY = "book_name";
    static final String USER_MESSAGE = "MESSAGE";

    private TextView textViewUserBook;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textViewUserBook = findViewById(R.id.textView);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                String bookName = data.getStringExtra(USER_MESSAGE);
                                String quoteText = data.getStringExtra(ShareActivity.QUOTES_KEY);
                                String display = "Вы указали книгу: \"" + bookName + "\".\nЦитата: " + quoteText;
                                textViewUserBook.setText(display);
                            }
                        }
                    }
                }
        );

        Button butt = findViewById(R.id.button);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                activityResultLauncher.launch(intent);
            }
        });
    }
}
