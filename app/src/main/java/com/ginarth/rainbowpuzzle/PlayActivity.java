package com.ginarth.rainbowpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class PlayActivity extends AppCompatActivity {

    public static final String GAME_KEY = "game_key";
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            game = new Game(getIntent().getIntExtra(MainActivity.SIZE_KEY, 3));
        } else {
            game = (Game) savedInstanceState.getSerializable(GAME_KEY);
        }

        setContentView(new DrawView(this, game));
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(GAME_KEY, game);
        super.onSaveInstanceState(savedInstanceState);
    }
}