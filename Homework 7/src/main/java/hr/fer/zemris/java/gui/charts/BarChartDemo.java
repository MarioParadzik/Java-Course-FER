package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
/**
 * This class is used for testing the Bar chart.
 */
public class BarChartDemo extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int ROWS = 6;
	private static String xDesc;
	private static String yDesc;
	private static List<XYValue> points;
	private static int yMin;
	private static int yMax;
	private static int yDiff;
	
	/**
	 * Basic Constructor.
	 * @param barChart BarChart.
	 * @param path Path of Document.
	 */
	public BarChartDemo(BarChart barChart, String path) {
		if(barChart == null || path == null) throw new NullPointerException("Path or BarChart is null.");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI(barChart);
		setSize(new Dimension(700, 500));
	}
	
	/**
	 * Initialization of GUI.
	 * @param barChart BarChart.
	 */
	private void initGUI(BarChart barChart) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
        cp.add(new BarChartComponent(barChart), BorderLayout.CENTER);	
	}
	
	/**
	 * Main method.
	 * @param args Command Line arguments.
	 */
	public static void main(String[] args) {
		if(args.length != 1) throw new IllegalArgumentException("Wrong number of arguments.");
		File f = new File(args[0]);
		String output = null;
		try (Scanner scanner = new Scanner(f)) {
		    scanner.useDelimiter("\\Z");
		    output = scanner.next();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String[] input = output.split("\\n");
		if(input.length < ROWS) throw new IllegalArgumentException("Not enough rows.");
		xDesc = input[0].strip();
		yDesc = input[1].strip();
		try {
			points = parse(input[2].strip());
			yMin = Integer.parseInt(input[3].strip());
			yMax = Integer.parseInt(input[4].strip());
			yDiff = Integer.parseInt(input[5].strip());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
		
		SwingUtilities.invokeLater(() -> new BarChartDemo(new BarChart(points, xDesc, yDesc, yMin, yMax, yDiff),
                Paths.get(args[0]).toAbsolutePath().normalize().toString()).setVisible(true));
	}

	/**
	 * Parser for points.
	 * @param strip String representation of points.
	 * @return List of parsed points.
	 */
	private static List<XYValue> parse(String strip) {
		if(strip == null) throw new NullPointerException();
		List<XYValue> points = new ArrayList<>();
		String[] stringPoints = strip.split("\\s");
		for(String s: stringPoints) {
			String[] values = s.split(",");
			if(values.length != 2) throw new IllegalArgumentException("Wrong x,y value input");
			points.add(new XYValue(Integer.parseInt(values[0]), Integer.parseInt(values[1])));
		}
		return points;
	}
}
