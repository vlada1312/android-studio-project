package ru.mirea.sosnovskayave.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

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

    // TimeDialog
    public void onClickShowTimeDialog(View view) {
        MyTimeDialogFragment timeDialogFragment = new MyTimeDialogFragment();
        timeDialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onTimeClicked(int hourOfDay, int minute) {
//        Toast.makeText(getApplicationContext(), "Было выбрано время: " + hourOfDay +  "ч " + minute + "мин",
//                Toast.LENGTH_LONG).show();
        View view = findViewById(R.id.main);
        Snackbar.make(view, "Было выбрано время: " + hourOfDay +  "ч " + minute + "мин", Snackbar.LENGTH_LONG).show();
    }

    // DateDialog
    public void onClickShowDateDialog(View view) {
        MyDateDialogFragment dateDialogFragment = new MyDateDialogFragment();
        dateDialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onDateClicked(int year, int month, int dayOfMonth) {
//        Toast.makeText(getApplicationContext(), "Была выбрана дата: " + dayOfMonth + "." + (month + 1) + "." + year,
//                Toast.LENGTH_LONG).show();
        View view = findViewById(R.id.main);
        Snackbar.make(view, "Была выбрана дата: " + dayOfMonth + "." + (month + 1) + "." + year, Snackbar.LENGTH_LONG).show();
    }

    // ProgressDialog
    public void onClickShowProgressDialog(View view) {
        MyProgressDialogFragment progressDialogFragment = new MyProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    // ShowDialog (пример)
    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }


}