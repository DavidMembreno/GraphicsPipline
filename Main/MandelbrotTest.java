package Main;

import Models.Mandelbrot;

public class MandelbrotTest
{
    public static void main(String[] args)
    {
        Mandelbrot m = new Mandelbrot();
        m.generate().render();

    }
}
