package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class represents a layout for calculator.
 */
public class CalcLayout implements LayoutManager2{
	private static final int R = 5;
	private static final int C = 7;
	private static final int MAX = 31;
	private static final RCPosition FIRST_COMPONENT = new RCPosition(1, 1);
	private int gap;
	private Map<RCPosition, Component> compontents;
	
	/**
	 * Zero Constructor.
	 */
	public CalcLayout() {
		this(0);
	}
	
	/**
	 * Basic Constructor.
	 * @param gap Gap between the buttons.
	 */
	public CalcLayout(int gap) {
		if(gap < 0) throw new CalcLayoutException("Gap can't be negative.");
		this.gap = gap;
		compontents = new HashMap<>();
	}
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("We don't want this method to be called.!");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if(comp == null) throw new NullPointerException("Comp can't be null.");
		compontents.values().remove(comp);
		
	}
	/**
	 * This enumeration is helping us to calculate the Layoutsizes
	 */
	private enum LayoutType {
		MINIMUM,
		PREFERRED,
		MAXIMUM,
		CURRENT
	}
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calculate(parent, LayoutType.PREFERRED);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calculate(parent, LayoutType.MINIMUM);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calculate(target, LayoutType.MAXIMUM);
	}
	
	/**
	 * This method is calculating the layout sizes.
	 * @param parent Parent.
	 * @param type LayoutType.
	 * @return Dimension for the given layout type.
	 */
	private Dimension calculate(Container parent, LayoutType type) {
		int width = 0;
		int height = 0;
		for(Entry<RCPosition, Component> entry : compontents.entrySet()) {
			RCPosition position = entry.getKey();
			Component comp = entry.getValue();
			switch(type) {
				case MINIMUM:
					if(comp.getMinimumSize() != null) {
						int maxHigh = comp.getMaximumSize().height;
						int maxWidth = comp.getMaximumSize().width;
						if(position.equals(FIRST_COMPONENT)) maxWidth = (maxWidth - 4 * gap) / 5;
						height = Math.max(height, maxHigh);
						width = Math.max(width, maxWidth);
					}
					break;
				case PREFERRED:
					if(comp.getPreferredSize() != null) {
						int maxHigh = comp.getPreferredSize().height;
						int maxWidth = comp.getPreferredSize().width;
						if(position.equals(FIRST_COMPONENT)) maxWidth = (maxWidth - 4 * gap) / 5;
						height = Math.max(height, maxHigh);
						width = Math.max(width, maxWidth);
					}
					break;
				case MAXIMUM, CURRENT:
					if(comp.getPreferredSize() != null) {
						int maxHigh = comp.getMaximumSize().height;
						int maxWidth = comp.getMaximumSize().width;
						if(position.equals(FIRST_COMPONENT)) maxWidth = (maxWidth - 4 * gap) / 5;
						height = Math.max(height, maxHigh);
						width = Math.max(width, maxWidth);
					}
					break;
			}
		}
		if(LayoutType.CURRENT == type) return new Dimension(width, height);
		int x = parent.getInsets().left + parent.getInsets().right + C * width + (C-1) * gap;
		int y = parent.getInsets().top + parent.getInsets().bottom + R * height + (R-1) * gap;
		return new Dimension(x,y);
	}

	@Override
	public void layoutContainer(Container parent) {
		//najveci
		Dimension dim = calculate(parent, LayoutType.CURRENT);
		double x = (double) parent.getWidth() / preferredLayoutSize(parent).getWidth();
		double y = (double) parent.getHeight() / preferredLayoutSize(parent).getHeight();
		dim.setSize((x * dim.getWidth()), (y * dim.getHeight()));
		for(Entry<RCPosition, Component> entry : compontents.entrySet()) {
			if(entry.getKey().equals(FIRST_COMPONENT)) {
				entry.getValue().setBounds(0, 0, dim.width * 5 + 4 * gap, dim.height);;
			} else {
				entry.getValue().setBounds((entry.getKey().getColumn()-1) * (dim.width + gap),
						(entry.getKey().getRow()-1) * (dim.height + gap),
						dim.width,
						dim.height);
			}
		}
		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null) throw new NullPointerException("Component can't be null");
		if(constraints == null) throw new NullPointerException("Constraints can't be null");
		
		RCPosition position;
		if(constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else if (constraints instanceof String) {
			String[] split = ((String) constraints).split(",");
			try {
				position = new RCPosition(Integer.parseInt(split[0]),
						Integer.parseInt(split[1]));
			} catch (Exception e) {
				throw new IllegalArgumentException("Illegal string format.");
			}
		} else {
			throw new IllegalArgumentException("Invalid type.");
		}
		if(position.getRow() < 1 || position.getRow() > R) throw new CalcLayoutException("Row number should be between 1 and 5.");
		if(position.getColumn() < 1 || position.getColumn() > C) throw new CalcLayoutException("Column number should be between 1 and 7.");
		if(position.getRow() == 1 && position.getColumn() > 1 && position.getColumn() < 6) throw new CalcLayoutException("Invalid column number for the first row.");
		if (compontents.size() == MAX) throw new CalcLayoutException("Layout is full");
		if(compontents.containsKey(position)) throw new IllegalArgumentException("Constraint already exists");
		compontents.put(position, comp);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {}

}
