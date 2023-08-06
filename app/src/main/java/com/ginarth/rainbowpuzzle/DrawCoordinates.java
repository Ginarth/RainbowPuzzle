package com.ginarth.rainbowpuzzle;

public class DrawCoordinates {
    int width, height, size;
    boolean isVertical;

    private Coordinates screen, puzzle, panel;
    private Coordinates[][] tiles;
    private Coordinates time, moves, back, retry;

    float titleSize, titlePadding;

    public Coordinates[][] getTiles() {
        return tiles;
    }
    public Coordinates getBack() {
        return back;
    }
    public Coordinates getRetry() {
        return retry;
    }
    public Coordinates getMoves() {
        return moves;
    }
    public Coordinates getTime() {
        return time;
    }

    public DrawCoordinates(int width, int height, int size) {
        this.width = width;
        this.height = height;
        this.size = size;
        this.tiles = new Coordinates[size][size];

        isVertical = height > width;

        createScreen();
        createPuzzle();
        createPanel();

        titleSize = puzzle.getWidth() / size;
        titlePadding = titleSize / 20;

        createTiles();
        createInfo();
    }

    private void createInfo() {
        float elementHeight, elementWidth;
        if (isVertical) {
            elementHeight = panel.getHeight() / 2;
            elementWidth = panel.getWidth() / 2;
            int counter = 0;
            for (int row = 0; row < 2; row++) {
                for (int col = 0; col < 2; col++) {
                    counter++;
                    Coordinates coordinates = new Coordinates(
                            panel.getStartX() + col * elementWidth + titlePadding,
                            panel.getStartY() + row * elementHeight + titlePadding,
                            panel.getStartX() + col * elementWidth + elementWidth - titlePadding,
                            panel.getStartY() + row * elementHeight + elementHeight - titlePadding
                    );
                    switch (counter) {
                        case 1:
                            back = coordinates;
                            break;
                        case 2:
                            retry = coordinates;
                            break;
                        case 3:
                            moves = coordinates;
                            break;
                        case 4:
                            time = coordinates;
                            break;
                    }
                }
            }
        } else {
            elementHeight = panel.getHeight() / 4;
            elementWidth = panel.getWidth();
            int counter = 0;
            for (int row = 0; row < 4; row++) {
                counter++;
                Coordinates coordinates = new Coordinates(
                        panel.getStartX() + titlePadding,
                        panel.getStartY() + row * elementHeight + titlePadding,
                        panel.getStartX() + elementWidth - titlePadding,
                        panel.getStartY() + row * elementHeight + elementHeight - titlePadding
                );
                switch (counter) {
                    case 1:
                        back = coordinates;
                        break;
                    case 2:
                        retry = coordinates;
                        break;
                    case 3:
                        time = coordinates;
                        break;
                    case 4:
                        moves = coordinates;
                        break;
                }
            }
        }


    }
    private void createTiles() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                tiles[row][col] = new Coordinates(
                        puzzle.getStartX() + col * titleSize + titlePadding,
                        puzzle.getStartY() + row * titleSize + titlePadding,
                        puzzle.getStartX() + col * titleSize + titleSize - titlePadding,
                        puzzle.getStartY() + row * titleSize + titleSize - titlePadding
                );
            }
        }
    }
    private void createPanel() {
        float x1, y1, x2, y2;
        if (isVertical) {
            x1 = screen.getStartX();
            y1 = screen.getStartY();
            x2 = screen.getFinishX();
            y2 = puzzle.getStartY();
        } else {
            x1 = screen.getStartX();
            y1 = screen.getStartY();
            x2 = puzzle.getStartX();
            y2 = screen.getFinishY();
        }
        panel = new Coordinates(x1, y1, x2, y2);
    }
    private void createPuzzle() {
        float x1, y1;
        if (isVertical) {
            x1 = 0;
            y1 = screen.getStartY() + screen.getHeight() - width;
        } else {
            x1 = screen.getStartX() + screen.getWidth() - height;
            y1 = 0;
        }
        puzzle = new Coordinates(x1, y1, screen.getFinishX(), screen.getFinishY());
    }
    private void createScreen() {
        float w, h, x1, y1, x2, y2;
        if (isVertical) {
            w = width;
            h = width / 17F * 24F;
            x1 = 0;
            y1 = (height - h) / 2;
            x2 = width;
            y2 = y1 + h;
        } else {
            w = height / 17F * 24F;
            h = height;
            x1 = (width - w) / 2;
            y1 = 0;
            x2 = x1 + w;
            y2 = h;
        }
        screen = new Coordinates(x1, y1, x2, y2);
    }
}

class Coordinates
{
    private final float startX, startY, finishX, finishY;

    public Coordinates(float startX, float startY, float finishX, float finishY) {
        this.startX = startX;
        this.startY = startY;
        this.finishX = finishX;
        this.finishY = finishY;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getFinishX() {
        return finishX;
    }

    public float getFinishY() {
        return finishY;
    }

    public float getWidth() {
        return finishX - startX;
    }

    public float getHeight() {
        return finishY - startY;
    }
}
