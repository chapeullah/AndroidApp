package ru.mirea.sda.looper;

import android.os.*;

public class MyLooper extends Thread
{
    public Handler mHandler;
    private final Handler mainHandler;

    public MyLooper(Handler mainThreadHandler)
    {
        this.mainHandler = mainThreadHandler;
    }

    @Override
    public void run()
    {
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                String data = msg.getData().getString("KEY");
                android.util.Log.d("MyLooper получил сообщение: ", data);

                int count = data != null ? data.length() : 0;

                Bundle bundle = new Bundle();
                bundle.putString("result",
                        String.format("Количество символов во фразе \"%s\": %d", data, count));

                Message message = Message.obtain();
                message.setData(bundle);
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}
