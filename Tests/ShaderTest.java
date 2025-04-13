package Tests;

import java.io.IOException;

import Models.*;
import Tools.ReadWriteImage;


public class ShaderTest {

	public static void main (String[] args) {
		int[][][] framebuffer = new int[3][512][512];
		// -- set the view point
		VectorAbstract viewpoint = new Vector(0, 0, -1, new Color(0, 0, 0));
		
		VectorAbstract v0 = new Vector(100, 100, 0, new Color(0, 0, 1));
		VectorAbstract v1 = new Vector(200, 100, 0, new Color(1, 0, 0));
		VectorAbstract v2 = new Vector(150, 200, 0, new Color(0, 1, 0));
		TriangleAbstract t = new Triangle(v0, v1, v2);
		t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.SHADE, viewpoint);
		
		v0 = new Vector(250, 100, 0, new Color(0, 0, 1));
		v1 = new Vector(350, 100, 0, new Color(1, 0, 0));
		v2 = new Vector(300, 200, 0, new Color(0, 1, 0));
		t = new Triangle(v0, v1, v2);

		t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.FILL, viewpoint);

		v0 = new Vector(200, 300, 0, new Color(0, 0, 1));
		v1 = new Vector(300, 300, 0, new Color(1, 0, 0));
		v2 = new Vector(250, 400, 0, new Color(0, 1, 0));
		t = new Triangle(v0, v1, v2);
		t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.NONE, viewpoint);
		
		// -- this triangle should not be visible from the specified viewpoint
		v2 = new Vector(210, 310, 0, new Color(0, 0, 1));
		v1 = new Vector(310, 310, 0, new Color(1, 0, 0));
		v0 = new Vector(260, 410, 0, new Color(0, 1, 0));
		t = new Triangle(v0, v1, v2);
		t.render(framebuffer, true, ShaderAbstract.FILLSTYLE.NONE, viewpoint);

		try {
			ReadWriteImage.writeImage(framebuffer, "trianglefill.PNG");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
