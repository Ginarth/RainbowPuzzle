package com.ginarth.rainbowpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    public static final String NAME_KEY = "name_key", DATE_KEY = "date_key";
    private int size, moves, time;
    private String name, date;
    private EditText result_name;
    private TextView result_size, result_date, result_moves, result_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        size = getIntent().getIntExtra(MainActivity.SIZE_KEY, -1);
        moves = getIntent().getIntExtra(DrawView.MOVES_KEY, -1);
        time = getIntent().getIntExtra(DrawView.TIME_KEY, -1);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        date = dateFormat.format(new Date());

        result_name = findViewById(R.id.result_name);
        result_size = findViewById(R.id.result_size);
        result_moves = findViewById(R.id.result_moves);
        result_time = findViewById(R.id.result_time);
        result_date = findViewById(R.id.result_date);

        String text = String.format("%d X %d", size, size);
        result_size.setText(text);
        result_moves.setText(String.valueOf(moves));
        int hours = (int) (time / 3600);
        int mins = (int) (time % 3600 / 60);
        int secs = (int) (time % 60);
        text = String.format("%02d:%02d:%02d", hours, mins, secs);
        result_time.setText(text);
        result_date.setText(date);

        findViewById(R.id.result_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        findViewById(R.id.result_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                name = result_name.getText().toString();
                if (name.length() > 7) {
                    name = name.substring(0, 6);
                } else if (name.equals("")) {
                    name = "Anonim";
                }

                intent.putExtra(NAME_KEY, name);
                intent.putExtra(MainActivity.SIZE_KEY, size);
                intent.putExtra(DrawView.MOVES_KEY, moves);
                intent.putExtra(DrawView.TIME_KEY, time);
                intent.putExtra(DATE_KEY, date);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}