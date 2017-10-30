package com.ssn.life;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int width;
    private final int height;

    private boolean[][] cells;

    public Field(boolean[][] cells) {
        this.width = cells.length;
        this.height = cells[0].length;
        this.cells = cells;
    }

    public Field(int height, int width) {
        this.width = width;
        this.height = height;
        cells = new boolean[height][width];

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                cells[h][w] = Math.random() < 0.5;
            }
        }
    }

    private int[] getDown(int h, int w) {
        return new int[]{h + 1 > height - 1 ? 0 : h + 1, w};
    }

    private int[] getUp(int h, int w) {
        return new int[]{h - 1 < 0 ? height - 1 : h - 1, w};
    }

    private int[] getRight(int h, int w) {
        return new int[]{h, w + 1 > width - 1 ? 0 : w + 1};
    }

    private int[] getLeft(int h, int w) {
        return new int[]{h, w - 1 < 0 ? width - 1 : w - 1};
    }

    private int[] getDownRight(int h, int w) {
        return new int[]{getDown(h,w)[0], getRight(h,w)[1]};
    }

    private int[] getDownLeft(int h, int w) {
        return new int[]{getDown(h, w)[0], getLeft(h, w)[1]};
    }

    private int[] getUpRight(int h, int w) {
        return new int[]{getUp(h,w)[0],getRight(h,w)[1]};
    }

    private int[] getUpLeft(int h, int w) {
        return new int[]{getUp(h,w)[0], getLeft(h,w)[1]};
    }

    private boolean getCell(int h, int w) {
        return cells[h][w];
    }

    private boolean getCell(int[] point) {
        return getCell(point[0], point[1]);
    }

    private int getNeighboursCount(int h, int w) {
        return (getCell(getRight(h,w)) ? 1 : 0) +
                (getCell(getLeft(h,w)) ? 1 : 0) +
                (getCell(getUp(h,w)) ? 1 : 0) +
                (getCell(getDown(h,w)) ? 1 : 0) +
                (getCell(getUpRight(h,w)) ? 1 : 0) +
                (getCell(getUpLeft(h,w)) ? 1 : 0) +
                (getCell(getDownRight(h,w)) ? 1 : 0) +
                (getCell(getDownLeft(h,w)) ? 1 : 0);
    }

    //TODO make using temp
    public void step() {
        List<int[]> future = new ArrayList<>();

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                if (cells[h][w] && (getNeighboursCount(h,w) < 2 || getNeighboursCount(h,w) > 3)) {
                    future.add(new int[]{h,w});
                }
                if (!cells[h][w] && getNeighboursCount(h,w) == 3) {
                    future.add(new int[]{h,w});
                }
            }
        }

        future.forEach(c->cells[c[0]][c[1]] = !cells[c[0]][c[1]]);
    }

    public boolean[][] getState() {
        return cells.clone();
    }

}
