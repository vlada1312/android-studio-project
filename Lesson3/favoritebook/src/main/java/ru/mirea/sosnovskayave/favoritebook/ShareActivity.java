package ru.mirea.sosnovskayave.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_share);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView textViewDeveloperBook = findViewById(R.id.textViewDeveloperBook);

        String text = getIntent().getStringExtra("key");
        textViewDeveloperBook.setText("Любимая книга разработчика – " + text);
    }
    public void buttonSendBook(View view) {
        EditText editTextUserBook = findViewById(R.id.editTextUserBook);
        String userBookName = editTextUserBook.getText().toString();
        Intent data = new Intent();
        data.putExtra("MESSAGE", "Название Вашей любимой книги: " + userBookName);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}