package Models;

public class AffineTransformation extends AffineTransformationAbstract {
    @Override
    public MatrixAbstract rotateX(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        Matrix originMatrix = new Matrix(new double[][]{
                {1, 0, 0, -fixedpoint.getX()},
                {0, 1, 0, -fixedpoint.getY()},
                {0, 0, 1, -fixedpoint.getZ()},
                {0, 0, 0, 1}});

        Matrix rotationMatrix = new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, Math.cos(theta), -Math.sin(theta), 0},
                {0, Math.sin(theta), Math.cos(theta), 0},
                {0, 0, 0, 1}});

        Matrix originReturnMatrix = new Matrix(new double[][]{
                {1, 0, 0, fixedpoint.getX()},
                {0, 1, 0, fixedpoint.getY()},
                {0, 0, 1, fixedpoint.getZ()},
                {0, 0, 0, 1}});

        return (Matrix) originReturnMatrix.mult(rotationMatrix.mult(originMatrix.mult(data)));
    }

    @Override
    public MatrixAbstract rotateY(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        Matrix originMatrix = new Matrix(new double[][]{
                {1, 0, 0, -fixedpoint.getX()},
                {0, 1, 0, -fixedpoint.getY()},
                {0, 0, 1, -fixedpoint.getZ()},
                {0, 0, 0, 1}});

        Matrix rotationMatrix = new Matrix(new double[][]{
                {Math.cos(theta), 0, Math.sin(theta), 0},
                {0, 1, 0, 0},
                {-Math.sin(theta), 0, Math.cos(theta), 0},
                {0, 0, 0, 1}});

        Matrix originReturnMatrix = new Matrix(new double[][]{
                {1, 0, 0, fixedpoint.getX()},
                {0, 1, 0, fixedpoint.getY()},
                {0, 0, 1, fixedpoint.getZ()},
                {0, 0, 0, 1}});

        return (Matrix) originReturnMatrix.mult(rotationMatrix.mult(originMatrix.mult(data)));
    }

    @Override
    public MatrixAbstract rotateZ(double theta, VectorAbstract fixedpoint, MatrixAbstract data) {
        Matrix originMatrix = new Matrix(new double[][]{
                {1, 0, 0, -fixedpoint.getX()},
                {0, 1, 0, -fixedpoint.getY()},
                {0, 0, 1, -fixedpoint.getZ()},
                {0, 0, 0, 1}});

        Matrix rotationMatrix = new Matrix(new double[][]{
                {Math.cos(theta), -Math.sin(theta), 0, 0},
                {Math.sin(theta), Math.cos(theta), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}});

        Matrix originReturnMatrix = new Matrix(new double[][]{
                {1, 0, 0, fixedpoint.getX()},
                {0, 1, 0, fixedpoint.getY()},
                {0, 0, 1, fixedpoint.getZ()},
                {0, 0, 0, 1}});

        return (Matrix) originReturnMatrix.mult(rotationMatrix.mult(originMatrix.mult(data)));
    }

    @Override
    public MatrixAbstract translate(VectorAbstract transvec, MatrixAbstract data) {
        Matrix translation = new Matrix(new double[][]{
                {1, 0, 0, transvec.getX()},
                {0, 1, 0, transvec.getY()},
                {0, 0, 1, transvec.getZ()},
                {0, 0, 0, 1}});
        return (Matrix) translation.mult(data);
    }

    @Override
    public MatrixAbstract scale(VectorAbstract factor, VectorAbstract fixedpoint, MatrixAbstract data) {
        Matrix originMatrix = new Matrix(new double[][]{
                {1, 0, 0, -fixedpoint.getX()},
                {0, 1, 0, -fixedpoint.getY()},
                {0, 0, 1, -fixedpoint.getZ()},
                {0, 0, 0, 1}});

        Matrix scalingMatrix = new Matrix(new double[][]{
                {factor.getX(), 0, 0, 0},
                {0, factor.getY(), 0, 0},
                {0, 0, factor.getZ(), 0},
                {0, 0, 0, 1}});

        Matrix originReturnMatrix = new Matrix(new double[][]{
                {1, 0, 0, fixedpoint.getX()},
                {0, 1, 0, fixedpoint.getY()},
                {0, 0, 1, fixedpoint.getZ()},
                {0, 0, 0, 1}});

        return (Matrix) originReturnMatrix.mult(scalingMatrix.mult(originMatrix.mult(data)));
    }

    @Override
    public MatrixAbstract rotateAxis(VectorAbstract axis, VectorAbstract fixedpoint, double arads, MatrixAbstract data) {
        double ax = axis.getX();
        double ay = axis.getY();
        double az = axis.getZ();
        double length = Math.sqrt(ax*ax + ay*ay + az*az);

        // Normalize axis
        ax /= length;
        ay /= length;
        az /= length;

        if (Math.abs(ax - 1.0) < 0.0001 && Math.abs(ay) < 0.0001 && Math.abs(az) < 0.0001) {
            return rotateX(arads, fixedpoint, data);
        }

        if (Math.abs(ax) < 0.0001 && Math.abs(ay - 1.0) < 0.0001 && Math.abs(az) < 0.0001) {
            return rotateY(arads, fixedpoint, data);
        }

        if (Math.abs(ax) < 0.0001 && Math.abs(ay) < 0.0001 && Math.abs(az - 1.0) < 0.0001) {
            return rotateZ(arads, fixedpoint, data);
        }

        double d = Math.sqrt(ax*ax + az*az);

        // Translate to fixed point
        Matrix originMatrix = new Matrix(new double[][]{
                {1, 0, 0, -fixedpoint.getX()},
                {0, 1, 0, -fixedpoint.getY()},
                {0, 0, 1, -fixedpoint.getZ()},
                {0, 0, 0, 1}});


        Matrix thetaX = new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, az/d, -ay/d, 0},
                {0, ay/d, az/d, 0},
                {0, 0, 0, 1}});

        Matrix thetaY = new Matrix(new double[][]{
                {d, 0, -ax, 0},
                {0, 1, 0, 0},
                {ax, 0, d, 0},
                {0, 0, 0, 1}});

        // Rotation around Z-axis
        Matrix theta = new Matrix(new double[][]{
                {Math.cos(arads), -Math.sin(arads), 0, 0},
                {Math.sin(arads), Math.cos(arads), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}});

        // Undo Y-axis rotation
        Matrix invThetaY = new Matrix(new double[][]{
                {d, 0, ax, 0},
                {0, 1, 0, 0},
                {-ax, 0, d, 0},
                {0, 0, 0, 1}});

        // Undo X-axis rotation
        Matrix invThetaX = new Matrix(new double[][]{
                {1, 0, 0, 0},
                {0, az/d, ay/d, 0},
                {0, -ay/d, az/d, 0},
                {0, 0, 0, 1}});

        // Translate back to original position
        Matrix originReturnMatrix = new Matrix(new double[][]{
                {1, 0, 0, fixedpoint.getX()},
                {0, 1, 0, fixedpoint.getY()},
                {0, 0, 1, fixedpoint.getZ()},
                {0, 0, 0, 1}});

        return (Matrix) originReturnMatrix.mult(invThetaX.mult(invThetaY.mult(theta.mult(thetaY.mult(thetaX.mult(originMatrix.mult(data)))))));
    }

    //R = Rx (−θ x )R y (−θ y )Rz (θ )R y (θ y )Rx (θx ).

}
