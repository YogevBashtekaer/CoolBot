package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ImageProcessing {
    private static Scanner random;
    private BufferedImage bufferedImage;
    final int MAX_COLOR = 255;

    public ImageProcessing(BufferedImage bufferedImage,PhotoOptions photoOptions) {
        switch (photoOptions){
            case MIRROR :
                this.bufferedImage = mirror(bufferedImage);
                break;
            case BLACK_AND_WHITE:
                this.bufferedImage = convertToBlackAndWhite(bufferedImage);
                break;
            case GRAY_SCALE:
                this.bufferedImage = convertToCustomGrayScale(bufferedImage);
                break;
            case SHUFFLE_COLORS:
                this.bufferedImage = sufferedImage(bufferedImage);
                break;
            case OPPOSITE_COLORS:
                this.bufferedImage = oppositeColors(bufferedImage);
                break;
            case DRAW_EDGES:
                this.bufferedImage = drawEdges(bufferedImage);
                break;
            default:
                this.bufferedImage = bufferedImage;
                break;

        }

    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    private BufferedImage mirror(BufferedImage bufferedImage){
        BufferedImage processes = new BufferedImage(bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                int color = bufferedImage.getRGB(i,j);
                Color newColor = new Color(color);
                processes.setRGB(processes.getWidth()-i-1,j,newColor.getRGB());
            }
        }
        return processes;
    }
    private BufferedImage convertToBlackAndWhite(BufferedImage originalImage){
        BufferedImage processedImage = new BufferedImage(originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int color = originalImage.getRGB(x, y);
                int red = (color >> 16) & 0xFF;
                int green = (color >> 8) & 0xFF;
                int blue = color & 0xFF;

                int average = (red + green + blue) / 3;

                if (average > 128) {
                    processedImage.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    processedImage.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        return processedImage;
    }

    private BufferedImage convertToCustomGrayScale(BufferedImage originalImage){
        BufferedImage processedImage = new BufferedImage(originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                int color = originalImage.getRGB(x, y);
                int red = (color >> 16) & 0xFF;
                int green = (color >> 8) & 0xFF;
                int blue = color & 0xFF;

                // Calculate new gray value with a small variation
                int grayValue = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
                grayValue = Math.min(255, Math.max(0, grayValue + (int) (Math.random() * 30 - 15)));

                Color newColor = new Color(grayValue, grayValue, grayValue);
                processedImage.setRGB(x, y, newColor.getRGB());
            }
        }

        return processedImage;
    }
    private BufferedImage sufferedImage(BufferedImage bufferedImage){
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        BufferedImage processed = new BufferedImage(width,
                height,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = new Color(bufferedImage.getRGB(i,j));
                Color newColor = new Color(color.getGreen(),
                        color.getBlue(),
                        color.getRed());
                processed.setRGB(i,j,newColor.getRGB());
            }
        }
        return processed;
    }

    private BufferedImage oppositeColors(BufferedImage bufferedImage){
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        BufferedImage processed = new BufferedImage(width,
                height,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = new Color(bufferedImage.getRGB(i,j));
                Color newColor = new Color(MAX_COLOR - color.getRed(),
                        MAX_COLOR - color.getGreen(),
                        MAX_COLOR - color.getBlue());
                processed.setRGB(i,j,newColor.getRGB());
            }
        }
        return  processed;
    }

    private BufferedImage drawEdges(BufferedImage original){
        int width = original.getWidth();
        int height = original.getHeight();
        BufferedImage outPut = new BufferedImage(width,
                height,
                BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width-1; i++) {
            for (int j = 0; j < height-1; j++) {
                Color curernt = new Color(original.getRGB(i,j));
                Color north = new Color(original.getRGB(i,j+1));
                //       Color south = new Color(original.getRGB(x,y-1));
                //  Color east = new Color(original.getRGB(i-1,j));
                Color west = new Color(original.getRGB(i+1,j));
                if(isDifferent(curernt,north) ||
                        //     isDifferent(curernt,south) ||
                        //     isDifferent(curernt,east) ||
                        isDifferent(curernt,west)){
                    outPut.setRGB(i,j,Color.blue.getRGB());
                }else{
                    outPut.setRGB(i,j,Color.WHITE.getRGB());
                }

            }
        }
        return  outPut;
    }

    private boolean isDifferent(Color color1,Color color2){
        boolean different = false;
        int redDiff = Math.abs(color1.getRed() - color2.getRed());
        int greenDiff = Math.abs(color1.getGreen() - color2.getGreen());
        int blueDiff = Math.abs(color1.getBlue() - color2.getBlue());
        if(redDiff + greenDiff +blueDiff > 30){
            different = true;
        }
        return different;
    }






}
