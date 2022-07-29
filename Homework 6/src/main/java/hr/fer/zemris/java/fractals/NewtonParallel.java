package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexParser;
import hr.fer.zemris.math.ComplexRootedPolynomial;
/**
 * This class is a multithread implementation of Newton-Raphson fractal viewer.
 */
public class NewtonParallel {
	private static final String t = "-t ";
	private static final String w = "-w ";
	private static final String t1 = "--tracks=";
	private static final String w1 = "--workers=";
	private static final String END = "done";
	private static int routeCounter = 1;
	private static List<Complex> roots = new LinkedList<>();
	
	/**
	 * Main method.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {

		if(!(args.length == 1 || args.length == 2 || args.length == 0)) throw new IllegalArgumentException("To many argguments.");
		
		int workers = 0;
		int tracks = 0;
		if(args.length == 1) {
			if(args[0].startsWith(t) || args[0].startsWith(t1) || args[0].startsWith(w) || args[0].startsWith(w1)) {
				try {
					if(args[0].startsWith(t) || args[0].startsWith(t1)) {
						tracks = args[0].startsWith(t) ? Integer.parseInt(args[0].substring(t.length())) : Integer.parseInt(args[0].substring(t1.length()));
					} else {
						workers = args[0].startsWith(w) ? Integer.parseInt(args[0].substring(w.length())) : Integer.parseInt(args[0].substring(w1.length()));
					}
				} catch (Exception e) {
					System.out.println("Can't parse to number.");
					System.exit(-1);
				}
			} else throw new IllegalArgumentException("Wrong argguments.");
		} else if(args.length == 2){
			if((args[0].startsWith(w) || args[0].startsWith(w1)) && (args[1].startsWith(t) || args[1].startsWith(t1))) {
				try {
					workers = args[0].startsWith(w) ? Integer.parseInt(args[0].substring(w.length())) : Integer.parseInt(args[0].substring(w1.length()));
					tracks = args[1].startsWith(t) ? Integer.parseInt(args[1].substring(t.length())) : Integer.parseInt(args[1].substring(t1.length()));
				}catch (Exception e) {
					System.out.println("Can't parse to number.");
					System.exit(-1);
				}	
			} else throw new IllegalArgumentException("Wrong argguments.");
			
		} else {
			workers = Runtime.getRuntime().availableProcessors();
			tracks = Runtime.getRuntime().availableProcessors() * 4;
		}

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
					System.out.println("Generation Started.");
					System.out.println("Number of threads: " + workers);
					System.out.println("Number of jobs: " + tracks);
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
		FractalViewer.show(new NewtonFractalParallelProducer(poly, workers, tracks));
	}

	/**
	 * This class calculates colors for tracks.
	 */
	public static class Calculation implements Runnable {
		public final static Calculation NO_JOB = new Calculation();
		private ComplexRootedPolynomial rootedPolynom;
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		
		public Calculation() {}
		
		public Calculation(ComplexRootedPolynomial rootedPolynom, double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, int m, short[] data, AtomicBoolean cancel) {
			super();
			this.rootedPolynom = rootedPolynom;
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
		}
		@Override
		public void run() {
			int offset = this.yMin * this.width;	
			for(int y = yMin; y <= yMax; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 -y) / (height - 1) * (imMax - imMin) + imMin;
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
			
		}
		
	}
	
	/**
	 * This class represents an implementation for a multithread production of Newton-Raphson fractal viewer.
	 */
	public static class NewtonFractalParallelProducer implements IFractalProducer {
		private ComplexRootedPolynomial rootedPolynom;
		private int workers;
		private int trakcs;
		
		public NewtonFractalParallelProducer(ComplexRootedPolynomial poly, int workers, int tracks) {
			this.rootedPolynom = poly;
			this.workers = workers;
			this.trakcs = tracks;
		}
	
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			if(trakcs > height) trakcs = height;
			int m = 16*16*16;
			short[] data = new short[width * height];
			int brojYPoTraci = height / trakcs;
			final BlockingQueue<Calculation> queue = new LinkedBlockingQueue<>();
			
			Thread[] threads = new Thread[this.workers];
			for(int i = 0; i < threads.length; i++) {
				threads[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							Calculation p = null;
							try {
								p = queue.take();
								if(p==Calculation.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < threads.length; i++) threads[i].start();
			for(int i = 0; i < this.trakcs; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci - 1;
				if(i == this.trakcs - 1) yMax = height - 1;
				
				Calculation posao = new Calculation(rootedPolynom, reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				while(true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {}
				}
			}
			for(int i = 0; i < threads.length; i++) {
				while(true) {
					try {
						queue.put(Calculation.NO_JOB);
						break;
					} catch (InterruptedException e) {}
				}
			}
			for(int i = 0; i < threads.length; i++) {
				while(true) {
					try {
						threads[i].join();
						break;
					} catch (InterruptedException e) {}
				}
			}
			observer.acceptResult(data, (short)(rootedPolynom.toComplexPolynom().order() + 1), requestNo);
		}
		 
	 }
}
