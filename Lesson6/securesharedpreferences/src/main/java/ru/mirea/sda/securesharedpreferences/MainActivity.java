package ru.mirea.sda.securesharedpreferences;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private TextView name;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        photo = findViewById(R.id.photo);

        try {
            String mainKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            SharedPreferences securePrefs = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            String savedName = securePrefs.getString("secure", "Шамко Денис");
            name.setText(savedName);

            SharedPreferences.Editor editor = securePrefs.edit();
            editor.putString("secure", "Шамко Денис");
            editor.putString("kot", "kot");
            editor.apply();

            String imageKey = securePrefs.getString("kot", "kot");
            int imageResId = getResources().getIdentifier(imageKey, "raw", getPackageName());
            InputStream inputStream = getResources().openRawResource(imageResId);
            Drawable drawable = Drawable.createFromStream(inputStream, imageKey);
            photo.setImageDrawable(drawable);

        } catch (Exception e) {
            e.printStackTrace();
            name.setText("Ошибка загрузки данных");
        }
    }
}
