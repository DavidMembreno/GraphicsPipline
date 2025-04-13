package Models;


public abstract class AffineTransformationAbstract {
	

	/**
	 * Rotate matrix data about the X axis
	 * @param theta angle of rotation in radians
	 * @param fixedpoint fixed point for rotation
	 * @param data data points to be rotated
	 * @return rotated data point in 4 x N homogenous coordinates
	 */
	public abstract MatrixAbstract rotateX(double theta,  VectorAbstract fixedpoint, MatrixAbstract data);

	/**
	 * Rotate matrix data about the Y axis
	 * @param theta angle of rotation in radians
	 * @param fixedpoint fixed point for rotation
	 * @param data data points to be rotated
	 * @return rotated data point in 4 x N homogenous coordinates
	 */
	public abstract MatrixAbstract rotateY(double theta,  VectorAbstract fixedpoint, MatrixAbstract data);
	

	/**
	 * Rotate matrix data about the Z axis
	 * @param theta angle of rotation in radians
	 * @param fixedpoint fixed point for rotation
	 * @param data data points to be rotated
	 * @return rotated data point in 4 x N homogenous coordinates
	 */
	public abstract MatrixAbstract rotateZ(double theta,  VectorAbstract fixedpoint, MatrixAbstract data);


	/**
	 * Translate matrix data 
	 * @param transvec translation distances along each axis
	 * @param data data points to be translated
	 * @return
	 */
	public abstract MatrixAbstract translate(VectorAbstract transvec, MatrixAbstract data);

	
	/**
	 * Scale matrix data points
	 * @param factor scale factors along each axis
	 * @param fixedpoint fixed point for the scaling
	 * @param data data points to be scaled
	 * @return
	 */
	public abstract MatrixAbstract scale (VectorAbstract factor, VectorAbstract fixedpoint, MatrixAbstract data);

	/**
	 * Rotate matrix about an arbitrary axis
	 * @param axis Axis of rotation
	 * @param fixedpoint Fixed point for the rotation
	 * @param arads Angle of rotation in radians
	 * @param data data points to be rotated
	 * @return
	 */
	public abstract MatrixAbstract rotateAxis(VectorAbstract axis, VectorAbstract fixedpoint, double arads, MatrixAbstract data);

}
