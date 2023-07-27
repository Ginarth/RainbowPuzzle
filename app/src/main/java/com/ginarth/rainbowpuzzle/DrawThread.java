package com.ginarth.rainbowpuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

    Game game;
    DrawCoordinates drawCoordinates;
    Typeface typeface;
    SurfaceHolder surfaceHolder;
    private volatile boolean running;

    public DrawThread(Context context, SurfaceHolder surfaceHolder, Game game, DrawCoordinates drawCoordinates, Typeface typeface) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
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
                    drawTiles(drawCoordinates.getTiles(), canvas, paint);
                    drawPanel(canvas, paint);
                    if (game.getIsComplete()) {
                        drawCongratulations(canvas, paint);
                    }
                } finally{
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void drawBackground(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
    }
    private void drawTiles(Coordinates[][] tiles, Canvas canvas, Paint paint) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                float round = tiles[row][col].getWidth() / 10;
                int value = game.getTileValue(row, col);
                if (value != 0) {
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.HSVToColor(new float[] {(value - 1) *
                            (300F / (game.getSize() * game.getSize() - 1)), 1.0f, 1.0f}));
                    canvas.drawRoundRect(
                            tiles[row][col].getStartX(),
                            tiles[row][col].getStartY(),
                            tiles[row][col].getFinishX(),
                            tiles[row][col].getFinishY(),
                            round, round, paint);

                    paint.setColor(Color.WHITE);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setTypeface(typeface);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(tiles[row][col].getWidth() / 2F);
                    float textX = tiles[row][col].getStartX() + ((tiles[row][col].getFinishX() - tiles[row][col].getStartX()) / 2f);
                    float textY = tiles[row][col].getStartY() + ((tiles[row][col].getFinishY() - tiles[row][col].getStartY()) / 2f) - ((paint.descent() + paint.ascent()) / 2);
                    canvas.drawText(String.valueOf(value), textX, textY, paint);
                }
            }
        }
    }
    private void drawPanel(Canvas canvas, Paint paint) {
        float round = drawCoordinates.getTiles()[0][0].getWidth() / 10;
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(
                drawCoordinates.getTime().getStartX(),
                drawCoordinates.getTime().getStartY(),
                drawCoordinates.getTime().getFinishX(),
                drawCoordinates.getTime().getFinishY(),
                round, round, paint);
        canvas.drawRoundRect(
                drawCoordinates.getMoves().getStartX(),
                drawCoordinates.getMoves().getStartY(),
                drawCoordinates.getMoves().getFinishX(),
                drawCoordinates.getMoves().getFinishY(),
                round, round, paint);
        paint.setColor(Color.rgb(0, 191, 255));
        canvas.drawRoundRect(
                drawCoordinates.getBack().getStartX(),
                drawCoordinates.getBack().getStartY(),
                drawCoordinates.getBack().getFinishX(),
                drawCoordinates.getBack().getFinishY(),
                round, round, paint);
        paint.setColor(Color.rgb(128, 0, 255));
        canvas.drawRoundRect(
                drawCoordinates.getRetry().getStartX(),
                drawCoordinates.getRetry().getStartY(),
                drawCoordinates.getRetry().getFinishX(),
                drawCoordinates.getRetry().getFinishY(),
                round, round, paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(drawCoordinates.getTime().getHeight() / 2.5F);
        float textX = drawCoordinates.getTime().getStartX() + ((drawCoordinates.getTime().getFinishX() - drawCoordinates.getTime().getStartX()) / 2f);
        float textY = drawCoordinates.getTime().getStartY() + ((drawCoordinates.getTime().getFinishY() - drawCoordinates.getTime().getStartY()) / 2f) - ((paint.descent() + paint.ascent()) / 2);
        long time = game.getFinishTime();
        int hours = (int) (time / 3600);
        int mins = (int) (time % 3600 / 60);
        int secs = (int) (time % 60);
        canvas.drawText(String.format("%02d:%02d:%02d", hours, mins, secs), textX, textY, paint);

        textX = drawCoordinates.getMoves().getStartX() + ((drawCoordinates.getMoves().getFinishX() - drawCoordinates.getMoves().getStartX()) / 2f);
        textY = drawCoordinates.getMoves().getStartY() + ((drawCoordinates.getMoves().getFinishY() - drawCoordinates.getMoves().getStartY()) / 2f) - ((paint.descent() + paint.ascent()) / 2);
        canvas.drawText(String.valueOf(game.getMoves()), textX, textY, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(drawCoordinates.getTime().getHeight() / 2.5F);
        textX = drawCoordinates.getBack().getStartX() + ((drawCoordinates.getBack().getFinishX() - drawCoordinates.getBack().getStartX()) / 2f);
        textY = drawCoordinates.getBack().getStartY() + ((drawCoordinates.getBack().getFinishY() - drawCoordinates.getBack().getStartY()) / 2f) - ((paint.descent() + paint.ascent()) / 2);
        canvas.drawText("BACK", textX, textY, paint);

        textX = drawCoordinates.getRetry().getStartX() + ((drawCoordinates.getRetry().getFinishX() - drawCoordinates.getRetry().getStartX()) / 2f);
        textY = drawCoordinates.getRetry().getStartY() + ((drawCoordinates.getRetry().getFinishY() - drawCoordinates.getRetry().getStartY()) / 2f) - ((paint.descent() + paint.ascent()) / 2);
        canvas.drawText("RETRY", textX, textY, paint);
    }

    private void drawCongratulations(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setAlpha(200);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        paint.setAlpha(100);
        paint.setColor(Color.WHITE);
        paint.setTextSize(400 / 4F);
        float textX = canvas.getWidth() / 2f;
        float textY = canvas.getHeight() / 2 - 200 + ((canvas.getHeight() / 2 + 200 - (canvas.getHeight() / 2 - 200)) / 2f) - ((paint.descent() + paint.ascent()) / 2);
        canvas.drawText("Congratulations!", textX, textY, paint);
    }

    public void finish() {
        running = false;
        this.interrupt();
    }
}
