package Models;

/**
 * Provides basic matrix operations used in computer graphics.
 */
public abstract class MatrixAbstract {

	/**
	 * Used to control toString digits and in double valued comparisons
	 */
	protected final static int PRECISION = 10000;
	
	/**
	 * Stores the matrix coefficients
	 */
	protected double[][] m;
	
	/**
	 * Provides get access to the matrix coefficients
	 * @return the matrix coefficients
	 */
	public double[][] getMatrix() {
		return m;
	}
	
	/**
	 * Provides set access to the matrix coefficients
	 * @param the matrix coefficients
	 */
	public void setMatrix(double[][] m) {
		this.m = m.clone();
	}
	
	@Override
	public String toString() {
		String s = "[\n";
		for (int i = 0; i < this.m.length; ++i) {
			for (int j = 0; j < this.m[i].length; ++j) {
				int value = (int)(m[i][j] * PRECISION);
				s += value / (double)PRECISION + "\t";
			}
			s += "\n";
		}
		s += "]";
		return s;
	}
	
	/**
	 * Matrix multiplication
	 * @param m the matrix (right hand operand) to multiply this (the left hand operand)
	 * @return the matrix product
	 * @throws IllegalArgumentException if the matrices are of incompatible shapes
	 */
	
	public abstract MatrixAbstract mult(MatrixAbstract m) throws IllegalArgumentException;
	/**
	 * Matrix inversion
	 * @return the inverse of this
	 * @throws ArithmeticException thrown if the matrix is singular
	 * @throws IllegalArgumentException thrown if the matrix is not square
	 */
	public abstract MatrixAbstract invert() throws ArithmeticException, IllegalArgumentException;
	
	/**
	 * Matrix transpose
	 * @return the tranpose of this
	 */
	public abstract MatrixAbstract transpose();

	/**
	 * Matrix scaling (multiply by a scalar)
	 * @param s the scaling factor
	 * @return the scaled version of this
	 */
	public abstract MatrixAbstract scale(double s);

}
