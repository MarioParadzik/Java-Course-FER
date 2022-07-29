package hr.fer.zemris.math;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents a parser for complex numbers.
 */
public class ComplexParser {

	/**
	 * This method parses the given input to a complex number.
	 * @param s Input to parse.
	 * @return Parsed complex number.
	 */
	public static Complex parse(String s) {
		
		// dok oba dva postoje
		Pattern realAndImaginary = Pattern.compile("([-]?[0-9]+)([-|+]+[i][0-9]*)$");
		
		// dok postji samo realni dio
		Pattern real = Pattern.compile("^([-]?[0-9]+)$");
		
		// dok postoji samo imaginarni dio
		Pattern imaginary = Pattern.compile("^([-]?[i][0-9]*)$");
		
		Matcher rai = realAndImaginary.matcher(s.replaceAll("\\s",""));
		Matcher r = real.matcher(s.replaceAll("\\s",""));
		Matcher i = imaginary.matcher(s.replaceAll("\\s",""));
		
		if(rai.find()) {
			double re;
			double im;
			try {
				re = Double.parseDouble(rai.group(1));
				if(rai.group(2).equals("i")) return new Complex(re, 1.0);
		        im = Double.parseDouble(rai.group(2).replace("i", ""));
			} catch (Exception e) {
				throw new RuntimeException("Can't parse input to complex number.");
			}
			
	        return new Complex(re, im);
	        
		} else if(r.find()) {
			double re;
			try {
				re = Double.parseDouble(r.group(1));
			} catch (Exception e) {
				throw new RuntimeException("Can't parse input to complex number.");
			}
			
			return new Complex(re, 0.0);
			
		} else if(i.find()) {
			if(i.group(1).equals("i")) return new Complex(0.0, 1.0);
			if(i.group(1).equals("-i")) return new Complex(0.0, -1.0);	
			double im;
			try {
				im = Double.parseDouble(i.group(1).replace("i", ""));
			} catch (Exception e) {
				throw new RuntimeException("Can't parse input to complex number.");
			}
			
			return new Complex(0.0, im);
			
		} else {
			throw new RuntimeException("Can't parse input to complex number.");
		}
		
	}
	
	
}
