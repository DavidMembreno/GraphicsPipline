//package Tests;
//
//import Models.*;
//import Tools.ReadWriteImage;
//
//public class RotationTest {
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
//			double theta = 45;
//			TriangleAbstract tx;
//
//			VectorAbstract rotationaxis;
//
//			rotationaxis = new Vector(1, 0, 0, null);
//			t.render(framebuffer, true);
//			tx = t.rotateAxis(rotationaxis, t.getCenter(), Math.toRadians(theta), t);
//			tx.render(framebuffer, true);
//			ReadWriteImage.writeImage(framebuffer, "rotateX.png");
//
//			framebuffer = new int[3][256][256];
//			rotationaxis = new Vector(0, 1, 0, null);
//			t.render(framebuffer, true);
//			tx = t.rotateAxis(rotationaxis, t.getCenter(), Math.toRadians(theta), t);
//			tx.render(framebuffer, true);
//			ReadWriteImage.writeImage(framebuffer, "rotateY.png");
//
//			framebuffer = new int[3][256][256];
//			rotationaxis = new Vector(0, 0, 1, null);
//			t.render(framebuffer, true);
//			tx = t.rotateAxis(rotationaxis, t.getCenter(), Math.toRadians(theta), t);
//			tx.render(framebuffer, true);
//			ReadWriteImage.writeImage(framebuffer, "rotateZ.png");
//
//			framebuffer = new int[3][256][256];
//			rotationaxis = new Vector(1, 1, 1, null);
//			t.render(framebuffer, true);
//			tx = t.rotateAxis(rotationaxis, t.getCenter(), Math.toRadians(theta), t);
//			tx.render(framebuffer, true);
//			ReadWriteImage.writeImage(framebuffer, "rotateArbit.png");
//
//
//		}
//		catch (Exception e) {
//			System.out.println(e);
//		}
//
//	}
//
//}
