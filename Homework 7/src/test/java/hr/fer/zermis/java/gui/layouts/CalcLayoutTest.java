package hr.fer.zermis.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

public class CalcLayoutTest {

	@Test
    void testRowLowerThanDefault() {
        JPanel p = new JPanel(new CalcLayout());
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("o"), new RCPosition(0, 2)));
    }
	
	@Test
    void testRowHigherThanDefault() {
        JPanel p = new JPanel(new CalcLayout());
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("o"), new RCPosition(6, 6)));
    }
	
	@Test
    void testColumnLowerThanDefault() {
        JPanel p = new JPanel(new CalcLayout());
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("0"), new RCPosition(2, -4)));
    }
	
	@Test
    void testColumnHigherThanDefault() {
        JPanel p = new JPanel(new CalcLayout());
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("o"), new RCPosition(1, 10)));
    }
	
	@Test
    void testfirstRowLimits() {
        JPanel p = new JPanel(new CalcLayout());
        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("o"), new RCPosition(1, 3)));
    }
	
	@Test
	void testDimensionSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(new Dimension(152, 158), dim);
	}
	
	@Test
	void testDimensionSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108, 15));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16, 30));
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();
		assertEquals(new Dimension(152, 158), dim);
	}
}
