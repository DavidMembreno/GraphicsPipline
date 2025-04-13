//package Tests;
//
//import Models.*;
//import Tools.ReadWriteImage;
//
//public class AffineTransformationTest {
//
//	public static void main(String[] args) {
//		int framebuffer[][][] = new int[3][256][256];
//		VectorAbstract v0, v1, v2;
//		TriangleAbstract t;
//		try {
//			int scalefactor = 100;
//			VectorAbstract offset = new Vector(78, 78, 78, new Color(1.0, 0.0, 0.0));
//
//			v0 = new Vector(0.0, 0.0, 1.0, new Color(1.0, 0.0, 0.0));
//			v0 = v0.mult(scalefactor);
//			v0 = v0.add(offset);
//			v1 = new Vector(1.0, 0.0, 1.0, new Color(1.0, 0.0, 0.0));
//			v1 = v1.mult(scalefactor);
//			v1 = v1.add(offset);
//			v2 = new Vector(0.0, 1.0, 1.0, new Color(1.0, 0.0, 0.0));
//			v2 = v2.mult(scalefactor);
//			v2 = v2.add(offset);
//			t = new Triangle(v0, v1, v2);
//			t.render(framebuffer,  true);
//			double theta = 45;
//			TriangleAbstract tx;
//
//			framebuffer = new int[3][256][256];
//			tx = t.translate(new Vector(10, 15, 20, null), t);
//			tx.render(framebuffer, true);
//			ReadWriteImage.writeImage(framebuffer, "translate.png");
//
//			framebuffer = new int[3][256][256];
//			tx = t.rotateX(Math.toRadians(theta), t.getCenter(), t);
//			tx.render(framebuffer, true);
//			ReadWriteImage.writeImage(framebuffer, "rotateX.png");
//
//			framebuffer = new int[3][256][256];
//			tx = t.rotateY(Math.toRadians(theta), t.getCenter(), t);
//			tx.render(framebuffer, true);
//			ReadWriteImage.writeImage(framebuffer, "rotateY.png");
//
//			framebuffer = new int[3][256][256];
//			tx = t.rotateZ(Math.toRadians(theta), t.getCenter(), t);
//			tx.render(framebuffer, true);
//			ReadWriteImage.writeImage(framebuffer, "rotateZ.png");
//
//			framebuffer = new int[3][256][256];
//			tx = t.scale(new Vector(1, 2, 3, null),  t.getCenter(), t);
//			tx.render(framebuffer, true);
//			ReadWriteImage.writeImage(framebuffer, "scale.png");
//		}
//		catch (Exception e) {
//			System.out.println(e);
//		}
//
//	}
//
//}
