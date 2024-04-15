package ru.mirea.sosnovskayave.task1;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.sosnovskayave.task1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

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

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        load();
    }
    public void load() {
        SharedPreferences preferences = getSharedPreferences("mirea_settings",	Context.MODE_PRIVATE);
        binding.editTextText.setText(String.valueOf(preferences.getInt("GroupNumber", 0)));
        binding.editTextText2.setText(String.valueOf(preferences.getInt("listNumber", 0)));
        binding.editTextText3.setText(preferences.getString("film", "unknown"));
    }

    public void save(View view) {
        SharedPreferences sharedPref = getSharedPreferences("mirea_settings",	Context.MODE_PRIVATE);
        SharedPreferences.Editor editor	= sharedPref.edit();

        int groupNumber = Integer.parseInt(binding.editTextText.getText().toString());
        int listNumber = Integer.parseInt(binding.editTextText2.getText().toString());
        String film = binding.editTextText3.getText().toString();

        editor.putInt("GroupNumber", groupNumber);
        editor.putInt("listNumber", listNumber);
        editor.putString("film", film);

        editor.apply();
    }
}