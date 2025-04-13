package Models;


/**
 * Abstract class for creating a Triangle class
 */
public abstract class TriangleAbstract {

	/**
	 * An array of vertices (which can be treated as points since VectorAbstract stores
	 * a vector as a direction vector (not tail and head), should be size 3.
	 */
	protected VectorAbstract[] vertices;
	
	/**
	 * Compute the center of the triangle (average of the 3 vertices)
	 * @return the central point of the triangle
	 */
	public abstract VectorAbstract getCenter();

	/**
	 * Compute the perimeter of the triangle
	 * @return the length of the perimeter
	 */
	public abstract double getPerimeter();

	/** 
	 * Compute the area of the triangle
	 * @return the area
	 */
	public abstract double getArea();
	
	/**
	 * Compute the normal vector of the triangle. This can be anchored at any vertex
	 * @return the normal vector as a unit vector (normalized)
	 */
	public abstract VectorAbstract getNormal();
	
	/**
	 * Renders the triangle to the frame buffer
	 * @param framebuffer frame buffer to receive render
	 * @param shownormal show surface normal or not
	 * @param fill FILLSTYLE.SHADE for a shaded fill, FILLSTYLE.FILL for a solid fill, FILLSTYLE.NONE for no fill
	 * @param viewpoint Location from which the triangle is being viewed (for visibility determination)
	 */
	public abstract void render(int[][][] framebuffer, boolean shownormal, Shader.FILLSTYLE fill, VectorAbstract viewpoint);

	/**
	 * Default constructor for use by the extending class only (can't construct an abstract class)
	 */
	protected TriangleAbstract() {
		super();
	}

	/**
	 * Return the list of triangle vertices
	 * @return the vertices
	 */
	public VectorAbstract[] getVertices() {
		return vertices;
	}
	
	/**
	 * Set the list of triangle vertices
	 * @param vertices the new vertex values
	 */
	public void setVertices (VectorAbstract[] vertices) {
		this.vertices = vertices.clone();
	}
	
	/**
	 * Rotate triangle data about the X axis
	 * @param theta angle of rotation in radians
	 * @fixedpoint fixed point for the rotation
	 * @param data data points to be rotated
	 * @return rotated data points 4 x N homogeneous coordinate points
	 */
	public abstract TriangleAbstract rotateX(double theta, VectorAbstract fixedpoint, TriangleAbstract data);

	/**
	 * Rotate triangle data about the Y axis
	 * @param theta angle of rotation in radians
	 * @fixedpoint fixed point for the rotation
	 * @param data data points to be rotated
	 * @return rotated data points 4 x N homogeneous coordinate points
	 */
	public abstract TriangleAbstract rotateY(double theta, VectorAbstract fixedpoint, TriangleAbstract data);

	/**
	 * Rotate triangle data about the Z axis
	 * @param theta angle of rotation in radians
	 * @fixedpoint fixed point for the rotation
	 * @param data data points to be rotated
	 * @return rotated data points 4 x N homogeneous coordinate points
	 */
	public abstract TriangleAbstract rotateZ(double theta, VectorAbstract fixedpoint, TriangleAbstract data);

	/**
	 * Rotates a triangle about an arbitrary axis
	 * @param axis axis of rotation
	 * @param fixedpoint fixed point for rotation
	 * @param arads angle of rotation in radians
	 * @param data data points to be rotated
	 * @return
	 */
	public abstract TriangleAbstract rotateAxis(VectorAbstract axis, VectorAbstract fixedpoint, double arads,
			TriangleAbstract data);

	/**
	 * Translate triangle data
	 * @param trans vector of translation amounts
	 * @param data data points to be translated
	 * @return translated data points 4 x N homogeneous coordinate points
	 */
	public abstract TriangleAbstract translate(VectorAbstract transvec, TriangleAbstract data);

	/**
	 * Scale triangle data around a specified point
	 * @param factor scale factors (x, y, z)
	 * @fixedpoint fixed point for the rotation
	 * @param data data to be scaled 4xN homogeneous coordinate points
	 * @return scaled data
	 */
	public abstract TriangleAbstract scale (VectorAbstract factor, VectorAbstract fixedpoint, TriangleAbstract data);

	/**
	 * Determines triange visiblity based on viewpoint
	 * @param viewpoint the viewpoint vector
	 * @return true if visible, false otherwise
	 */
	public abstract boolean isVisible(VectorAbstract viewpoint);

	@Override
	public String toString() {
		String s = "[";
		for(VectorAbstract v : vertices){
			s += v;
		}
		s += "]";
		return s;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof TriangleAbstract) {
			TriangleAbstract rhs = (TriangleAbstract)obj;
			for (int i = 0; i < vertices.length; ++i) {
				if (!this.vertices[i].equals(rhs.vertices[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}