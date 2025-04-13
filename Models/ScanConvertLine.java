package Models;

import java.util.ArrayList;

//I would like to acknowledge that the idea behind color interpolation in this code was largely inspired by this article
//https://www.alanzucconi.com/2016/01/06/colour-interpolation/
public class ScanConvertLine extends ScanConvertAbstract
{
    public void digitaldifferentialanalyzer(int x0, int y0, int x1, int y1, int[][][] framebuffer) throws NullPointerException, ArrayIndexOutOfBoundsException
    {
        try
        {
            double slope = (double) (y1 - y0) / (x1 - x0);
            if (Math.abs(slope) > 1) {
                int x = x0;
                for (int y = Math.min(y0, y1); y <= Math.max(y0, y1); y++) {
                    x = (int) (x0 + (y - y0) / slope);
                    framebuffer[0][y][x] = 255;
                    framebuffer[1][y][x] = 255;
                    framebuffer[2][y][x] = 255;
                }
            } else {
                int y = y0;
                for (int x = Math.min(x0, x1); x <= Math.max(x0, x1); x++) {
                    y = (int) (slope * (x - x0) + y0);
                    framebuffer[0][y][x] = 255;
                    framebuffer[1][y][x] = 255;
                    framebuffer[2][y][x] = 255;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {}
    }

    public void bresenham(int x0, int y0, int x1, int y1, ColorAbstract c0, ColorAbstract c1, int framebuffer[][][])  throws NullPointerException, ArrayIndexOutOfBoundsException
    {
        /// new
        // Get framebuffer dimensions
        int width = framebuffer[0][0].length;
        int height = framebuffer[0].length;
        // Add bounds checking
        if (x0 < 0 || x0 >= width || y0 < 0 || y0 >= height ||
                x1 < 0 || x1 >= width || y1 < 0 || y1 >= height) {
            // Skip this line if it's out of bounds
            return;
            /// new
        }
        if (c0 == null) c0 = new Color(1.0, 1.0, 0.0); // Default to white
        if (c1 == null) c1 = new Color(1.0, 1.0, 0.0);

        int dx = Math.abs(x1 - x0);//Run, difference inx
        int dy = Math.abs(y1 - y0);//Rise, difference in y

        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        //Here we calculate the pixels in the line
        int pixelCount  = Math.max(dx, dy);

        //We need to extract the RGB values
        double r0 = c0.getR();
        double g0 = c0.getG();
        double b0 = c0.getB();
        double r1 = c1.getR();
        double g1 = c1.getG();
        double b1 = c1.getB();


        //while (true) was swapped out for a loop
        int i = 0;
        while (i < pixelCount)
        {
            double j = (double) i / pixelCount;//cast to double

            //interpolation
           int r = (int)((r0+j*(r1-r0))*255);
           int g = (int)((g0+j*(g1-g0))*255);
           int b = (int)((b0+j*(b1-b0))*255);

            framebuffer[0][y0][x0] = r;
            framebuffer[1][y0][x0] = g;
            framebuffer[2][y0][x0] = b;

            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;

            if (e2 > -dy)
            {
                err -= dy;
                x0 += sx;
            }

            if (e2 < dx)
            {
                err += dx;
                y0 += sy;
            }
            i++;
        }
    }

    @Override
    public void bresenham(int x0, int y0, int x1, int y1, Color c0, Color c1, ArrayList<VectorAbstract> points)
    {
        //2 Step Curves
        //ArrayList of Vector abstract to hold points then send them to FB
        //We get to create main hahaha, we should generate hermite curve into array list and a bezier into array list
        //We then call old bresenham to render into FB after getting bezier and hermite curve points

        //bezier hermite generate points
        //render arraylist of points to fb using old bresenham

        //we get to select p0 1 2 3 for bezeoer ad b0 1 2 3 for hermite

        //Hermite
            //2 points (endpoints)
            //2 vectors (continuity) tangent
                //4 values here used to get a.b.c.d coeff, u=0 up to u=1 with aggregat of 0.5 using bresenham's lines

        //Bezier
            //2 points (endpoints)
            //2 points (knots) gravity
                //4 values here used to get a.b.c.d

        //Useful functions
            //SetCoefs( ) //compute coeff and store them somewhere
            //generatePoints(ArrayList<VectorAbstract>points)//loops through new bresenham to put points into array list, loops through U values

    }
}
