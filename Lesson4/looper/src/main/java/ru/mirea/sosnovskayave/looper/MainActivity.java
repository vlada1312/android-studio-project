package ru.mirea.sosnovskayave.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import ru.mirea.sosnovskayave.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    MyLooper myLooper;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding	= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler	mainThreadHandler =	new	Handler(Looper.getMainLooper())	{
            @Override
            public void handleMessage(Message msg)	{
                Log.d(MainActivity.class.getSimpleName(),	"Задача выполнена, результат: " +	msg.getData().getString("result"));
            }
        };
        myLooper =	new	MyLooper(mainThreadHandler);
        myLooper.start();
    }

    public void buttonClick(View v)	{
        Message	msg	= Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putInt("age", Integer.parseUnsignedInt(binding.editText1.getText().toString()));
        bundle.putString("place", binding.editText2.getText().toString());
        msg.setData(bundle);
        myLooper.mHandler.sendMessage(msg);
    }
}