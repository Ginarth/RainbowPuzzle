package com.ginarth.rainbowpuzzle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    public static final String TIME_KEY = "time_key", MOVES_KEY = "moves_key";
    private Game game;
    private final Context activityContext;
    private DrawCoordinates drawCoordinates;
    private DrawThread drawThread;

    public DrawView(Context context, Game game) {
        super(context);
        this.game = game;
        this.activityContext = context;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.nunito_bold);
        drawCoordinates = new DrawCoordinates(getWidth(), getHeight(), game.getSize());
        drawThread = new DrawThread(getContext(), getHolder(), game, drawCoordinates, typeface);
        drawThread.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
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
        if (game.isComplete()) {
            Intent intent = new Intent();
            intent.putExtra(TIME_KEY, game.getTimeSecs());
            intent.putExtra(MOVES_KEY, game.getMoves());
            Activity activity = (Activity) activityContext;
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
            return false;
        }

        if (isMatched(event.getX(), event.getY(), drawCoordinates.getBack())) {
            Activity activity = (Activity) activityContext;
            activity.setResult(Activity.RESULT_CANCELED);
            activity.finish();
            return false;
        }

        if (isMatched(event.getX(), event.getY(), drawCoordinates.getRetry())) {
            game.retry();
            return false;
        }

        for (int row = 0; row < game.getSize(); row++) {
            for (int col = 0; col < game.getSize(); col++) {
                if (isMatched(event.getX(), event.getY(), drawCoordinates.getTiles()[row][col])) {
                    game.move(row, col);
                    return false;
                }
            }
        }

        return false;
    }

    private boolean isMatched(float x, float y, Coordinates c) {
        return x >= c.getStartX() && x <= c.getFinishX() &&
                y >= c.getStartY() && y <= c.getFinishY();
    }
}