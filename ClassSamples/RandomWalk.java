package ClassSamples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import Models.*;//we need vecor and color
import Tools.ReadWriteImage;


//Generatig a turtle graphics for random movement (Conway's_

public class RandomWalk
{
    public void walk(ArrayList<VectorAbstract> path)
    {
        int n =500000;
        Random rn = new Random();
        int currentx = 0;
        int currenty = 0;
        int currentz = 0;

        for (int i=0;i<n;i++)
        {
            int xdir = rn.nextInt(3)-1;
            int ydir = rn.nextInt(3)-1;
            int zdir = rn.nextInt(3)-1;
            //we generate 2 random numbers with value of 0, 1, or 2
            //By subtracting 1 we get either -1, 0 , or 1 for x or y direction
                //1,1 upper right   1,0 move right  0,1 move up, -1,0 move left
                    //1,1 up one right one...
            currentx += xdir;
            currenty += ydir;
            currentz += zdir;

            VectorAbstract va = new Vector (currentx,currenty,currentz, null);
            path.add(va);

        }
    }
    public void render(ArrayList<VectorAbstract> path,int [][][]framebuffer){
        for(VectorAbstract va:path)
        {
            try
            {
                int CenterX = framebuffer[0][0].length/2;
                int CenterY = framebuffer[0][1].length/2;
                framebuffer[0][(int) va.getY()+CenterY][(int) va.getX()+CenterX] = 255;
                framebuffer[1][(int) va.getY()+CenterY][(int) va.getX()+CenterX] = 255;
                framebuffer[2][(int) va.getY()+CenterY][(int) va.getX()+CenterX] = 255;
            } catch(ArrayIndexOutOfBoundsException e)
            {

            }
        }
    }
    public static void main(String[] args)
    {
        ScanConvertAbstract scl = new ScanConvertLine();
        RandomWalk rw = new RandomWalk();
        int [][][] framebuffer = new int[3][512][512];
        ArrayList<VectorAbstract> path = new ArrayList<VectorAbstract>();
        rw.walk(path);
        rw.render(path,framebuffer);
        VectorAbstract start = path.get(0);
        for (int i=0;i<path.size();i++)
        {
            VectorAbstract end = path.get(i);
            int CenterX = framebuffer[0][0].length/2;
            int CenterY = framebuffer[0][1].length/2;
            scl.bresenham((int)start.getX() * 5 +CenterX, (int) start.getY() * 5 +CenterY,
                    (int)end.getX()*5+CenterX, (int)end.getY()*5+CenterY, new Color (0,1,1),
                    new Color(0,1,0),framebuffer);
            start = end;
            //sierpinski triangle
                //triangle edfe midpoints, draw triangles between them, loop this till side of triangle is "too small"
            //Today we implemented Brownian Motion
            Random r = new Random();
//            ArrayList<> list = new ArrayList<>();
//            for (int j=0;j<9;j++){
//            }
        }
        try{
            ReadWriteImage.writeImage(framebuffer, "randomwalk.PNG");
        } catch (IOException e)
        {
            System.out.println(e);
        }
    }
}
