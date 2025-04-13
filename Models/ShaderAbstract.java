package Models;

public abstract class ShaderAbstract {
		
		public static enum FILLSTYLE {SHADE, FILL, NONE};
		
		/**
		 * Render a filled triangle with a solid color. The color is selected from the first vertex listed
		 * for the triangle
		 * @param tri Triangle to be filled/rendered
		 * @param framebuffer Frame buffer to receive rendering
		 */
		public abstract void solidFill(TriangleAbstract tri, int[][][] framebuffer);

		

		/**
		 * Render a Gouraud shaded triangle. The colors for the bilinear interpolatio (Gouraud shading) are
		 * taken from the vertices of the triangle.
		 * @param tri Triangle to be filled/rendered
		 * @param framebuffer Frame buffer to receive rendering
		 */
		public abstract void shadeFill(TriangleAbstract tri, int[][][] framebuffer);

}
