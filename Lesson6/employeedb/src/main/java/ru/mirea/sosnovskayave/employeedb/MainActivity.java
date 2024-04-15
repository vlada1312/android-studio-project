package ru.mirea.sosnovskayave.employeedb;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase db = App.getInstance().getDatabase();
    HeroDao heroDao = db.heroDao();
    hero hero = new hero();


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

    }


    public void add(View view) {
        EditText editText = findViewById(R.id.editTextText);
        String heroName = editText.getText().toString();
        new Thread(() -> {
            hero hero = new hero();
            List<hero> heroes = heroDao.getAll();
            hero.id = (heroes.get(heroes.size() - 1).id)+1;
            hero.name = heroName;
            hero.salary = 10000;
            heroDao.insert(hero);
            runOnUiThread(() -> Toast.makeText(this, "Успешно добавлено!", Toast.LENGTH_SHORT).show());
        }).start();
    }

    public void show(View view) {
        TextView tv = findViewById(R.id.textView2);
        new Thread(() -> {
            List<hero> heroes = heroDao.getAll();
            hero lastHero = heroDao.getById(heroes.get(heroes.size() - 1).id);
            lastHero.salary = 20000;
            heroDao.update(lastHero);
            String heroName = lastHero.name;
            runOnUiThread(() -> {
                tv.setText(heroName);
                Log.d(TAG, lastHero.name + " " + lastHero.salary);
            });
        }).start();
    }
}
