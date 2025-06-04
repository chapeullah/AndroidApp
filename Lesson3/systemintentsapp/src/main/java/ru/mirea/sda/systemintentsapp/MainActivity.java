package ru.mirea.sda.systemintentsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.activity_main);
    }

    public void onClickCall(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:88005553535"));
        startActivity(callIntent);
    }

    public void onClickOpenBrowser(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("https://online-edu.mirea.ru/login/index.php"));
        startActivity(browserIntent);
    }

    public void onClickOpenMaps(View view) {
        Uri mapLocation = Uri.parse("geo:55.749479,37.613944");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapLocation);
        startActivity(mapIntent);
    }
}
