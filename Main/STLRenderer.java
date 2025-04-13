package Main;

import Tools.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import Models.*;

public class STLRenderer {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int BUFFER_SIZE = 256; // Size of the 3D framebuffer

    public static void main(String[] args) {
        // Path to the STL file
        String filePath = "C:\\Users\\socce\\IdeaProjects\\Graphics Pipeline\\src\\Assets\\cube-ascii.stl";

        try {
            // Create a 3D framebuffer (RGB)
            int[][][] framebuffer = new int[3][BUFFER_SIZE][BUFFER_SIZE];
            clearFramebuffer(framebuffer);

            // Parse the STL file
            List<TriangleAbstract> triangles = STLParser.parseSTLFile(Path.of(filePath));
            System.out.println("Loaded " + triangles.size() + " triangles from STL file");

            // Create a scene object and add all triangles
            SceneObject sceneObject = new SceneObject();
            for (TriangleAbstract triangle : triangles) {
                sceneObject.addTriangle(triangle);
            }

            // Center the object in the viewport
            centerObject(sceneObject, BUFFER_SIZE, BUFFER_SIZE);

            VectorAbstract cubeCenter = new Vector(0, 0, 0); // Assume cube center is at (0, 0, 0)
            for (TriangleAbstract triangle : triangles) {
                VectorAbstract normal = triangle.getNormal();
                VectorAbstract triangleCenter = triangle.getCenter();
            }

            // Set up a viewpoint for rendering
            VectorAbstract viewpoint = new Vector(0, 0, -1, null);

            // Render the scene object
            sceneObject.render(framebuffer, false, ShaderAbstract.FILLSTYLE.SHADE, viewpoint);

            // Convert the framebuffer to a BufferedImage
            BufferedImage image = ReadWriteImage.toBI(framebuffer);

            // Save the image as PNG file
            String outputPath = "output_image.png"; // You can specify a different file path
            ReadWriteImage.writeImage(framebuffer, outputPath);
            System.out.println("Image saved to " + outputPath);

        } catch (IOException e) {
            System.err.println("Error loading STL file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void clearFramebuffer(int[][][] framebuffer) {
        for (int i = 0; i < framebuffer.length; i++) {
            for (int j = 0; j < framebuffer[i].length; j++) {
                for (int k = 0; k < framebuffer[i][j].length; k++) {
                    framebuffer[i][j][k] = 0; // Set all values to 0 (black)
                }
            }
        }
    }

    private static void centerObject(SceneObject sceneObject, int width, int height) {
        // Get the current center and extents of the object
        VectorAbstract[] extents = sceneObject.getExtents();

        // Calculate scaling factor to fit object in viewport - use a smaller factor
        double xRange = Math.abs(extents[1].getX() - extents[0].getX());
        double yRange = Math.abs(extents[1].getY() - extents[0].getY());
        double zRange = Math.abs(extents[1].getZ() - extents[0].getZ());

        double maxRange = Math.max(Math.max(xRange, yRange), zRange);
        // Reduce the scaling factor to ensure it fits within the buffer
        double scaleFactor = (width * 0.5) / maxRange; // Changed from 0.7 to 0.5

        // Scale the object
        sceneObject.scale(new Vector(scaleFactor, scaleFactor, scaleFactor, null));

        // Center the object in the viewport
        VectorAbstract center = sceneObject.getCenter();
        VectorAbstract translation = new Vector(
                width / 2 - center.getX(),
                height / 2 - center.getY(),
                0, // Keep Z as is
                null
        );

        sceneObject.translate(translation);

        // Use smaller rotation angles
        sceneObject.rotate(new Vector(1, 0, 0, null), 15); // Changed from 20 to 15
        sceneObject.rotate(new Vector(0, 1, 0, null), 30); // Changed from 45 to 30
    }
}
