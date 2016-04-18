package com.home.xiaoniuniu;

import com.home.xiaoniuniu.InvalidArgumentException;

/**
 * Created by XiaoNiuniu on 2/20/2016.
 */
public class MultipleSquareDrawer {

    private char[][] canvas;

    public MultipleSquareDrawer() {
    }

    public void drawMultipleSquares(int ... slideLengths) throws InvalidArgumentException {
        if(slideLengths.length == 0)
            throw new InvalidArgumentException("No slide length input");

        int maxSlideLength = SquareDrawerUtil.maxLength(slideLengths);

        if (maxSlideLength <= 0)
            throw new InvalidArgumentException("Invalid max slide length: " + maxSlideLength);

        this.canvas = new char[maxSlideLength][maxSlideLength];
        initCanvas();

        // Draw square one by one and then merge the result to canvas
        SquareDrawer squareDrawer = new SquareDrawer();
        for (int i = 0; i < slideLengths.length; i++) {
            squareDrawer.drawSquare(slideLengths[i]);
            mergeImage(squareDrawer.getImage());
        }
    }

    // Set all to space
    private void initCanvas() {
        for (int rowCnt = 0; rowCnt < canvas.length; rowCnt++) {
            for (int colCnt = 0; colCnt < canvas[rowCnt].length; colCnt++) {
                canvas[rowCnt][colCnt] = SquareDrawerConstant.space;
            }
        }
    }

    // Merge an image to existing canvas. Only care about the non-space
    private void mergeImage(char[][] image) {
        for (int rowCnt = 0; rowCnt < image.length; rowCnt++) {
            for (int colCnt = 0; colCnt < image[rowCnt].length; colCnt++) {
                // Current position in image is non-space but in canvas is space
                // fill in the canvas
                if (image[rowCnt][colCnt] == SquareDrawerConstant.imageSymbol
                        && this.canvas[rowCnt][colCnt] == SquareDrawerConstant.space) {
                    this.canvas[rowCnt][colCnt] = image[rowCnt][colCnt];
                }
            }
        }
    }

    @Override
    public String toString() {
        return SquareDrawerUtil.imageToString(this.canvas);
    }
}
