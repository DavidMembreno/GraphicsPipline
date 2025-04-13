package Models;

import java.util.ArrayList;

public class Curves extends ScanConvertAbstract {
    private double[] hermiteCoeffs_x = new double[4];
    private double[] hermiteCoeffs_y = new double[4];
    private double[] bezierCoeffs_x = new double[4];
    private double[] bezierCoeffs_y = new double[4];

    @Override
    public void bresenham(int x0, int y0, int x1, int y1, ColorAbstract c0, ColorAbstract c1, int[][][] framebuffer) throws NullPointerException, ArrayIndexOutOfBoundsException {
        // Get framebuffer dimensions
        int width = framebuffer[0][0].length;
        int height = framebuffer[0].length;

        // Add bounds checking
        if (x0 < 0 || x0 >= width || y0 < 0 || y0 >= height ||
                x1 < 0 || x1 >= width || y1 < 0 || y1 >= height) {
            // Skip this line if it's out of bounds
            return;
        }

        if (c0 == null) c0 = new Color(1.0, 1.0, 1.0); // Default to white
        if (c1 == null) c1 = new Color(1.0, 1.0, 1.0);

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        // Here we calculate the pixels in the line
        int pixelCount = Math.max(dx, dy);

        // We need to extract the RGB values
        double r0 = c0.getR();
        double g0 = c0.getG();
        double b0 = c0.getB();
        double r1 = c1.getR();
        double g1 = c1.getG();
        double b1 = c1.getB();

        int i = 0;
        while (i < pixelCount) {
            double j = (pixelCount == 0) ? 0 : (double) i / pixelCount;

            // Interpolation
            int r = (int)((r0 + j * (r1 - r0)) * 255);
            int g = (int)((g0 + j * (g1 - g0)) * 255);
            int b = (int)((b0 + j * (b1 - b0)) * 255);

            framebuffer[0][y0][x0] = r;
            framebuffer[1][y0][x0] = g;
            framebuffer[2][y0][x0] = b;

            if (x0 == x1 && y0 == y1) break;

            int e2 = 2 * err;

            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
            i++;
        }
    }

    @Override
    public void bresenham(int x0, int y0, int x1, int y1, Color c0, Color c1, ArrayList<VectorAbstract> points) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx - dy;

        // Calculate the number of pixels in the line for interpolation
        int pixelCount = Math.max(dx, dy);

        // We need to extract the RGB values
        double r0 = c0.getR();
        double g0 = c0.getG();
        double b0 = c0.getB();
        double r1 = c1.getR();
        double g1 = c1.getG();
        double b1 = c1.getB();

        int i = 0;
        int currentX = x0;
        int currentY = y0;

