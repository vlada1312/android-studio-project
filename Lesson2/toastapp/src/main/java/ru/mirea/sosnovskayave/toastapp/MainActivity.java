package ru.mirea.sosnovskayave.toastapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    public void buttonClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextText);
        String text = String.valueOf(editText.getText());
        int count = text.length();

        Toast toast = Toast.makeText(getApplicationContext(),
                "«СТУДЕНТ № 25 ГРУППА БСБО-04-22 Количество символов - " + count,
                Toast.LENGTH_SHORT);
        toast.show();
    }

}