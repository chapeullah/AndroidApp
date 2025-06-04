package ru.mirea.sda.simplefragmentapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment1;
    private Fragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        fragment1 = new BlankFragment();
        fragment2 = new BlankFragment2();

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button btnFirstFragment = findViewById(R.id.btnFirstFragment);
        Button btnSecondFragment = findViewById(R.id.btnSecondFragment);

        btnFirstFragment.setOnClickListener(view -> {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment1)
                    .commit();
        });

        btnSecondFragment.setOnClickListener(view -> {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView, fragment2)
                    .commit();
        });
    }
}
