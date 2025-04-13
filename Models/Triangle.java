package Models;

import java.sql.SQLOutput;

public class Triangle extends TriangleAbstract
{
    //The Triangle Constructor
    public Triangle(VectorAbstract v1, VectorAbstract v2, VectorAbstract v3)
    {   VectorAbstract vertices [] = new VectorAbstract[]{v1,v2,v3};
        setVertices(vertices);
    }

    //Methods
    @Override
    public VectorAbstract getCenter()
    {
        VectorAbstract triangle_sum = vertices[0].add(vertices[1].add(vertices[2]));

        return triangle_sum.mult(1.0/3.0);
    }
    @Override
    public double getPerimeter()
    {
        //For the notes A is vertices[0], B is vertices[1], and C is Vertices[2]
        Vector side1 = (Vector) vertices[1].sub(vertices[0]);//AB = B-A
        Vector side2 = (Vector) vertices[2].sub(vertices[1]);//BC = C-B
        Vector side3 = (Vector) vertices[0].sub(vertices[2]);//CA = A-C
        //Now that we have the side verts we have to get the lengths
        double length1 = side1.length();
        double length2 = side2.length();
        double length3 = side3.length();
        double perimeter = length1 + length2 + length3;

        return perimeter;
    }

    @Override
    public double getArea()
    {
        Vector side1 = (Vector) vertices[1].sub(vertices[0]);//AB = B-A
        Vector side2 = (Vector) vertices[2].sub(vertices[0]);//AC = C-A
        VectorAbstract crossProduct = side1.cross(side2);
        double magnitude = crossProduct.length();
        double area = (0.5*magnitude);
        return area;
    }
    @Override
    public VectorAbstract getNormal()
    {
        Vector side1 = (Vector) vertices[1].sub(vertices[0]);//AB = B-A
        Vector side2 = (Vector) vertices[2].sub(vertices[0]);//AC = C-A
        Vector normalV = (Vector) side1.cross(side2);
        normalV = (Vector) normalV.unit();
        return normalV;
    }
/// new
    private boolean isPointInBounds(int x, int y, int[][][] framebuffer) {
        int width = framebuffer[0][0].length;
        int height = framebuffer[0].length;
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    @Override
    public void render(int[][][] framebuffer, boolean shownormal, ShaderAbstract.FILLSTYLE fill,VectorAbstract viewpoint)
    {
        if (!isVisible(viewpoint)) {
            return; // Skip rendering if it's not visible
        }

        // Create an instance of Shader
        Shader shader = new Shader();

        // Choose the fill method based on the specified fill option
        if (fill == ShaderAbstract.FILLSTYLE.FILL) {
            shader.solidFill(this, framebuffer);
        } else if (fill == ShaderAbstract.FILLSTYLE.SHADE) {
            shader.shadeFill(this, framebuffer);
        }

        //I had to create an instance of ScanConvertLine because bresenham is not static
        ScanConvertLine line = new ScanConvertLine();
        //line 1
        int x0 = (int) vertices[0].getX();
        int y0 = (int) vertices[0].getY();
        //line 2
        int x1 = (int) vertices[1].getX();
        int y1 = (int) vertices[1].getY();
        //line3
        int x2 = (int) vertices[2].getX();
        int y2 = (int) vertices[2].getY();
/// new
        // Check if all points are in bounds
        boolean allPointsInBounds =
                isPointInBounds(x0, y0, framebuffer) &&
                        isPointInBounds(x1, y1, framebuffer) &&
                        isPointInBounds(x2, y2, framebuffer);

        ColorAbstract[] colors = { vertices[0].getColor(), vertices[1].getColor(), vertices[2].getColor() };

        //calling method through instnace
        if(fill != ShaderAbstract.FILLSTYLE.FILL && allPointsInBounds) {
            line.bresenham(x0, y0, x1, y1, colors[0], colors[0], framebuffer);
            line.bresenham(x1, y1, x2, y2, colors[1], colors[1], framebuffer);
            line.bresenham(x2, y2, x0, y0, colors[2], colors[2], framebuffer);
        }
        //From the assignment description:
        if (shownormal) {
            VectorAbstract triangleCenter = getCenter();
            VectorAbstract surfaceNormal = getNormal();

            int scale = 20;

            double normalX = surfaceNormal.getX() * scale;
            double normalY = surfaceNormal.getY() * scale;

            int centerX = (int) triangleCenter.getX();
            int centerY = (int) triangleCenter.getY();

            int normalEndX = centerX + (int) normalX;
            int normalEndY = centerY + (int) normalY;

            // Clamp values to prevent out-of-bounds errors
            normalEndX = Math.max(0, Math.min(255, normalEndX));
            normalEndY = Math.max(0, Math.min(255, normalEndY));
            centerX = Math.max(0, Math.min(255, centerX));
            centerY = Math.max(0, Math.min(255, centerY));

            ColorAbstract normalColor = new Color(0, 1, 0); // Green for normal because it looks cool
            line.bresenham(centerX, centerY, normalEndX, normalEndY, normalColor, normalColor, framebuffer);
        }


    }



    @Override
    public TriangleAbstract rotateX(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformation tnfm = new AffineTransformation();
        Vector v0 = (Vector) data.getVertices()[0];
        Vector v1 = (Vector) data.getVertices()[1];
        Vector v2 = (Vector) data.getVertices()[2];

        // matrix representation of the vertices of the triangle
        Matrix vertexMatrix = new Matrix(new double[][]{
                {v0.getX(), v1.getX(), v2.getX()},
                {v0.getY(), v1.getY(), v2.getY()},
                {v0.getZ(), v1.getZ(), v2.getZ()},
                {1, 1, 1} // Homogeneous coordinates for matrix operations
        });

        Matrix transformedMatrix = (Matrix) tnfm.rotateX(theta, fixedpoint, vertexMatrix);

        // Extract the transformed vertices from the result
        Vector n1 = new Vector(transformedMatrix.getMatrix()[0][0], transformedMatrix.getMatrix()[1][0], transformedMatrix.getMatrix()[2][0], v0.getColor());
        Vector n2 = new Vector(transformedMatrix.getMatrix()[0][1], transformedMatrix.getMatrix()[1][1], transformedMatrix.getMatrix()[2][1], v1.getColor());
        Vector n3 = new Vector(transformedMatrix.getMatrix()[0][2], transformedMatrix.getMatrix()[1][2], transformedMatrix.getMatrix()[2][2], v2.getColor());

        return new Triangle(n1, n2, n3);
    }

    @Override
    public TriangleAbstract rotateY(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformation tnfm = new AffineTransformation();
        Vector v0 = (Vector) data.getVertices()[0];
        Vector v1 = (Vector) data.getVertices()[1];
        Vector v2 = (Vector) data.getVertices()[2];


        Matrix vertexMatrix = new Matrix(new double[][]{
                {v0.getX(), v1.getX(), v2.getX()},
                {v0.getY(), v1.getY(), v2.getY()},
                {v0.getZ(), v1.getZ(), v2.getZ()},
                {1, 1, 1} // Homogeneous coordinates for matrix operations
        });


        Matrix transformedMatrix = (Matrix) tnfm.rotateY(theta, fixedpoint, vertexMatrix);


        Vector n1 = new Vector(transformedMatrix.getMatrix()[0][0], transformedMatrix.getMatrix()[1][0], transformedMatrix.getMatrix()[2][0], v0.getColor());
        Vector n2 = new Vector(transformedMatrix.getMatrix()[0][1], transformedMatrix.getMatrix()[1][1], transformedMatrix.getMatrix()[2][1], v1.getColor());
        Vector n3 = new Vector(transformedMatrix.getMatrix()[0][2], transformedMatrix.getMatrix()[1][2], transformedMatrix.getMatrix()[2][2], v2.getColor());

        return new Triangle(n1, n2, n3);
    }

    @Override
    public TriangleAbstract rotateZ(double theta, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformation tnfm = new AffineTransformation();
        Vector v0 = (Vector) data.getVertices()[0];
        Vector v1 = (Vector) data.getVertices()[1];
        Vector v2 = (Vector) data.getVertices()[2];

        Matrix vertexMatrix = new Matrix(new double[][]{
                {v0.getX(), v1.getX(), v2.getX()},
                {v0.getY(), v1.getY(), v2.getY()},
                {v0.getZ(), v1.getZ(), v2.getZ()},
                {1, 1, 1}
        });

        Matrix transformedMatrix = (Matrix) tnfm.rotateZ(theta, fixedpoint, vertexMatrix);

        Vector n1 = new Vector(transformedMatrix.getMatrix()[0][0], transformedMatrix.getMatrix()[1][0], transformedMatrix.getMatrix()[2][0], v0.getColor());
        Vector n2 = new Vector(transformedMatrix.getMatrix()[0][1], transformedMatrix.getMatrix()[1][1], transformedMatrix.getMatrix()[2][1], v1.getColor());
        Vector n3 = new Vector(transformedMatrix.getMatrix()[0][2], transformedMatrix.getMatrix()[1][2], transformedMatrix.getMatrix()[2][2], v2.getColor());

        return new Triangle(n1, n2, n3);
    }

    @Override
    public TriangleAbstract scale(VectorAbstract factor, VectorAbstract fixedpoint, TriangleAbstract data) {
        AffineTransformation tnfm = new AffineTransformation();
        Vector v0 = (Vector) data.getVertices()[0];
        Vector v1 = (Vector) data.getVertices()[1];
        Vector v2 = (Vector) data.getVertices()[2];

        Matrix vertexMatrix = new Matrix(new double[][]{
                {v0.getX(), v1.getX(), v2.getX()},
                {v0.getY(), v1.getY(), v2.getY()},
                {v0.getZ(), v1.getZ(), v2.getZ()},
                {1, 1, 1}
        });

        Matrix transformedMatrix = (Matrix) tnfm.scale(factor, fixedpoint, vertexMatrix);

        Vector n1 = new Vector(transformedMatrix.getMatrix()[0][0], transformedMatrix.getMatrix()[1][0], transformedMatrix.getMatrix()[2][0], v0.getColor());
        Vector n2 = new Vector(transformedMatrix.getMatrix()[0][1], transformedMatrix.getMatrix()[1][1], transformedMatrix.getMatrix()[2][1], v1.getColor());
        Vector n3 = new Vector(transformedMatrix.getMatrix()[0][2], transformedMatrix.getMatrix()[1][2], transformedMatrix.getMatrix()[2][2], v2.getColor());

        return new Triangle(n1, n2, n3);
    }
    //The isVisible method determines the visibility...
    //...of the triangle based on its surface normal and the viewer location which is specified as an...
    //...argument to the method

    //Perhaps angle is less than -90 or greater than 90
    //Calculate angle between the viewpoint and surface normal
    @Override
    public boolean isVisible(VectorAbstract viewpoint)
    {
        VectorAbstract normalizedVP = viewpoint.unit();
        VectorAbstract normalizedNM = getNormal().unit();
        double angleinR = normalizedNM.angleBetween(normalizedVP);
        double angle = Math.toDegrees(angleinR);//Edit, I think i was measuring radians to degrees at some point
        //System.out.println("Angle between normal and viewpoint: " + angle); gave me pi as output at some point
        // Check if the angle is within the visible range (typically between -90 and 90 degrees)
        if (angle >= -90 && angle <= 90)
        {
            return false; // Visible if the angle is within the range
        }
        else
        {
            return true; // Not visible if the angle is outside the range
        }//swapped my false and true for mor accurate results
    }

    @Override
    public TriangleAbstract translate(VectorAbstract transvec, TriangleAbstract data) {
        AffineTransformation tnfm = new AffineTransformation();
        Vector v0 = (Vector) data.getVertices()[0];
        Vector v1 = (Vector) data.getVertices()[1];
        Vector v2 = (Vector) data.getVertices()[2];

        Matrix vertexMatrix = new Matrix(new double[][]{
                {v0.getX(), v1.getX(), v2.getX()},
                {v0.getY(), v1.getY(), v2.getY()},
                {v0.getZ(), v1.getZ(), v2.getZ()},
                {1, 1, 1}
        });

        Matrix transformedMatrix = (Matrix) tnfm.translate(transvec, vertexMatrix);

        Vector n1 = new Vector(transformedMatrix.getMatrix()[0][0], transformedMatrix.getMatrix()[1][0], transformedMatrix.getMatrix()[2][0], v0.getColor());
        Vector n2 = new Vector(transformedMatrix.getMatrix()[0][1], transformedMatrix.getMatrix()[1][1], transformedMatrix.getMatrix()[2][1], v1.getColor());
        Vector n3 = new Vector(transformedMatrix.getMatrix()[0][2], transformedMatrix.getMatrix()[1][2], transformedMatrix.getMatrix()[2][2], v2.getColor());

        return new Triangle(n1, n2, n3);
    }

    @Override
    public TriangleAbstract rotateAxis(VectorAbstract axis, VectorAbstract fixedpoint, double arads, TriangleAbstract data) {
        AffineTransformation tnfm = new AffineTransformation();
        Vector v0 = (Vector) data.getVertices()[0];
        Vector v1 = (Vector) data.getVertices()[1];
        Vector v2 = (Vector) data.getVertices()[2];

        Matrix vertexMatrix = new Matrix(new double[][]{
                {v0.getX(), v1.getX(), v2.getX()},
                {v0.getY(), v1.getY(), v2.getY()},
                {v0.getZ(), v1.getZ(), v2.getZ()},
                {1, 1, 1}
        });

        Matrix transformedMatrix = (Matrix) tnfm.rotateAxis(axis,fixedpoint,arads,vertexMatrix);
        //System.out.println("New Matrix = " + transformedMatrix);
        //System.out.println("Original Matrix"+ vertexMatrix);
        Vector n1 = new Vector(transformedMatrix.getMatrix()[0][0], transformedMatrix.getMatrix()[1][0], transformedMatrix.getMatrix()[2][0], v0.getColor());
        Vector n2 = new Vector(transformedMatrix.getMatrix()[0][1], transformedMatrix.getMatrix()[1][1], transformedMatrix.getMatrix()[2][1], v1.getColor());
        Vector n3 = new Vector(transformedMatrix.getMatrix()[0][2], transformedMatrix.getMatrix()[1][2], transformedMatrix.getMatrix()[2][2], v2.getColor());
        return new Triangle(n1, n2, n3);
    }
}
