package com.ginarth.rainbowpuzzle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String SIZE_KEY = "size_key";
    private int size;
    private ImageButton menu_size;

    ActivityResultLauncher<Intent> StartPlayActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Intent intent = new Intent(
                                MainActivity.this,ResultActivity.class);
                        intent.putExtra(SIZE_KEY, size);
                        intent.putExtra(DrawView.TIME_KEY, data.getIntExtra(
                                DrawView.TIME_KEY, -1));
                        intent.putExtra(DrawView.MOVES_KEY, data.getIntExtra(
                                DrawView.MOVES_KEY, -1));
                        StartResultActivityForResult.launch(intent);
                    }
                }
            });

    ActivityResultLauncher<Intent> StartResultActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();

                        /*Toast.makeText(getApplicationContext(), String.format(
                                "%s %d %d %d %s",
                                data.getStringExtra(ResultActivity.NAME_KEY),
                                data.getIntExtra(SIZE_KEY, -1),
                                data.getIntExtra(DrawView.MOVES_KEY, -1),
                                data.getIntExtra(DrawView.TIME_KEY, -1),
                                data.getStringExtra(ResultActivity.DATE_KEY)
                        ), Toast.LENGTH_LONG).show();*/

                        if (data != null) {
                            DatabaseAdapter adapter = new DatabaseAdapter(MainActivity.this);
                            adapter.open();
                            adapter.add(
                                    data.getStringExtra(ResultActivity.NAME_KEY),
                                    data.getIntExtra(SIZE_KEY, -1),
                                    data.getIntExtra(DrawView.MOVES_KEY, -1),
                                    data.getIntExtra(DrawView.TIME_KEY, -1),
                                    data.getStringExtra(ResultActivity.DATE_KEY)
                            );
                            adapter.close();
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        findViewById(R.id.menu_play).callOnClick();
                    }
                }
            });

    ActivityResultLauncher<Intent> StartScoreActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data != null) {
                            size = data.getIntExtra(SIZE_KEY, 3);
                            updateSize();
                        }
                    }
                }
            }
    );

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(SIZE_KEY, size);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        size = savedInstanceState != null ? savedInstanceState.getInt(SIZE_KEY) : 3;
        menu_size = findViewById(R.id.menu_size);

        menu_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size++;
                if (size > 5) {
                    size = 3;
                }
                updateSize();
            }
        });
        findViewById(R.id.menu_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra(SIZE_KEY, size);
                StartPlayActivityForResult.launch(intent);
            }
        });
        findViewById(R.id.menu_score).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                intent.putExtra(SIZE_KEY, size);
                StartScoreActivityForResult.launch(intent);
            }
        });
        findViewById(R.id.menu_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
            }
        });
        findViewById(R.id.menu_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        updateSize();
    }

    private void updateSize() {
        switch (size) {
            case 3:
                menu_size.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_size_3));
                menu_size.setContentDescription(getApplicationContext().getResources().getString(R.string.menu_size_3));
                break;
            case 4:
                menu_size.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_size_4));
                menu_size.setContentDescription(getApplicationContext().getResources().
                        getString(R.string.menu_size_4));
                break;
            case 5:
                menu_size.setImageDrawable(AppCompatResources.
                        getDrawable(getApplicationContext(), R.drawable.menu_size_5));
                menu_size.setContentDescription(getApplicationContext().getResources().
                        getString(R.string.menu_size_5));
                break;
        }
    }
}