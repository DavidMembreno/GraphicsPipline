package Models;

import java.util.ArrayList;


/**
 * A set of triangles (TriangleAbstract) that comprise a single object
 */
public abstract class SceneObjectAbstract {

	/**
	 * List of triangles
	 */
	protected ArrayList<TriangleAbstract> triangles;
	
	/** 
	 * Default constructor for internal use
	 */
	protected SceneObjectAbstract() {
		super();
	}

	/**
	 * Rotate each of the triangles in the object
	 * The center of the object is used as the fixed point
	 * @param axis Axis of rotation
	 * @param theta Angle of rotation in degrees
	 */
	public abstract void rotate(VectorAbstract axis, double theta);

	/**
	 * Translate each of the triangles in the object
	 * @param trans Translation distances in X, Y, and Z
	 */
	public abstract void translate(VectorAbstract trans);

	/**
	 * Scale each of the triangles in the object
	 * The center of the object is used as the fixed point
	 * @param scale
	 */
	public abstract void scale(VectorAbstract scale);

	/**
	 * Render each of the triangles into the frame buffer 
	 * Hidden surface removal is performed based on the provided viewpoint. That is, a
	 * triangle may not be rendered if it is not a visible surface
	 * @param framebuffer The framebuffer receiving the rendering
	 * @param shownormals Show the normal of each triangle if true
	 * @param fill Shader style
	 * @param viewpoint The direction from which the object is being viewed (for hidden surface removal)
	 */
	public abstract void render(int[][][] framebuffer, boolean shownormals, ShaderAbstract.FILLSTYLE fill, VectorAbstract viewpoint);

	/**
	 * Returns the center of the object (average of the vertices of the triangles that make up the object
	 * @return The X, Y, and Z centers
	 */
	public abstract VectorAbstract getCenter();
	
	/**
	 * Returns the extents of the object -- minimum (x, y, z) and maximum (x, y, z)
	 * @return
	 */
	public abstract VectorAbstract[] getExtents();

}