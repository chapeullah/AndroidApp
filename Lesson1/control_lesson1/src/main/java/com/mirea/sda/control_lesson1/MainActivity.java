package com.mirea.sda.control_lesson1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStudent;
    private Button btnWhoAmI, btnItIsNotMe;
    private CheckBox checkBox;

    private void toggleCheckbox() {
        checkBox.setChecked(!checkBox.isChecked());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        textViewStudent = findViewById(R.id.textViewStudent);
        btnWhoAmI = findViewById(R.id.btnWhoAmI);
        btnItIsNotMe = findViewById(R.id.btnItIsNotMe);
        checkBox = findViewById(R.id.checkBox);

        btnWhoAmI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewStudent.setText("Мой номер по списку № 23");
                toggleCheckbox();
            }
        });

        btnItIsNotMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewStudent.setText("Это не я сделал");
                toggleCheckbox();
            }
        });
    }
}
