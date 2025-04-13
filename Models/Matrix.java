package Models;

public class Matrix extends MatrixAbstract
{

    public Matrix(double[][] m)
    {
        this.m = m;
    }

    @Override
    public MatrixAbstract mult(MatrixAbstract matB) throws IllegalArgumentException {
        double[][] matA = this.m;
        double[][] matBArray = matB.getMatrix();
        if (matA[0].length != matBArray.length) {
            throw new IllegalArgumentException("Incompatible matrix dimensions");
        }
        double[][] result = new double[matA.length][matBArray[0].length];
        for (int i = 0; i < matA.length; i++) {
            for (int j = 0; j < matBArray[0].length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < matA[0].length; k++) {
                    result[i][j] += matA[i][k] * matBArray[k][j];
                }
            }
        }
        return new Matrix(result);
    }

    @Override
    public MatrixAbstract scale(double s) {
        double[][] scaledMatrix = new double[m.length][m[0].length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                scaledMatrix[i][j] = m[i][j] * s;
            }
        }
        return new Matrix(scaledMatrix);
    }

    @Override
    public MatrixAbstract transpose()
    {
        double[][] transposedMatrix = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                transposedMatrix[j][i] = m[i][j];
            }
        }
        return new Matrix(transposedMatrix);
    }







    @Override
    public MatrixAbstract invert() throws ArithmeticException, IllegalArgumentException {
        if (m.length != m[0].length)
        {
            throw new IllegalArgumentException("Matrix must be square for inversion.");
        }

        int n = m.length;
        double[][] augmentedMatrix = new double[n][2 * n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = m[i][j];
                augmentedMatrix[i][j + n] = (i == j) ? 1.0 : 0.0;
            }
        }

        for (int i = 0; i < n; i++) {
            // Ensure the diagonal element is non-zero
            if (augmentedMatrix[i][i] == 0) {
                int swapRow = -1;
                for (int j = i + 1; j < n; j++) {
                    if (augmentedMatrix[j][i] != 0) {
                        swapRow = j;
                        break;
                    }
                }
                if (swapRow == -1) {
                    throw new ArithmeticException("Matrix cannot be inverted.");
                }

                double[] temp = augmentedMatrix[i];
                augmentedMatrix[i] = augmentedMatrix[swapRow];
                augmentedMatrix[swapRow] = temp;
            }

            double pivot = augmentedMatrix[i][i];
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[i][j] /= pivot;
            }

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double scale = augmentedMatrix[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmentedMatrix[j][k] -= augmentedMatrix[i][k] * scale;
                    }
                }
            }
        }

        // Extract the inverted matrix
        double[][] invertedMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                invertedMatrix[i][j] = augmentedMatrix[i][j + n];
            }
        }

        return new Matrix(invertedMatrix);
    }

}
