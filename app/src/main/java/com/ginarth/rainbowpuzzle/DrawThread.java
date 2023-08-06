package com.ginarth.rainbowpuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

    private Game game;
    private SurfaceHolder surfaceHolder;
    private DrawCoordinates drawCoordinates;
    private Typeface typeface;
    private volatile boolean running;

    public DrawThread(Context context, SurfaceHolder surfaceHolder, Game game,
                      DrawCoordinates drawCoordinates, Typeface typeface) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
        this.drawCoordinates = drawCoordinates;
        this.typeface = typeface;
        running = true;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            Paint paint = new Paint();
            if (canvas != null) {
                try {
                    paint.setAntiAlias(true);
                    drawBackground(canvas, paint);
                    drawTiles(canvas, paint);
                    drawPanel(canvas, paint);
                    if (game.isComplete()) {
                        drawWin(canvas, paint);
                    }
                } finally{
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    public void finish() {
        running = false;
        this.interrupt();
    }

    private void drawBackground(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }
    private void drawTiles(Canvas canvas, Paint paint) {
        Coordinates[][] tiles = drawCoordinates.getTiles();
        int value;

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {

                value = game.getTileValue(row, col);
                if (value != 0) {
                    paint.setColor(Color.HSVToColor(new float[] {(value - 1) *
                            (300F / (game.getSize() * game.getSize() - 1)), 1.0f, 1.0f}));
                    drawRoundRect(tiles[row][col], canvas, paint);

                    paint.setTextSize(tiles[row][col].getWidth() / 2F);
                    paint.setColor(Color.WHITE);
                    drawText(tiles[row][col], String.valueOf(value), canvas, paint);
                }
            }
        }
    }
    private void drawPanel(Canvas canvas, Paint paint) {
        paint.setColor(Color.rgb(0, 191, 255));
        drawRoundRect(drawCoordinates.getBack(), canvas, paint);

        paint.setColor(Color.rgb(128, 0, 255));
        drawRoundRect(drawCoordinates.getRetry(), canvas, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(drawCoordinates.getTime().getWidth() / 5F);
        drawText(drawCoordinates.getBack(), "BACK", canvas, paint);
        drawText(drawCoordinates.getRetry(), "RETRY", canvas, paint);
        drawText(drawCoordinates.getTime(), game.getTimeString(), canvas, paint);
        drawText(drawCoordinates.getMoves(), String.valueOf(game.getMoves()), canvas, paint);
    }
    private void drawWin(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setAlpha(200);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        paint.setAlpha(100);
        paint.setColor(Color.WHITE);
        paint.setTextSize(400 / 3F);
        float textX = canvas.getWidth() / 2f;
        float textY = canvas.getHeight() / 2 - 200 + ((canvas.getHeight() / 2 + 200 - (canvas.getHeight() / 2 - 200)) / 2f) - ((paint.descent() + paint.ascent()) / 2);
        canvas.drawText("YOU WIN", textX, textY, paint);
    }

    private void drawRoundRect(Coordinates c, Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(c.getStartX(), c.getStartY(), c.getFinishX(), c.getFinishY(),
                c.getWidth() / 10, c.getWidth() / 10, paint);
    }
    private void drawText(Coordinates c, String text, Canvas canvas, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(typeface);
        paint.setTextAlign(Paint.Align.CENTER);
        float textX = c.getStartX() + ((c.getFinishX() - c.getStartX()) / 2f);
        float textY = c.getStartY() + ((c.getFinishY() - c.getStartY()) / 2f) - ((paint.descent()
                + paint.ascent()) / 2);
        canvas.drawText(text, textX, textY, paint);
    }
}
