package com.home.xiaoniuniu;

import com.home.xiaoniuniu.InvalidArgumentException;

/**
 * Created by XiaoNiuniu on 2/19/2016.
 */
public class SquareDrawer {
    private char[][] image = null;

    public SquareDrawer() {
    }

    public void drawSquare(int slideLength) throws InvalidArgumentException {
        // Check the slide length
        if (slideLength <= 0) {
            throw new InvalidArgumentException(slideLength + " is not a valid slide length");
        }

        this.image = new char[slideLength][slideLength];

        for (int rowCnt = 0; rowCnt < this.image.length; rowCnt++) {
            if (rowCnt == 0 || rowCnt == (this.image.length - 1)) {
                drawOneRow(this.image[rowCnt], true);
            }
            else {
                drawOneRow(this.image[rowCnt], false);
            }
        }
    }

    private void drawOneRow(char[] row, boolean isFilledAll) {
        if (isFilledAll) {
            drawFullRow(row);
        }
        else {
            drawNonFullRow(row);
        }
    }

    private void drawFullRow(char[] row) {
        for (int colCnt = 0; colCnt < row.length; colCnt++) {
            row[colCnt] = SquareDrawerConstant.imageSymbol; // Simply fill in the whole row
        }
    }

    private  void drawNonFullRow(char[] row) {
        for (int colCnt = 0; colCnt < row.length; colCnt++) {
            if (colCnt == 0 || (colCnt == row.length - 1)) {
                row[colCnt] = SquareDrawerConstant.imageSymbol; // put the symbol at the beginning and end
            }
            else {
                row[colCnt] = SquareDrawerConstant.space; // Space
            }
        }
    }

    @Override
    public String toString() {
        return SquareDrawerUtil.imageToString(this.image);
    }

    public char[][] getImage() {
        return image;
    }
}

