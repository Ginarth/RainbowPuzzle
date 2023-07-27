package com.ginarth.rainbowpuzzle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    Game game;
    DrawCoordinates drawCoordinates;
    DrawThread drawThread;
    Context activityContext;

    public DrawView(Context context, Game game) {
        super(context);
        this.activityContext = context;
        this.game = game;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.nunito_bold);
        drawCoordinates = new DrawCoordinates(getWidth(), getHeight(), game.getSize());
        drawThread = new DrawThread(getContext(), getHolder(), game, drawCoordinates, typeface);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.finish();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (game.getIsComplete()) {
            Intent intent = new Intent(activityContext, ResultActivity.class);
            intent.putExtra("time", game.getFinishTime());
            intent.putExtra("moves", game.getMoves());
            activityContext.startActivity(intent);
        }

        Coordinates c;
        float x = event.getX(), y = event.getY();

        for (int row = 0; row < game.getSize(); row++) {
            for (int col = 0; col < game.getSize(); col++) {
                c =  drawCoordinates.getTiles()[row][col];
                if (x >= c.getStartX() && x <= c.getFinishX() && y >= c.getStartY() && y <= c.getFinishY()) {
                    game.move(row, col);
                    return false;
                }
            }
        }

        c =  drawCoordinates.getBack();
        if (x >= c.getStartX() && x <= c.getFinishX() && y >= c.getStartY() && y <= c.getFinishY()) {
            ((Activity) activityContext).finish();
            return false;
        }

        c =  drawCoordinates.getRetry();
        if (x >= c.getStartX() && x <= c.getFinishX() && y >= c.getStartY() && y <= c.getFinishY()) {
            game.retry();
        }

        return false;
    }
}
