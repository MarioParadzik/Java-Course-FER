package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a complex number.
 */
public class Complex {
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	private double real;
	private double imaginary;
	private double module;
	
	/**
	 * Default constructor.
	 */
	public Complex() {
		this.real = 0;
		this.imaginary = 0;
		this.module = 0;
	}
	
	/**
	 * Basic constructor.
	 * @param re Real part of complex number.
	 * @param im Imaginary part of complex number.
	 */
	public Complex(double re, double im) {
		this.real = re;
		this.imaginary = im;
		this.module = module();
	}
	
	/**
	 * This method is a getter for real value of complex number.
	 * @return Real part of complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * This method is a getter for imaginary value of complex number.
	 * @return Imaginary part of complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * This method is a getter for the module of complex number.
	 * @return Module of complex number.
	 */
	public double getModule() {
		return module;
	}
	
	/**
	 * This method calculates the value of the module of the complex number.
	 * @return Module of the complex number.
	 */
	public double module() {
		if(this.real != 0 || this.imaginary != 0) return Math.sqrt(real * real + imaginary * imaginary);
		else return 0;
	}
	
	/**
	 * This method multiplies this complex number with the given.
	 * @param c Complex number we multiply.
	 * @return Result of multiplying two complex numbers.
	 */
	public Complex multiply(Complex c) {
		if(c == null) throw new NullPointerException();
		return new Complex(this.real * c.getReal() - this.imaginary * c.getImaginary() 
				,this.real * c.getImaginary() + this.imaginary * c.getReal());
	}
	
	/**
	 * This method divides this complex number with the given.
	 * @param c Complex number we divide.
	 * @return Result of dividing two complex numbers.
	 */
	public Complex divide(Complex c) {
		if(c == null) throw new NullPointerException();
		double den = c.module() * c.module();
		if(den == 0) throw new IllegalArgumentException("Can't divide by 0.");
		
        return new Complex((this.real * c.getReal() + this.imaginary * c.getImaginary()) / den ,
        		(this.imaginary * c.getReal() - this.real * c.getImaginary()) / den);
	}
	
	/**
	 * This method sums this complex number with the given.
	 * @param c Complex number we multiply.
	 * @return Sum of two complex numbers.
	 */
	public Complex add(Complex c) {
		if(c == null) throw new NullPointerException();
		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * This method subtracts this complex number with the given.
	 * @param c Complex number we subtract.
	 * @return subtraction of two complex numbers.
	 */
	public Complex sub(Complex c) {
		if(c == null) throw new NullPointerException();
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * This method negates the real and imaginary part of complex number.
	 * @return Complex number with negated values.
	 */
	public Complex negate() {
		return new Complex(-this.real, -this.imaginary);
	}
	
	/**
	 * This method powers this complex number with the given value.
	 * @param n Number of power.
	 * @return Complex number powered by n.
	 */
	public Complex power(int n) {
		if(n < 0) throw new IllegalArgumentException("Exponent can't be negative");
		double powerModule = Math.pow(module(), n);
		double angle = Math.atan2(this.imaginary, this.real);
		
		return new Complex(powerModule * Math.cos(n * angle), powerModule * Math.sin(n * angle));
	}
	
	/**
	 * This method is used to calculate the n-th root of this complex number.
	 * @param n Number of rooting.
	 * @return n-th root of this complex number.
	 */
	public List<Complex> root(int n) {
		if(n < 1) throw new IllegalArgumentException("Exponent can't be smaller than 1.");
		List<Complex> roots = new LinkedList<>();
		double angle = Math.atan2(this.imaginary, this.real);
		double rootMag = Math.pow(module(), 1.0 / n);

        for (int i = 0; i < n; i++) {
        	double angleCurr = (angle + 2 * Math.PI * i) / n;
            roots.add(new Complex(rootMag * Math.cos(angleCurr),
            		rootMag * Math.sin(angleCurr)));
        }
        
        return roots;

	}
	@Override
	public String toString() {
		if(this.imaginary >= 0) {
			return this.real + "+i" + this.imaginary;
		} else if( this.imaginary < 0) {
			return this.real + "-i" + (-this.imaginary);
		} 
		throw new IllegalArgumentException("Can't exist");
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.real, this.imaginary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}
	
	

}
