package hr.fer.zemris.math;

import java.util.Arrays;

public class ComplexRootedPolynomial {
	private Complex[] roots;
	private Complex constant;
	
	/**
	 * Basic constructor.
	 * @param constant Constant.
	 * @param roots Input roots.
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		if(constant == null) throw new NullPointerException("Constant can't be null.");
		if(roots.length <= 0) throw new IllegalArgumentException("At least one argument expected.");
		
		this.constant= constant;
		this.roots = roots;
	}
	
	/**
	 * This method computes polynomal value at given point Z.
	 * @param z Given complex point.
	 * @return Complex value at given point.
	 */
	public Complex apply(Complex z) {
		if(z == null) throw new NullPointerException("Given point can't be null.");
		return toComplexPolynom().apply(z);
	}

	/**
	 * This method converts this representation to ComplexPolynomial type.
	 * @return Representation to ComplexPolynomial type.
	 */
	public ComplexPolynomial toComplexPolynom() {
		 ComplexPolynomial rezultat = new ComplexPolynomial(constant);
		 for(Complex c: roots) {
			 rezultat = rezultat.multiply(new ComplexPolynomial(c.negate(), Complex.ONE));
		 }
		 
		 return rezultat;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(this.constant.toString());
		sb.append(")*");

		for(Complex c: roots) {
			sb.append("(z-");
			sb.append("(");
			sb.append(c.toString());
			sb.append(")");
			sb.append(")*");
		}
		sb.setLength(sb.length()-1);
		return sb.toString();
	}
	
	/**
	 * This method finds index of closest root for given complex number z that is within
	 * treshold; if there is no such root, returns -1
	 * @param z Given point.
	 * @param treshold Treshold.
	 * @return index if point is within treshold, otherwise false.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if(z == null) throw new NullPointerException("Given point can't be null.");
		if (roots.length == 0) return -1;
		int index = 0;
		
		double min = z.sub(roots[index]).getModule();
		for(int i = 0; i < roots.length; i++) {
			double val = z.sub(roots[i]).getModule();
			if(val < min) {
				min = val;
				index = i;
			}
		}
		
		return min <= treshold ? index : -1;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(roots);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		ComplexRootedPolynomial other = (ComplexRootedPolynomial) obj;
		if (constant == null) {
			if (other.constant != null)
				return false;
		} else if (!constant.equals(other.constant))
			return false;
		if (!Arrays.equals(roots, other.roots))
			return false;
		return true;
	}
	
	
}
