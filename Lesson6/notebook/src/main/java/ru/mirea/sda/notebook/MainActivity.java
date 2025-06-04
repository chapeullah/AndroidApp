package ru.mirea.sda.notebook;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private EditText FileName;
    private EditText Quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileName = findViewById(R.id.text);
        Quote = findViewById(R.id.quote);
        Button Save = findViewById(R.id.save);
        Button Load = findViewById(R.id.load);

        Save.setOnClickListener(view -> handleSave());
        Load.setOnClickListener(view -> handleLoad());
    }

    private void handleSave() {
        String fileName = FileName.getText().toString();
        String content = Quote.getText().toString();

        if (fileName.trim().isEmpty()) {
            notifyUser("Введите имя файла");
            return;
        }

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File targetFile = new File(directory, fileName);

        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
            fos.write(content.getBytes());
            notifyUser("Файл сохранён: " + targetFile.getAbsolutePath());
        } catch (IOException e) {
            notifyUser("Ошибка сохранения файла: " + e.getMessage());
        }
    }

    private void handleLoad() {
        String fileName = FileName.getText().toString();

        if (fileName.trim().isEmpty()) {
            notifyUser("Введите имя файла");
            return;
        }

        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File sourceFile = new File(dir, fileName);

        if (!sourceFile.exists()) {
            notifyUser("Файл не найден");
            return;
        }

        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
            Quote.setText(builder.toString().trim());
            notifyUser("Данные успешно загружены");
        } catch (IOException e) {
            notifyUser("Ошибка загрузки: " + e.getMessage());
        }
    }

    private void notifyUser(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
