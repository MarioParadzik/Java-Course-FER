package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * This class represents a complex polynom.
 */
public class ComplexPolynomial {

	private Complex[] factors;
	
	/**
	 * This method is a getter for factors.
	 * @return Factors.
	 */
	public Complex[] getFactors() {
		return factors;
	}
	/**
	 * Basic constructor.
	 * @param factors Factors.
	 */
	public ComplexPolynomial(Complex ...factors) {
		if(factors == null) throw new NullPointerException("Factors can't be null.");
		if(factors.length <= 0) throw new IllegalArgumentException("At least one argument expected.");
		
		this.factors = factors;
	}
	
	/**
	 * This method calculates the order of t his polynom.
	 * @return Order of this polynom.
	 */
	public short order() {
		return (short) (factors.length-1);
	}
	
	// computes a new polynomial this*p
	/**
	 * This method multiplies this polynom with given one.
	 * @param p Given polynom.
	 * @return The result of multiplying two complex polynoms.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] rez = new Complex[this.order() + p.order() +1];
		Complex[] pfaktori = p.getFactors();
		Arrays.fill(rez, Complex.ZERO);
		
	   for (int i = 0; i < factors.length; i++) {
            for (int j = 0; j < pfaktori.length; j++) {
            	rez[i + j] = rez[i + j].add(factors[i].multiply(pfaktori[j]));
            }
        }
	   return new ComplexPolynomial(rez);
	}
	// computes first derivative of this polynomial; for example, for
	// (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	
	/**
	 * This method calculates the derivation of this complex polynom.
	 * @return 1st derivation of complex polynom.
	 */
	public ComplexPolynomial derive() {
		if(factors.length <= 1) return new ComplexPolynomial(Complex.ZERO);
		Complex[] derivirano = new Complex[factors.length -1];
		for (int i = 0, len = derivirano.length; i < len; i++) derivirano[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		return new ComplexPolynomial(derivirano);
	}
	
	/**
	 * This method computes polynomal value at given point Z.
	 * @param z Given complex point.
	 * @return Complex value at given point.
	 */
	public Complex apply(Complex z) {
		if(z == null) throw new NullPointerException("Given point can't be null.");
		Complex rez = Complex.ZERO;
		for(int i = 0; i < factors.length; i++) rez = rez.add(z.power(i).multiply(factors[i]));
		return rez;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.length - 1; i >= 0; i--) {
			sb.append("(");
            sb.append(factors[i].toString());
            sb.append(")");
            if(i != 0) sb.append("*z^").append(i).append("+");

		}
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(factors);

	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		ComplexPolynomial other = (ComplexPolynomial) obj;
		if (!Arrays.equals(factors, other.factors))
			return false;
		return true;
	}
	
	

}
