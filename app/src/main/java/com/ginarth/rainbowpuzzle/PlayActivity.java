package com.ginarth.rainbowpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class PlayActivity extends AppCompatActivity {

    Game game;

    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            game = new Game(getIntent().getIntExtra("difficulty", 3));
        } else {
            game = (Game) savedInstanceState.getSerializable("game");
        }

        drawView = new DrawView(this, game);
        setContentView(drawView);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("game", game);
        super.onSaveInstanceState(savedInstanceState);
    }
}