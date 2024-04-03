package ru.mirea.sosnovskayave.musicplayer;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.sosnovskayave.musicplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private	ActivityMainBinding	binding;
    int i = 1;

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

        binding.textView2.setText("Сейчас играет песня номер " + i);
        binding.imageButton2.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public void onClick(View v)	{
                i = i+1;
                binding.textView2.setText("Сейчас играет песня номер " + i);
            }
        });
        binding.imageButton3.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public void onClick(View v)	{
                i = i-1;
                binding.textView2.setText("Сейчас играет песня номер " + i);
            }
        });

    }
}