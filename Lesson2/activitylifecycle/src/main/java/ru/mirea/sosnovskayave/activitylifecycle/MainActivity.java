package ru.mirea.sosnovskayave.activitylifecycle;

//import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onStart() {
        super.onStart();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность становится видимой для пользователя");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность предоставила пользователю возможность взаимодействия с ней");
    }

    @Override
    protected void onPause() {
        super.onPause();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность на паузе");
    }


    @Override
    protected void onStop() {
        super.onStop();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность больше не видна пользователю");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность была перезапущена");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность уничтожена");
    }
}