package com.home.xiaoniuniu;

/**
 * Created by XiaoNiuniu on 2/19/2016.
 */
public class SquareDrawerUtil {

    public static  void printImage(char[][] image) {
        if (image != null) {
            for (int rowCnt = 0; rowCnt < image.length; rowCnt++) {
                for (int colCnt = 0; colCnt < image[rowCnt].length; colCnt++) {
                    System.out.print(image[rowCnt][colCnt]);
                }
                if (rowCnt != (image.length - 1)) {
                    System.out.println("");
                }
            }
        }
    }

    public static String imageToString(char[][] image) {
        if (image == null)
            return "no image";

        StringBuffer result = new StringBuffer();
        for (int rowCnt = 0; rowCnt < image.length; rowCnt++) {
            for (int colCnt = 0; colCnt < image[rowCnt].length; colCnt++) {
                result.append(image[rowCnt][colCnt]);
            }
            if (rowCnt != (image.length - 1)) {
                result.append("\n");
            }
        }

        return result.toString();
    }

    public static int maxLength(int ... slideLengths) {
        int maxLength = -1;
        for (int i = 0; i < slideLengths.length; i++) {
            if (slideLengths[i] > maxLength)
                maxLength = slideLengths[i];
        }
        return maxLength;
    }
}

