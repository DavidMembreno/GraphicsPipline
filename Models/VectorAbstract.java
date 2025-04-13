package Models;


/**
 * Abstract class for creating a 3D Vector class
 * The vector will be represented by one point. The other is assumed to be (0, 0, 0)
 */
public abstract class VectorAbstract {

	/**
	 * The vector is stored as a direction vector (not tail and head form.) This will
	 * simplify manipulations to be performed later.
	 * 
	 * x, y, z coordinates of the direction vector.
	 */
	protected double x;
	protected double y;
	protected double z;
	
	/**
	 * RGB color assigned to this coordinate
	 */
	protected Color color;
	
	/**
	 * Compute the angle between two vectors, this and the argument
	 * @param v2 the second vector
	 * @return the angle between the two vectors in radians
	 */
	public abstract double angleBetween(VectorAbstract v2);

	/**
	 * Compute the dot product of two vectors, this and the argument
	 * @param v2 the second vector
	 * @return the dot product of the two vectors
	 */
	public abstract double dot(VectorAbstract v2);

	/**
	 * Compute the cross product of two vectors, this and the argument
	 * @param v2 the second vector
	 * @return the cross product of the two vectors
	 */
	public abstract VectorAbstract cross(VectorAbstract v2);

	/**
	 * Compute the unit vector of this
	 * @return the unit vector of this
	 */
	public abstract VectorAbstract unit();

	public abstract double length();

	public abstract VectorAbstract add(VectorAbstract v2);

	public abstract VectorAbstract sub(VectorAbstract v2);

	public abstract VectorAbstract mult(double scale);


	public VectorAbstract() {
		super();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	@Override
	public String toString() {
	    return "[" + x + ", " + y + ", " + z + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
	    if (obj == this) {
	        return true;
	    }
	    if (obj instanceof VectorAbstract) {
	        VectorAbstract v = (Vector) obj;
	        return (x == v.x) && (y == v.y) && (z == v.z);
	    }
	    return false;
	}

}