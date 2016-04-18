package com.home.xiaoniuniu;

import com.home.xiaoniuniu.InvalidArgumentException;
import com.home.xiaoniuniu.SquareDrawer;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by XiaoNiuniu on 2/19/2016.
 */

public class SquareDrawerTest {

    @Test(expected = InvalidArgumentException.class)
    public void testDrawSquareNagativeSlideLenth() throws InvalidArgumentException {
        SquareDrawer squareDrawer = new SquareDrawer();
        squareDrawer.drawSquare(-1);
    }

    @Test(expected = InvalidArgumentException.class)
    public void testDrawSquareZeroSlideLenth() throws InvalidArgumentException {
        SquareDrawer squareDrawer = new SquareDrawer();
        squareDrawer.drawSquare(0);
    }

    @Test
    public void testDrawSquareOneSlideLength() throws InvalidArgumentException {
        SquareDrawer squareDrawer = new SquareDrawer();
        squareDrawer.drawSquare(1);
        String expectedResult = "X";
        Assert.assertEquals(expectedResult, squareDrawer.toString());
    }

    @Test
    public void testDrawSquare() throws InvalidArgumentException {
        SquareDrawer squareDrawer = new SquareDrawer();
        squareDrawer.drawSquare(4);
        String expectedResult =
                  "XXXX\n"
                + "X  X\n"
                + "X  X\n"
                + "XXXX";
        Assert.assertEquals(expectedResult, squareDrawer.toString());
    }

}
