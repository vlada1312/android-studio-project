package ru.mirea.sosnovskayave.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

import ru.mirea.sosnovskayave.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private	int	counter	=	0;
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

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread mainThread = Thread.currentThread();
        binding.textView.setText("Имя текущего потока: " + mainThread.getName());


        mainThread.setName("МОЙ НОМЕР ГРУППЫ: 4, НОМЕР ПО СПИСКУ: 25, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: Пираты Карибского моря");
        binding.textView.append("\nНовое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(),	"Stack:	"	+	Arrays.toString(mainThread.getStackTrace()));

        binding.button.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void onClick(View v) {
                new	Thread(new Runnable() {
                    public	void run() {
                        int	numberThread = counter++;
                        Log.d("ThreadProject", "Запущен поток № " + numberThread + " студентом группы БСБО-04-22 номер по списку №25");
                        long endTime = System.currentTimeMillis() +	20 * 1000;
                        while (System.currentTimeMillis() <	endTime) {
                            synchronized (this) {
                                try	{
                                    int countPairs = Integer.valueOf(binding.editText1.getText().toString());
                                    int countDays = Integer.valueOf(binding.editText2.getText().toString());
                                    double sr = (double) countPairs / countDays;

                                    wait(endTime	- System.currentTimeMillis());

                                    binding.textView.setText("Среднее количество пар: " + sr);
                                    Log.d(MainActivity.class.getSimpleName(),	"Endtime: " + endTime);
                                }	catch (Exception e)	{
                                    throw new RuntimeException(e);
                                }
                            }
                            Log.d("ThreadProject",	"Выполнен поток № " +	numberThread);
                        }
                    }
                }).start();
            }
        });
    }
}