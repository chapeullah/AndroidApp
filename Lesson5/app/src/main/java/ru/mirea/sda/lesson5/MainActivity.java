package ru.mirea.sda.lesson5;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.mirea.sda.lesson5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        ListView listSensor = binding.listView;

        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        for (Sensor sensor : sensors) {
            HashMap<String, Object> sensorInfo = new HashMap<>();
            sensorInfo.put("Название", sensor.getName());
            sensorInfo.put("Макс. значение", sensor.getMaximumRange());
            arrayList.add(sensorInfo);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                arrayList,
                android.R.layout.simple_list_item_2,
                new String[]{"Название", "Макс. значение"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        listSensor.setAdapter(adapter);
    }
}
