package com.ginarth.rainbowpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton menu_difficulty, menu_play, menu_exit, menu_info, menu_score;
    int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu_difficulty = findViewById(R.id.menu_difficulty);
        menu_play = findViewById(R.id.menu_play);
        menu_exit = findViewById(R.id.menu_exit);
        menu_info = findViewById(R.id.menu_info);
        menu_score = findViewById(R.id.menu_score);
        difficulty = savedInstanceState != null ? savedInstanceState.getInt("difficulty") : 3;

        menu_difficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                difficulty++;
                if (difficulty > 5) {
                    difficulty = 3;
                }
                updateMenuDifficulty();
            }
        });
        menu_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PlayActivity.class);
                i.putExtra("difficulty", difficulty);
                startActivity(i);
            }
        });
        menu_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*finishAffinity();
                System.exit(0);*/
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure?");
                builder.setTitle("Exit");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    finishAffinity();
                    System.exit(0);
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        menu_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
            }
        });
        menu_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        updateMenuDifficulty();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("difficulty", difficulty);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void updateMenuDifficulty() {
        switch (difficulty) {
            case 3:
                menu_difficulty.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_difficulty_easy));
                break;
            case 4:
                menu_difficulty.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_difficulty_normal));
                break;
            case 5:
                menu_difficulty.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_difficulty_hard));
                break;
        }
    }
}