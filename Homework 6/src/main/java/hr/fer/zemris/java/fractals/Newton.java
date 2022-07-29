package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexParser;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class is a non-multithread implementation of Newton-Raphson fractal viewer.
 */
public class Newton {
	private static final String END = "done";
	private static int routeCounter = 1;
	private static List<Complex> roots = new LinkedList<>();
	
	/**
	 * Main method.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("Root " + routeCounter + "> ");
			String line = sc.nextLine().trim();
			
			if(line.equals(END)) {
				if(routeCounter < 3) {
					System.out.println("You need to enter at least two roots.");
					continue;
				} else {
					System.out.println("Image of fractal will appear shortly. Thank you.");
					break;
				}
			} else if(line.isBlank()) {
				System.out.println("You can't leave it blank.");
				continue;
			} else {
				routeCounter++;
				try{
					roots.add(ComplexParser.parse(line));
				} catch (Exception e) {
					System.out.println(e.getMessage());
					routeCounter--;
					continue;
				}
			}		

			
		}
		sc.close();
		ComplexRootedPolynomial poly = new ComplexRootedPolynomial(Complex.ONE, roots.toArray(Complex[]::new));
		FractalViewer.show(new NewtonFractalProducer(poly));
	}
	
	/**
	 * This class represents an implementation for a single-thread production of Newton-Raphson fractal viewer.
	 */
	private static class NewtonFractalProducer implements IFractalProducer {

		private ComplexRootedPolynomial rootedPolynom;
		
		public NewtonFractalProducer(ComplexRootedPolynomial poly) {
			this.rootedPolynom = poly;
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			int offset = 0;
			int m = 16*16*16;
			short[] data = new short[width * height];
			
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					int iters = 0;
					Complex zn = new Complex(cre, cim);
					Complex znold;
					do {
						znold = zn;
						zn = zn.sub(rootedPolynom.toComplexPolynom().apply(zn).divide(rootedPolynom.toComplexPolynom().derive().apply(zn)));
						iters++;
						// 0.001 convergence threshold
					} while(iters < m && Math.abs(zn.sub(znold).module()) > 0.001);
					// 0.002 route-distance
					data[offset] = (short) (rootedPolynom.indexOfClosestRootFor(zn, 0.002) + 1);
					offset++;
				}
			}
			
			observer.acceptResult(data, (short)(rootedPolynom.toComplexPolynom().order() + 1), requestNo);
		}
		
	}
	
}
