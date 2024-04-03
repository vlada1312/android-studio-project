package ru.mirea.sosnovskayave.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


public class MyLooper extends Thread {
    public Handler mHandler;
    private	Handler	mainHandler;

    public MyLooper(Handler mainThreadHandler)	{
        mainHandler	= mainThreadHandler;
    }

    public void	run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            public	void handleMessage(Message msg)	{
                Bundle bundle = msg.getData();
                int age = bundle.getInt("age");
                String place = bundle.getString("place");

                Log.d("MyLooper", "Age: " + age + ", Place: " + place);

                Message	message	= new Message();
                bundle = new Bundle();
                try {
                    Thread.sleep(age * 1000);
                    bundle.putString("result",	"успешно");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    bundle.putString("result",	"ошибка");
                }
                message.setData(bundle);
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}