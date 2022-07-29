package hr.fer.zemris.math;

/**
 * This class is used for testing Complex classes.
 */
public class Main {

	/**
	 * Main method.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
				ComplexPolynomial cp = crp.toComplexPolynom();
				System.out.println(crp);
				System.out.println(cp);
				System.out.println(cp.derive());


	}

}
