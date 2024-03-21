package ru.mirea.sosnovskayave.multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickNewActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        EditText editText =(EditText) findViewById(R.id.editText);
        String text = String.valueOf(editText.getText());
        intent.putExtra("key", text);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность 1 становится видимой для пользователя");
    }

    @Override
    protected void onResume() {
        super.onResume();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность 1 предоставила пользователю возможность взаимодействия с ней");
    }

    @Override
    protected void onPause() {
        super.onPause();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность 1 на паузе");
    }


    @Override
    protected void onStop() {
        super.onStop();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность 1 больше не видна пользователю");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность 1 была перезапущена");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String TAG = MainActivity.class.getSimpleName();
        Log.i(TAG, "Активность 1 уничтожена");
    }
}
