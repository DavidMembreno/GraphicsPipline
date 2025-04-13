package Main;

import Models.*;
import Tools.ReadWriteImage;
import java.io.IOException;
import java.util.ArrayList;

public class CurveTest {
    // Helper method to draw a point in the framebuffer
    private static void drawPoint(int[][][] framebuffer, int x, int y, int size, int r, int g, int b) {
        try {
            for (int i = -size/2; i <= size/2; i++) {
                for (int j = -size/2; j <= size/2; j++) {
                    framebuffer[0][y + j][x + i] = r;
                    framebuffer[1][y + j][x + i] = g;
                    framebuffer[2][y + j][x + i] = b;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Ignore out of bounds points
        }
    }

    // Helper method to draw a line without interpolation in the framebuffer
    private static void drawLine(int[][][] framebuffer, int x0, int y0, int x1, int y1, int r, int g, int b) {
        ScanConvertLine scl = new ScanConvertLine();
        Color color = new Color(r/255.0, g/255.0, b/255.0);
        try {
            scl.bresenham(x0, y0, x1, y1, color, color, framebuffer);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Ignore out of bounds lines
        }
    }

    public static void main(String[] args) {
        // Initialize framebuffer
        int width = 800;
        int height = 600;
        int[][][] framebuffer = new int[3][height][width];

        // Clear the framebuffer (set to black)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                framebuffer[0][y][x] = 0;  // R
                framebuffer[1][y][x] = 0;  // G
                framebuffer[2][y][x] = 0;  // B
            }
        }

        // Step 1: Create ArrayLists to store the curve points
        ArrayList<VectorAbstract> hermitePoints = new ArrayList<>();
        ArrayList<VectorAbstract> bezierPoints = new ArrayList<>();

        // Create a curves object
        Curves curves = new Curves();

        // Define control points for Hermite curve
        VectorAbstract h_p0 = new Vector(100, 300, 0);  // Start point
        VectorAbstract h_p1 = new Vector(700, 300, 0);  // End point
        VectorAbstract h_r0 = new Vector(300, -200, 0); // Start tangent
        VectorAbstract h_r1 = new Vector(300, 200, 0);  // End tangent

        // Define control points for Bezier curve
        VectorAbstract b_p0 = new Vector(100, 500, 0);  // Start point
        VectorAbstract b_p1 = new Vector(300, 100, 0);  // Control point 1
        VectorAbstract b_p2 = new Vector(500, 100, 0);  // Control point 2
        VectorAbstract b_p3 = new Vector(700, 500, 0);  // End point

        // Define colors
        Color redColor = new Color(1.0, 0.0, 0.0);    // Red
        Color blueColor = new Color(0.0, 0.0, 1.0);   // Blue
        Color purpleColor = new Color(1.0, 0.0, 1.0); // Purple
        Color greenColor = new Color(0.0, 1.0, 0.0);  // Green

        // Generate Hermite curve points
        System.out.println("Generating Hermite curve points...");
        curves.generateHermiteCurve(h_p0, h_p1, h_r0, h_r1, redColor, blueColor, 50, hermitePoints);
        System.out.println("Generated " + hermitePoints.size() + " Hermite curve points");

        // Generate Bezier curve points
        System.out.println("Generating Bezier curve points...");
        curves.generateBezierCurve(b_p0, b_p1, b_p2, b_p3, purpleColor, greenColor, 50, bezierPoints);
        System.out.println("Generated " + bezierPoints.size() + " Bezier curve points");

        // Step 2: Render the points to the framebuffer
        ScanConvertLine scl = new ScanConvertLine();

        // Render Hermite curve
        System.out.println("Rendering Hermite curve...");
        for (int i = 0; i < hermitePoints.size() - 1; i++) {
            VectorAbstract p1 = hermitePoints.get(i);
            VectorAbstract p2 = hermitePoints.get(i + 1);

            // Get colors
            ColorAbstract c1 = p1.getColor();
            ColorAbstract c2 = p2.getColor();

            // Render to framebuffer using the "old" Bresenham method
            scl.bresenham(
                    (int)p1.getX(), (int)p1.getY(),
                    (int)p2.getX(), (int)p2.getY(),
                    c1, c2, framebuffer
            );
        }

        // Render Bezier curve
        System.out.println("Rendering Bezier curve...");
        for (int i = 0; i < bezierPoints.size() - 1; i++) {
            VectorAbstract p1 = bezierPoints.get(i);
            VectorAbstract p2 = bezierPoints.get(i + 1);

            // Get colors
            ColorAbstract c1 = p1.getColor();
            ColorAbstract c2 = p2.getColor();

            // Render to framebuffer using the "old" Bresenham method
            scl.bresenham(
                    (int)p1.getX(), (int)p1.getY(),
                    (int)p2.getX(), (int)p2.getY(),
                    c1, c2, framebuffer
            );
        }

        // Visualize control points and structures

        // Draw Hermite control points
        drawPoint(framebuffer, (int)h_p0.getX(), (int)h_p0.getY(), 6, 255, 255, 0);  // Yellow
        drawPoint(framebuffer, (int)h_p1.getX(), (int)h_p1.getY(), 6, 255, 255, 0);  // Yellow

        // Draw Hermite tangent vectors
        drawLine(framebuffer, (int)h_p0.getX(), (int)h_p0.getY(),
                (int)(h_p0.getX() + h_r0.getX()/3), (int)(h_p0.getY() + h_r0.getY()/3),
                255, 255, 255);  // White

        drawLine(framebuffer, (int)h_p1.getX(), (int)h_p1.getY(),
                (int)(h_p1.getX() + h_r1.getX()/3), (int)(h_p1.getY() + h_r1.getY()/3),
                255, 255, 255);  // White

        // Draw Bezier control points
        drawPoint(framebuffer, (int)b_p0.getX(), (int)b_p0.getY(), 6, 255, 255, 0);  // Yellow
        drawPoint(framebuffer, (int)b_p1.getX(), (int)b_p1.getY(), 6, 255, 255, 0);  // Yellow
        drawPoint(framebuffer, (int)b_p2.getX(), (int)b_p2.getY(), 6, 255, 255, 0);  // Yellow
        drawPoint(framebuffer, (int)b_p3.getX(), (int)b_p3.getY(), 6, 255, 255, 0);  // Yellow

        // Draw Bezier control structure
        drawLine(framebuffer, (int)b_p0.getX(), (int)b_p0.getY(),
                (int)b_p1.getX(), (int)b_p1.getY(),
                255, 255, 255);  // White

        drawLine(framebuffer, (int)b_p1.getX(), (int)b_p1.getY(),
                (int)b_p2.getX(), (int)b_p2.getY(),
                255, 255, 255);  // White

        drawLine(framebuffer, (int)b_p2.getX(), (int)b_p2.getY(),
                (int)b_p3.getX(), (int)b_p3.getY(),
                255, 255, 255);  // White

        // Save the image
        try {
            System.out.println("Saving image...");
            ReadWriteImage.writeImage(framebuffer, "curves.png");
            System.out.println("Image saved as curves.png");
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }
}