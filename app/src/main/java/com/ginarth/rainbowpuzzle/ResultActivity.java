package com.ginarth.rainbowpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long time = getIntent().getLongExtra("time", (long) 0);
        int moves = getIntent().getIntExtra("moves", 0);
        Toast.makeText(this,   time + " " + moves, Toast.LENGTH_SHORT).show();

        setContentView(R.layout.activity_result);
    }
}