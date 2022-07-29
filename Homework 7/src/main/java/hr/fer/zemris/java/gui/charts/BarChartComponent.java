package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * This class represent a BarChartComponent.
 */
public class BarChartComponent extends JComponent {
	private static final long serialVersionUID = 7740414201595075368L;
	public static final Color CHART_COLOUR = new Color(244,119,72);
	public static final Color LINE_COLOUR = new Color(242,230,230);
	private static final int OFFSET = 40;
	private int leftText;
    private BarChart chart;
    private XYValue origin;
	
    public BarChartComponent(BarChart barChart) {
    	this.chart = barChart;
	}

	@Override
	protected void paintComponent(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	int yTextLen = g.getFontMetrics().stringWidth(String.valueOf(50));
    	leftText = 35 + yTextLen;
    	origin = new XYValue(leftText, getHeight() - 35);
    	
    	
    	//kordinatne osi
    	g2d.drawLine(origin.getX(), origin.getY(), getWidth() - OFFSET, origin.getY());
        g2d.drawLine(origin.getX(), origin.getY() + 5, origin.getX(), OFFSET);
		
        //horizontalne linije
        int i = 0;
        for(int y = origin.getY();y >= OFFSET; y -= yAxisLength() / ((chart.getMaxY() - chart.getMinY())/chart.getDiffY())) {
        	g.drawLine((int) (origin.getX() - 5), (int) y, (int) (getWidth() -OFFSET), (int) y);
        	String currentValue = String.valueOf(chart.getMinY() + chart.getDiffY() * (i++));
        	 g.drawString(currentValue,
                     (int) (origin.getX() - 5 - 5 - yTextLen),
                     (int) (y + g.getFontMetrics().getAscent() / 2)
                 );
        }
        //tekst
        g.drawString(chart.getxDesc(),
                (int) (origin.getX() + (getWidth() - leftText - OFFSET) / 2 - g2d.getFontMetrics().stringWidth(chart.getxDesc()) / 2),
                getHeight() - 10);
        
        AffineTransform defaultAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(-Math.PI / 2);
        g2d.setTransform(at);
        
        g.drawString(chart.getyDesc(),
                (int) ((-2.3 * origin.getY() + yAxisLength()) / 2 - g2d.getFontMetrics().stringWidth(chart.getyDesc()) / 2),
                (int) (leftText - 5) / 2
            );
        g2d.setTransform(defaultAt);
        
        List<XYValue> values = chart.getXyObjects();
        for (int j = 0; j < values.size(); j++) {
        	XYValue val = values.get(j);
            double barHeight = (double) val.getY() / chart.getMaxY() * yAxisLength();
            g2d.setColor(CHART_COLOUR);
            g2d.fillRect((int) origin.getX() + j * ((getWidth() - leftText - OFFSET) / values.size()),(int) ( origin.getY() - barHeight),
            		(int) ((getWidth() - leftText - OFFSET) / values.size()), (int) barHeight);
        }
        g2d.setColor(Color.BLACK);
        i = 0;
        for (double x = origin.getX(); x <= getWidth() - OFFSET; x += ((getWidth() - leftText - OFFSET) / values.size())) {
            g.drawLine((int) x, (int) (origin.getY() + 5), (int) x, (int) OFFSET);

            if (x == getWidth() - OFFSET || i >= values.size()) continue;

            int currentValue = values.get(i++).getX();
            g.drawString(String.valueOf(currentValue),
                (int) (x + ((getWidth() - leftText - OFFSET) / values.size()) / 2),
                origin.getY() + g.getFontMetrics().getAscent());
        }

	}

	/**
	 * This method is a getter for y Axies lenght.
	 * @return
	 */
	private double yAxisLength() {
        return (getHeight() - 2*OFFSET);
    }
	
}    
