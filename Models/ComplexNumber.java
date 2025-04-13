package Models;
//This source code was initially written for CSC 220 (re-used)


public class ComplexNumber {

    private double real;
    private double imag;


    //Constructor Methods and Overloading
    public ComplexNumber()
    {
        this.real = 0;
        this.imag = 0;
    }
    public ComplexNumber(double r, double i)
    {
        this.real = r;
        this.imag = i;
    }

    public ComplexNumber(ComplexNumber rhs)//used to make copies of fields with same values without manually doing so
    {
        this.real = rhs.real;
        this.imag = rhs.imag;;
    }

    //setter and getter methods (mutators and accessors)
    public double getReal()
    {
        return real;
    }

    public void setReal(double r)
    {
        this.real = r;
    }

    public double getImag()
    {
        return imag;
    }

    public void setImag(double i)
    {
        this.imag = i;
    }

    //toString method is redefined here so that the automatic hashcode is not printed

    @Override//super is used to call the methods of parent classes in case of inheritance
    //In the toString method, since every class is a child of the object class--
    //--the super.toString return value would be a hash-code of the variable
    public String toString()
    {
        if (imag==0)
        {
            return real + " ";}
        if (imag>0)
        {
            return real + "+"+imag +"i";
        }
        else
        {
            return real + "-"+(-imag)+"i";
        }
    }
    //equals is redefined to--
    //--check if object field values are the same instead of checking if objects are same in memory
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof ComplexNumber)
        {
            ComplexNumber other = (ComplexNumber) obj;
            return (this.real == other.real &&this.imag == other.imag);
        }
        else
        {
            return false;
        }
    }
    //operation methods for complex num
    public ComplexNumber add(ComplexNumber rhs)
    {
        return new ComplexNumber(this.real+rhs.real,this.imag+rhs.imag);
    }
    public ComplexNumber sub(ComplexNumber rhs)
    {
        return new ComplexNumber(this.real- rhs.real,this.imag- rhs.imag);
    }
    public ComplexNumber mult(ComplexNumber rhs)
    {
        return new ComplexNumber((this.real* rhs.real-this.imag* rhs.imag),(this.real*rhs.imag)+(this.imag*rhs.real));
    }
    public ComplexNumber div(ComplexNumber rhs)throws IllegalArgumentException
    {
        double divDenom = rhs.real*rhs.real+rhs.imag*rhs.imag;
        double numerator_1 = this.real*rhs.real+this.imag*rhs.imag;
        double numerator_2 = this.imag*rhs.real-this.real*rhs.imag;
        if((divDenom)==0)
            throw new IllegalArgumentException("We can not divide by 0+0i");
        else
        {
            return new ComplexNumber( (numerator_1)/divDenom,(numerator_2)/divDenom);
        }
    }
    //divides this by rhs, throws exception if division by 0 + 0i

    public double mag ()
    {
        return Math.sqrt(real*real+imag*imag);
    }
    public ComplexNumber conj ()
    {
        return new ComplexNumber(real,-imag);
    }
    public ComplexNumber sqrt ()
    {
        if (imag != 0)
        {
            double realSection = (Math.sqrt((real+Math.sqrt(real*real+imag*imag))/2));
            double imageSection = (Math.sqrt((Math.sqrt(real*real+imag*imag)-real)/2));
            if (imag <0)
            {
                imageSection = -imageSection;
            }
            return new ComplexNumber (realSection,imageSection);
        }
        else if (imag==0)
        {
            if(real >=0)
            {
                return new ComplexNumber(Math.sqrt(real),0);
            }
            else
            {
                return new ComplexNumber(0,(Math.sqrt(-real)));
            }
        }
        return null;
    }
}
