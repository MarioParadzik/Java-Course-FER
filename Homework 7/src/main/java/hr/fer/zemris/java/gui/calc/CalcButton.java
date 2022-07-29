package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This class represents a basic calculator button.
 */
public class CalcButton extends JButton{
	public static final Color BUTTON_COLOUR = new Color(221,221,255);
	private static final long serialVersionUID = 1L;
	Font font1 = new Font("SansSerif", Font.BOLD, 15);
	private String[] name;
	private ActionListener event;
	private ActionListener inverseEvent;
	private boolean flag = false;
	
	/**
	 * Basic Constructor.
	 * @param name Button name.
	 * @param event Event.
	 * @param inverseEvent Inverse event.
	 */
	public CalcButton(String name, ActionListener event, ActionListener inverseEvent) {
		this.name = name.split(" ");
		this.event = event;
		if(inverseEvent == null) this.inverseEvent = event;
		else this.inverseEvent = inverseEvent;
		initGUI();
	}

	/**
	 * This method is used to initialize the button.
	 */
	private void initGUI() {
		setBackground(BUTTON_COLOUR);
		setForeground(Color.BLACK);
		setFont(font1);
		setText(name[0]);
		addActionListener(event);
	}
	
	/**
	 * This method is used to inverse the actions.
	 */
	public void inverse() {
		if(!flag) {
			setText(name[1]);
			removeActionListener(event);
			addActionListener(inverseEvent);
			flag = true;
		} else {
			setText(name[0]);
			removeActionListener(inverseEvent);
			addActionListener(event);
			flag = false;
		}
	}
	
}
