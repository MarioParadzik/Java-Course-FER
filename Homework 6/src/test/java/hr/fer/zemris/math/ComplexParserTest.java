package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComplexParserTest {

	@Test
	public void testBasic() {
		String input = "0 - i1";
		Complex tst = ComplexParser.parse(input);
		assertEquals(tst, new Complex(0.0, -1.0));
	}
	
	@Test
	public void testOnlyRealNumber() {
		String input = "54";
		Complex tst = ComplexParser.parse(input);
		assertEquals(tst, new Complex(54.0, 0.0));
	}
	
	@Test
	public void testOnlyImaginaryNumber() {
		String input = "i15";
		Complex tst = ComplexParser.parse(input);
		assertEquals(tst, new Complex(0.0, 15.0));
	}
	
	@Test
	public void testOnlyImaginaryI() {
		String input = "i";
		Complex tst = ComplexParser.parse(input);
		assertEquals(tst, new Complex(0.0, 1.0));
	}
	@Test
	public void testOnlyImaginaryINegative() {
		String input = "-i";
		Complex tst = ComplexParser.parse(input);
		assertEquals(tst, new Complex(0.0, -1.0));
	}
	
	@Test
	public void testWrongInput() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			ComplexParser.parse("ds");
		});
	}

	@Test
	public void testWrongInput2() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			ComplexParser.parse("0 - 5i");
		});
	}
}