        while (true) {
            double j = (pixelCount == 0) ? 0 : (double) i / pixelCount;

            // Interpolate color
            double r = r0 + j * (r1 - r0);
            double g = g0 + j * (g1 - g0);
            double b = b0 + j * (b1 - b0);

            // Create a new vector with the point and color and add to ArrayList
            Vector point = new Vector(currentX, currentY, 0);
            Color color = new Color(r, g, b);
            point.setColor(color);
            points.add(point);

            if (currentX == x1 && currentY == y1) break;

            int e2 = 2 * err;

            if (e2 > -dy) {
                err -= dy;
                currentX += sx;
            }

            if (e2 < dx) {
                err += dx;
                currentY += sy;
            }
            i++;
        }
    }

    /**
     * Compute Hermite curve coefficients for x and y coordinates
     *
     * @param p0 Start point
     * @param p1 End point
     * @param r0 Start tangent vector
     * @param r1 End tangent vector
     */
    public void setHermiteCoeffs(VectorAbstract p0, VectorAbstract p1, VectorAbstract r0, VectorAbstract r1) {
        // For x coordinates
        hermiteCoeffs_x[0] = p0.getX();
        hermiteCoeffs_x[1] = r0.getX();
        hermiteCoeffs_x[2] = -3 * p0.getX() - 2 * r0.getX() + 3 * p1.getX() - r1.getX();
        hermiteCoeffs_x[3] = 2 * p0.getX() + r0.getX() - 2 * p1.getX() + r1.getX();

        // For y coordinates
        hermiteCoeffs_y[0] = p0.getY();
        hermiteCoeffs_y[1] = r0.getY();
        hermiteCoeffs_y[2] = -3 * p0.getY() - 2 * r0.getY() + 3 * p1.getY() - r1.getY();
        hermiteCoeffs_y[3] = 2 * p0.getY() + r0.getY() - 2 * p1.getY() + r1.getY();
    }

    /**
     * Compute Bezier curve coefficients for x and y coordinates
     *
     * @param p0 Start point
     * @param p1 First control point
     * @param p2 Second control point
     * @param p3 End point
     */
    public void setBezierCoeffs(VectorAbstract p0, VectorAbstract p1, VectorAbstract p2, VectorAbstract p3) {
        // For x coordinates
        bezierCoeffs_x[0] = p0.getX();
        bezierCoeffs_x[1] = 3 * (p1.getX() - p0.getX());
        bezierCoeffs_x[2] = 3 * (p0.getX() - 2 * p1.getX() + p2.getX());
        bezierCoeffs_x[3] = -p0.getX() + 3 * p1.getX() - 3 * p2.getX() + p3.getX();

        // For y coordinates
        bezierCoeffs_y[0] = p0.getY();
        bezierCoeffs_y[1] = 3 * (p1.getY() - p0.getY());
        bezierCoeffs_y[2] = 3 * (p0.getY() - 2 * p1.getY() + p2.getY());
        bezierCoeffs_y[3] = -p0.getY() + 3 * p1.getY() - 3 * p2.getY() + p3.getY();
    }

    /**
     * Generate points along a Hermite curve and add them to the ArrayList
     *
     * @param p0 Start point
     * @param p1 End point
     * @param r0 Start tangent vector
     * @param r1 End tangent vector
     * @param c0 Start color
     * @param c1 End color
     * @param steps Number of curve segments
     * @param points ArrayList to store the points
     */
    public void generateHermiteCurve(VectorAbstract p0, VectorAbstract p1, VectorAbstract r0, VectorAbstract r1,
                                     Color c0, Color c1, int steps, ArrayList<VectorAbstract> points) {
        // Calculate Hermite coefficients
        setHermiteCoeffs(p0, p1, r0, r1);

        // Generate points along the curve
        for (int i = 0; i < steps; i++) {
            double u1 = (double) i / steps;
            double u2 = (double) (i + 1) / steps;

            // Calculate first point
            double x1 = hermiteCoeffs_x[0] + hermiteCoeffs_x[1] * u1 +
                    hermiteCoeffs_x[2] * u1 * u1 + hermiteCoeffs_x[3] * u1 * u1 * u1;
            double y1 = hermiteCoeffs_y[0] + hermiteCoeffs_y[1] * u1 +
                    hermiteCoeffs_y[2] * u1 * u1 + hermiteCoeffs_y[3] * u1 * u1 * u1;

            // Calculate second point
            double x2 = hermiteCoeffs_x[0] + hermiteCoeffs_x[1] * u2 +
                    hermiteCoeffs_x[2] * u2 * u2 + hermiteCoeffs_x[3] * u2 * u2 * u2;
            double y2 = hermiteCoeffs_y[0] + hermiteCoeffs_y[1] * u2 +
                    hermiteCoeffs_y[2] * u2 * u2 + hermiteCoeffs_y[3] * u2 * u2 * u2;

            // Interpolate colors
            Color color1 = new Color(
                    c0.getR() + u1 * (c1.getR() - c0.getR()),
                    c0.getG() + u1 * (c1.getG() - c0.getG()),
                    c0.getB() + u1 * (c1.getB() - c0.getB())
            );

            Color color2 = new Color(
                    c0.getR() + u2 * (c1.getR() - c0.getR()),
                    c0.getG() + u2 * (c1.getG() - c0.getG()),
                    c0.getB() + u2 * (c1.getB() - c0.getB())
            );

            // Generate line segment between these points using Bresenham
            bresenham((int)Math.round(x1), (int)Math.round(y1),
                    (int)Math.round(x2), (int)Math.round(y2),
                    color1, color2, points);
        }
    }

    /**
     * Generate points along a Bezier curve and add them to the ArrayList
     *
     * @param p0 Start point
     * @param p1 First control point
     * @param p2 Second control point
     * @param p3 End point
     * @param c0 Start color
     * @param c1 End color
     * @param steps Number of curve segments
     * @param points ArrayList to store the points
     */
    public void generateBezierCurve(VectorAbstract p0, VectorAbstract p1, VectorAbstract p2, VectorAbstract p3,
                                    Color c0, Color c1, int steps, ArrayList<VectorAbstract> points) {
        // Calculate Bezier coefficients
        setBezierCoeffs(p0, p1, p2, p3);

        // Generate points along the curve
        for (int i = 0; i < steps; i++) {
            double u1 = (double) i / steps;
            double u2 = (double) (i + 1) / steps;

            // Calculate first point
            double x1 = bezierCoeffs_x[0] + bezierCoeffs_x[1] * u1 +
                    bezierCoeffs_x[2] * u1 * u1 + bezierCoeffs_x[3] * u1 * u1 * u1;
            double y1 = bezierCoeffs_y[0] + bezierCoeffs_y[1] * u1 +
                    bezierCoeffs_y[2] * u1 * u1 + bezierCoeffs_y[3] * u1 * u1 * u1;

            // Calculate second point
            double x2 = bezierCoeffs_x[0] + bezierCoeffs_x[1] * u2 +
                    bezierCoeffs_x[2] * u2 * u2 + bezierCoeffs_x[3] * u2 * u2 * u2;
            double y2 = bezierCoeffs_y[0] + bezierCoeffs_y[1] * u2 +
                    bezierCoeffs_y[2] * u2 * u2 + bezierCoeffs_y[3] * u2 * u2 * u2;

            // Interpolate colors
            Color color1 = new Color(
                    c0.getR() + u1 * (c1.getR() - c0.getR()),
                    c0.getG() + u1 * (c1.getG() - c0.getG()),
                    c0.getB() + u1 * (c1.getB() - c0.getB())
            );

            Color color2 = new Color(
                    c0.getR() + u2 * (c1.getR() - c0.getR()),
                    c0.getG() + u2 * (c1.getG() - c0.getG()),
                    c0.getB() + u2 * (c1.getB() - c0.getB())
            );

            // Generate line segment between these points using Bresenham
            bresenham((int)Math.round(x1), (int)Math.round(y1),
                    (int)Math.round(x2), (int)Math.round(y2),
                    color1, color2, points);
        }
    }
}