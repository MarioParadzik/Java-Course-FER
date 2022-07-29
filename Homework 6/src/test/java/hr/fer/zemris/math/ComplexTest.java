package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ComplexTest {

	@Test
	public void testDefaultConstructor() {
		Complex tst = new Complex();
		assertEquals(tst, new Complex(0.0, 0.0));
	}
	
	@Test
	public void testBasicConstructor() {
		Complex tst = new Complex(1, 1);
		assertEquals(tst, new Complex(1.0, 1.0));
	}
	
	@Test
	public void testModule() {
		Complex tst = new Complex(3, 4);
		assertEquals(5, tst.module());
	}
	
	@Test
	public void testMultiply() {
		Complex tst = new Complex(3, 4).multiply(new Complex(5, 6));
		assertEquals(-9, tst.getReal());
		assertEquals(38, tst.getImaginary());
	}
	
	@Test
	public void testMultiplyNull() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			@SuppressWarnings("unused")
			Complex tst = new Complex(2, 2).multiply(null);
		});
	}
	
	@Test
	public void testDivide() {
		Complex tst = new Complex(3, 4).divide(new Complex(5, 6));
        assertEquals(39.0 / 61, tst.getReal());
        assertEquals(2.0 / 61, tst.getImaginary());
	}
	
	@Test
	public void testDivideNull() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			@SuppressWarnings("unused")
			Complex tst = new Complex(2, 2).divide(null);
		});
	}
	
	@Test
	public void testDivideWithZero() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			Complex tst = new Complex(2, 2).divide(new Complex());
		});
	}
	
	@Test
    public void testAdd() {
        Complex tst = new Complex(2, 2).add(new Complex(3, 4));
        assertEquals(5, tst.getReal());
        assertEquals(6, tst.getImaginary());
    }
	
	@Test
	public void testAddNull() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			@SuppressWarnings("unused")
			Complex tst = new Complex(2, 2).add(null);
		});
	}
	
	@Test
    public void testSub() {
        Complex tst = new Complex(2, 2).sub(new Complex(3, 4));
        assertEquals(-1, tst.getReal());
        assertEquals(-2, tst.getImaginary());
    }
	
	@Test
	public void testSubNull() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			@SuppressWarnings("unused")
			Complex tst = new Complex(2, 2).sub(null);
		});
	}
	
	@Test
    public void testNegate() {
        Complex tst = new Complex(2, 2).negate();
        assertEquals(-2, tst.getReal());
        assertEquals(-2, tst.getImaginary());
    }
	
	@Test
    public void testPower() {
		Complex tst = new Complex(3, 4).power(6);
        assertEquals(11753, tst.getReal(), 1E-7);
        assertEquals(-10296, tst.getImaginary(), 1E-7);
    }
	
	@Test
	public void testPowerNegativeExponend() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			Complex tst = new Complex(2, 2).power(-2);
		});
	}
	
    @Test
    public void testRoot() {
        List<Complex> roots = new Complex(3, 4).root(4);

        assertEquals(1.455346690225355, roots.get(0).getReal(), 1E-7);
        assertEquals(0.34356074972251244, roots.get(0).getImaginary(), 1E-7);
        assertEquals(-0.34356074972251244, roots.get(1).getReal(), 1E-7);
        assertEquals(1.455346690225355, roots.get(1).getImaginary(), 1E-7);
        assertEquals(-1.455346690225355, roots.get(2).getReal(), 1E-7);
        assertEquals(-0.34356074972251244, roots.get(2).getImaginary(), 1E-7);
        assertEquals(0.34356074972251244, roots.get(3).getReal(), 1E-7);
        assertEquals(-1.455346690225355, roots.get(3).getImaginary(), 1E-7);
    }
    
    @Test
	public void testRootLessThan1() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			@SuppressWarnings("unused")
			List<Complex> roots = new Complex(2, 2).root(-5);
		});
	}
}
