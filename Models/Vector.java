package Models;

public class Vector extends VectorAbstract
{
    public Vector(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(double x, double y, double z,Color color)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    //From online resources I understood that you--
    //--calculate the dot product of two angles and then--
    //--divide it by the product of their magnitudes
    //lastly get the angle by findings the arcos of the number
    //convert to radians by multiplying by 180/pi
    @Override
    public double angleBetween(VectorAbstract v2)
    {
        Vector a = (Vector) v2;
        double  dot_product = this.dot(a);
        double mag_prod = this.length()*a.length();
        double arcos = Math.acos(dot_product/mag_prod);

        return arcos;
    }

    // a*b = (a1*b1)+(a2*b2)+(a3*b3) for Vectors a and b
    @Override
    public double dot(VectorAbstract v2)
    {
        //to access the x, y, and z of v2 we have to cast it as a vector rather than a Vector Abstract
        Vector a = (Vector) v2;
        double dotProduct = this.x * a.getX() + this.y * a.getY() + this.z * a.getZ();
        return dotProduct;
    }

    //The cross product of two vectors synthesizes an additional perpendicular vector
    //A*B = ((A2*B3-A3*B2),(A3*B1-A1*B3),(A1*B2-A2*B1) where x=1, y=2, and z=3
    //In the example above, A is this(current vector) and B is v2
    @Override
    public VectorAbstract cross(VectorAbstract v2)
    {
        Vector v = (Vector) v2;
        double x_new = (this.y*v.getZ()-this.z*v.getY());
        double y_new = (this.z*v.getX()-this.x*v.getZ());
        double z_new = (this.x*v.getY()-this.y*v.getX());
        Vector new_Vector = new Vector(x_new, y_new, z_new);
        return new_Vector;
    }


    //Based off the comments on the previous file I understood--
    //--that we are to find the cross product of this(current vector and v2)--
    //-- then normalize it afterward here
    //Actually it seems since we are not using v2 as a parameter we probably just need to compute the unit of the vector
    //So the formula is pretty much unit = vector/magnitude of vector
    @Override
    public VectorAbstract unit()
    {
        double magnitude = this.length();
        if (magnitude == 0) {
            return new Vector(0,0,0);
        }
        double x_new = this.x / magnitude;
        double y_new = this.y / magnitude;
        double z_new = this.z / magnitude;
        Vector new_Vector = new Vector(x_new, y_new, z_new);
        return new_Vector;
    }

    //Length is calculated through the sqrt(x^2+y^2+z^2)
    //It is also referred to as the magnitude
    @Override
    public double length()
    {
        double length = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return length;
    }

    //A+B = (x1+x2,y1+y2,z1+z2)
    @Override
    public VectorAbstract add(VectorAbstract v2)
    {
       Vector a = (Vector) v2;
       double x_new = this.x+a.getX();
       double y_new = this.y+a.getY();
       double z_new = this.z+a.getZ();
       Vector new_Vector = new Vector(x_new, y_new, z_new,this.color);
        return new_Vector;
    }

    @Override
    public VectorAbstract sub(VectorAbstract v2)
    {
        double x_new = this.getX()-v2.getX();
        double y_new = this.getY()-v2.getY();
        double z_new = this.getZ()-v2.getZ();
        Vector new_Vector = new Vector(x_new, y_new, z_new,this.color);
        return new_Vector;
    }

    @Override
    public VectorAbstract mult(double scale)
    {
        double x_new = this.x*scale;
        double y_new = this.y*scale;
        double z_new = this.z*scale;
        Vector new_Vector = new Vector(x_new, y_new, z_new,this.color);
        return new_Vector;
    }
}
