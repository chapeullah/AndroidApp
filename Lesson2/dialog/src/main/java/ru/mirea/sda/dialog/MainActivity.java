package ru.mirea.sda.dialog;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance();
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.textView);
        Button displayBT = findViewById(R.id.button2);

        displayBT.setOnClickListener(view -> {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            new TimePickerDialog(MainActivity.this, onTimeSetListener, hour, minute, true).show();
        });
    }

    private final TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String result = String.format("Установлено время: %02d:%02d", hourOfDay, minute);
            Snackbar.make(findViewById(R.id.main), result, Snackbar.LENGTH_LONG).show();
        }
    };

    public void onClickShowDialog(View view) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea_alert");
    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы подтвердили действие.", Toast.LENGTH_LONG).show();
    }

    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Операция отменена пользователем.", Toast.LENGTH_LONG).show();
    }

    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы решили отложить выбор.", Toast.LENGTH_LONG).show();
    }

    public void SnackBar() {
        Snackbar.make(findViewById(R.id.main), "Вызван SnackBar из диалога", Snackbar.LENGTH_LONG).show();
    }
}
