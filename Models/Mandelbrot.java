package Models;
//Assignment:
//You will also need to create a class, ComplexNumber,
// to perform operations on complex numbers, at
//least add, multiply, and compute the magnitude

//iterate |z| < 2.0
    //z^2 = z^2 +c
    //where z and c are complex
//Outer border of points render fractal
//On the real x imaginary plane


//computed

//counter = 0;
//while |z| < 2.0 && counter <255
    //z^2=z^2+c
    //counter++//the counter can be used as a pixel value
    //FB[0][i][j] = counter
    //[1]
    //[2]
//colors must be in black white or gre when we render
//oritentation does not matter, color will be added next week
//idea, use a generate method, use a render method, call them

import Tools.ReadWriteImage;

import java.io.IOException;

public class Mandelbrot
{
    private final int width = 800;
    private final int height = 800;
    private final int maxIter = 255;
    private int[][] frameBuffer = new int[height][width];

    public Mandelbrot()
    {
    }

    public Mandelbrot generate()
    {
        double realMin = -2.5, realMax = 1.5, imagMin = -2.0, imagMax = 2.0; //Just setting the bounds

        int i;
        int j;

        for(i = 0; i < height; i++)
        {
            for(j = 0; j < width; j++)
            {
                double real = realMin + (i / (double) width) * (realMax - realMin);
                double imag = imagMax - (j / (double) height) * (imagMax - imagMin); // invert y

                ComplexNumber c = new ComplexNumber(real, imag);
                ComplexNumber z = new ComplexNumber(0, 0);

                int count = 0;
                while (z.mag() <= 2.0 && count < maxIter) {
                    z = z.mult(z).add(c);
                    count++;
                }

                frameBuffer[i][j] = count;
            }
    }


        return this;
    }

    public Mandelbrot render()
    {
        int[][][] image = new int[3][height][width];  // [RGB][y][x]

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = 255 - frameBuffer[y][x];  // invert: black = in set

                image[0][y][x] = value; // Red
                image[1][y][x] = value; // Green
                image[2][y][x] = value; // Blue
            }
        }

        try {
            ReadWriteImage.writeImage(image, "mandelbrot.png");
            System.out.println("Mandelbrot image saved using ReadWriteImage.");
        } catch (IOException e) {
            System.err.println("Failed to save image: " + e.getMessage());
        }

        return this;
    }
}
