package com.home.xiaoniuniu;

import com.home.xiaoniuniu.InvalidArgumentException;
import com.home.xiaoniuniu.MultipleSquareDrawer;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by XiaoNiuniu on 2/19/2016.
 */

public class MultipleSquareDrawerTest {

    @Test(expected = InvalidArgumentException.class)
    public void testDrawMultipleSquaresNoSlideLength() throws InvalidArgumentException {
        MultipleSquareDrawer multipleSquareDrawer = new MultipleSquareDrawer();
        multipleSquareDrawer.drawMultipleSquares();

    }

    @Test(expected = InvalidArgumentException.class)
    public void testDrawMultipleSquaresMaxNagativeSlideLength() throws InvalidArgumentException {
        MultipleSquareDrawer multipleSquareDrawer = new MultipleSquareDrawer();
        multipleSquareDrawer.drawMultipleSquares(-1, -2, -3);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testDrawMultipleSquaresMaxZeroSlideLength() throws InvalidArgumentException {
        MultipleSquareDrawer multipleSquareDrawer = new MultipleSquareDrawer();
        multipleSquareDrawer.drawMultipleSquares(-1, -2, -3, 0);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testDrawMultipleSquaresWithNagativeSlideLength() throws InvalidArgumentException {
        MultipleSquareDrawer multipleSquareDrawer = new MultipleSquareDrawer();
        multipleSquareDrawer.drawMultipleSquares(-1, -2, -3, 1);
    }

    @Test
    public void testDrawMultipleSquaresOnlyOneSlideLength() throws InvalidArgumentException {
        MultipleSquareDrawer multipleSquareDrawer = new MultipleSquareDrawer();
        multipleSquareDrawer.drawMultipleSquares(1);
        String expectedImage = "X";
        Assert.assertEquals(expectedImage, multipleSquareDrawer.toString());
    }

    @Test
    public void testDrawMultipleSquaresTwoOneSlideLengths() throws InvalidArgumentException {
        MultipleSquareDrawer multipleSquareDrawer = new MultipleSquareDrawer();
        multipleSquareDrawer.drawMultipleSquares(1, 1);
        String expectedImage = "X";
        Assert.assertEquals(expectedImage, multipleSquareDrawer.toString());
    }

    @Test
    public void testDrawMultipleSquaresTwoSameSlideLengths() throws InvalidArgumentException {
        MultipleSquareDrawer multipleSquareDrawer = new MultipleSquareDrawer();
        multipleSquareDrawer.drawMultipleSquares(3, 3);
        String expectedImage =
                  "XXX\n"
                + "X X\n"
                + "XXX";
        Assert.assertEquals(expectedImage, multipleSquareDrawer.toString());
    }

    @Test
    public void testDrawMultipleSquaresTwoSlideLengths() throws InvalidArgumentException {
        MultipleSquareDrawer multipleSquareDrawer = new MultipleSquareDrawer();
        multipleSquareDrawer.drawMultipleSquares(2, 4);
        String expectedImage =
                   "XXXX\n"
                 + "XX X\n"
                 + "X  X\n"
                 + "XXXX";
        Assert.assertEquals(expectedImage, multipleSquareDrawer.toString());
    }

    @Test
    public void testDrawMultipleSquaresThreeSlideLengthsWithOne() throws InvalidArgumentException {
        MultipleSquareDrawer multipleSquareDrawer = new MultipleSquareDrawer();
        multipleSquareDrawer.drawMultipleSquares(1, 3, 5);
        String expectedImage =
                   "XXXXX\n"
                 + "X X X\n"
                 + "XXX X\n"
                 + "X   X\n"
                 + "XXXXX";
        Assert.assertEquals(expectedImage, multipleSquareDrawer.toString());
    }

    @Test
    public void testDrawMultipleSquaresThreeSlideLengths() throws InvalidArgumentException {
        MultipleSquareDrawer multipleSquareDrawer = new MultipleSquareDrawer();
        multipleSquareDrawer.drawMultipleSquares(3, 2, 5);
        String expectedImage =
                  "XXXXX\n"
                + "XXX X\n"
                + "XXX X\n"
                + "X   X\n"
                + "XXXXX";
        Assert.assertEquals(expectedImage, multipleSquareDrawer.toString());
    }
}
