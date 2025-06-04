package ru.mirea.sda.sharer;

import android.content.Intent;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "МИРЭА");

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);

        if (activities != null && !activities.isEmpty()) {
            startActivity(Intent.createChooser(intent, "Поделитесь через:"));
        }
    }
}
