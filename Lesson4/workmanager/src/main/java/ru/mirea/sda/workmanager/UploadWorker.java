package ru.mirea.sda.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class UploadWorker extends Worker
{
    private static final String TAG = "UploadWorker";

    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters params)
    {
        super(context, params);
    }

    @Override
    public Result doWork()
    {
        Log.d(TAG, "Выполнение задачи начато");

        emulateLongOperation();

        Log.d(TAG, "Выполнение задачи завершено");
        return Result.success();
    }

    private void emulateLongOperation()
    {
        try
        {
            TimeUnit.SECONDS.sleep(10); // Имитация задержки
        }
        catch (InterruptedException e)
        {
            Log.e(TAG, "Ошибка задержки", e);
        }
    }
}
