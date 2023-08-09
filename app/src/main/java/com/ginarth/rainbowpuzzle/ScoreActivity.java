package com.ginarth.rainbowpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    private int size;
    private ImageButton score_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        size = savedInstanceState != null ? savedInstanceState.getInt(MainActivity.SIZE_KEY) : getIntent().getIntExtra(MainActivity.SIZE_KEY, 3);
        score_size = findViewById(R.id.score_size);

        score_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size++;
                if (size > 5) {
                    size = 3;
                }
                updateSize();


            }
        });
        findViewById(R.id.score_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.SIZE_KEY, size);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        updateSize();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(MainActivity.SIZE_KEY, size);
        super.onSaveInstanceState(savedInstanceState);
    }

    private ArrayList<Record> getRecords() {
        ArrayList<Record> records;
        DatabaseAdapter adapter = new DatabaseAdapter(ScoreActivity.this);
        adapter.open();
        records = adapter.take(size);
        adapter.close();
        return records;
    }

    private void updateSize() {
        switch (size) {
            case 3:
                score_size.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_size_3));
                score_size.setContentDescription(getApplicationContext().getResources().getString(R.string.menu_size_3));
                break;
            case 4:
                score_size.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_size_4));
                score_size.setContentDescription(getApplicationContext().getResources().getString(R.string.menu_size_4));
                break;
            case 5:
                score_size.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_size_5));
                score_size.setContentDescription(getApplicationContext().getResources().getString(R.string.menu_size_5));
                break;
        }

        RecyclerView recyclerView = findViewById(R.id.score_view);
        ScoreAdapter adapter = new ScoreAdapter(this, getRecords());
        recyclerView.setAdapter(adapter);
    }
}