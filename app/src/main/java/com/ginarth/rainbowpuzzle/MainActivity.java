package com.ginarth.rainbowpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton menu_difficulty, menu_play, menu_exit, menu_info, menu_score;
    int difficulty = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu_difficulty = findViewById(R.id.menu_difficulty);
        menu_play = findViewById(R.id.menu_play);
        menu_exit = findViewById(R.id.menu_exit);
        menu_info = findViewById(R.id.menu_info);
        menu_score = findViewById(R.id.menu_score);

        menu_difficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty++;
                if (difficulty > 3) {
                    difficulty = 1;
                }
                updateMenuDifficulty();
            }
        });
        menu_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        menu_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        menu_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        menu_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        difficulty = savedInstanceState != null ?
                savedInstanceState.getInt("difficulty") : 1;
        updateMenuDifficulty();
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putInt("difficulty", difficulty);
        super.onSaveInstanceState(state);
    }

    public void updateMenuDifficulty() {
        switch (difficulty) {
            case 1:
                menu_difficulty.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_difficulty_easy));
                break;
            case 2:
                menu_difficulty.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_difficulty_normal));
                break;
            case 3:
                menu_difficulty.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_difficulty_hard));
                break;
        }
    }
}