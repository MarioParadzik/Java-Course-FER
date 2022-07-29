package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * This class represents a calculator.
 */
public class Calculator extends JFrame{
	Font font1 = new Font("SansSerif", Font.BOLD, 40);
	private static final long serialVersionUID = 1L;
	private CalcModel calc = new CalcModelImpl();
	private List<CalcButton> invOperations = new ArrayList<>();
	private Stack<Double> stog = new Stack<>();
	
	/**
	 * Basic constructor.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		setLocation(50, 50);
        setSize(630, 420);
		initGUI();
	}

	/**
	 * This method is used to initialize the calculator.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		JPanel p = new JPanel(new CalcLayout(4));
		JLabel ekran = new JLabel(calc.toString());
        ekran.setBackground(Color.YELLOW);
        ekran.setHorizontalAlignment(SwingConstants.RIGHT);
        ekran.setFont(font1);
        ekran.setOpaque(true);
        calc.addCalcValueListener(calc -> ekran.setText(calc.toString()));
        
        // 5,6
        CalcButton plus = new CalcButton("+",
        		e -> {
        			calculate();
        			calc.setPendingBinaryOperation((a, b) -> a + b);
        		}, null);
        p.add(plus, new RCPosition(5, 6));
        //4,6
        CalcButton minus = new CalcButton("-",
        		e -> {
        			calculate();
        			calc.setPendingBinaryOperation((a, b) -> a - b);
        		}, null);
        p.add(minus, new RCPosition(4, 6));
        //3,6
        CalcButton puta = new CalcButton("*",
        		e -> {
        			calculate();
        			calc.setPendingBinaryOperation((a, b) -> a * b);
        		}, null);
        p.add(puta, new RCPosition(3, 6));
        //2,6
        CalcButton podijeljeno = new CalcButton("/",
        		e -> {
        			calculate();
        			calc.setPendingBinaryOperation((a, b) -> a / b);
        		}, null);
        p.add(podijeljeno, new RCPosition(2, 6));
        //1,6
        CalcButton jednako = new CalcButton("=",
        		e -> {
        			jednako();
        		}, null);
        p.add(jednako, new RCPosition(1, 6));
        //2,1
        CalcButton jedanKrozX = new CalcButton("1/x",
        		e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(1.0 / (calc.getValue()));
        		}, null);
        p.add(jedanKrozX, new RCPosition(2, 1));
        //2,2
        CalcButton sinIasin = new CalcButton("sin arcsin",
        		e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.sin(calc.getValue()));
        		}
        		, e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.asin(calc.getValue()));
        		});
        p.add(sinIasin, new RCPosition(2, 2));
        //3,2
        CalcButton cosIacos = new CalcButton("cos arccos",
        		e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.cos(calc.getValue()));
        		}
        		, e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.acos(calc.getValue()));
        		});
        p.add(cosIacos, new RCPosition(3, 2));
        //3,1
        CalcButton logI10naX = new CalcButton("log 10^x",
        		e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.log10(calc.getValue()));
        		}
        		,e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.pow(10, calc.getValue()));
        		});
        p.add(logI10naX, new RCPosition(3, 1));
        //4,1
        CalcButton lnIe = new CalcButton("ln e^x",
        		e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.log10(calc.getValue()));
        		}
        		,e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.pow(Math.E, calc.getValue()));
        		});
        p.add(lnIe, new RCPosition(4, 1));
        //4,2
        CalcButton tanIarcTan = new CalcButton("tan arctan",
        		e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.tan(calc.getValue()));
        		}
        		,e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.atan(calc.getValue()));
        		});
        p.add(tanIarcTan, new RCPosition(4, 2));
        //5,1
        CalcButton xnIxn = new CalcButton("x^n x^(1/n)", e -> {
        	calculate();
        	calc.setPendingBinaryOperation(Math::pow);
        }, e -> {
        	calculate();
        	calc.setPendingBinaryOperation((a, b) -> Math.pow(a, 1 / b));
        });
        p.add(xnIxn, new RCPosition(5, 1));
        //5,2
        CalcButton ctanIarcCtan = new CalcButton("ctg arcctg",
        		e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(1.0 / Math.tan(calc.getValue()));
        		},
        		e -> {
        			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
        			calc.setValue(Math.PI / 2 - Math.atan(calc.getValue()));
        		});
        p.add(ctanIarcCtan, new RCPosition(5, 2));
        
        invOperations.add(sinIasin);
        invOperations.add(cosIacos);
        invOperations.add(logI10naX);
        invOperations.add(lnIe);
        invOperations.add(tanIarcTan);
        invOperations.add(ctanIarcCtan);
        invOperations.add(xnIxn);
        
        p.add(ekran, new RCPosition(1, 1));
        p.add(new NumberButton("0", e -> calc.insertDigit(0)), new RCPosition(5, 3));
        p.add(new NumberButton("1", e -> calc.insertDigit(1)), new RCPosition(4, 3));
        p.add(new NumberButton("2", e -> calc.insertDigit(2)), new RCPosition(4, 4));
        p.add(new NumberButton("3", e -> calc.insertDigit(3)), new RCPosition(4, 5));
        p.add(new NumberButton("4", e -> calc.insertDigit(4)), new RCPosition(3, 3));
        p.add(new NumberButton("5", e -> calc.insertDigit(5)), new RCPosition(3, 4));
        p.add(new NumberButton("6", e -> calc.insertDigit(6)), new RCPosition(3, 5));
        p.add(new NumberButton("7", e -> calc.insertDigit(7)), new RCPosition(2, 3));
        p.add(new NumberButton("8", e -> calc.insertDigit(8)), new RCPosition(2, 4));
        p.add(new NumberButton("9", e -> calc.insertDigit(9)), new RCPosition(2, 5));
        p.add(new BasicOperatiosButton("clr", e -> calc.clear()), new RCPosition(1, 7));
        p.add(new BasicOperatiosButton("reset", e -> calc.clearAll()), new RCPosition(2, 7));
        p.add(new BasicOperatiosButton(".", e -> calc.insertDecimalPoint()), new RCPosition(5, 5));
        p.add(new BasicOperatiosButton("+/-", e -> calc.swapSign()), new RCPosition(5, 4));
        p.add(new BasicOperatiosButton("push", e -> stog.push(calc.getValue())), new RCPosition(3, 7));
        p.add(new BasicOperatiosButton("pop", e -> {
            if (stog.isEmpty()) throw new CalculatorInputException("Cannot pop from an empty stack!");
            else calc.setValue(stog.pop());
        }), new RCPosition(4, 7));
        
        JCheckBox reverse = new JCheckBox("Inv");
        reverse.addItemListener(e -> {
        	for(CalcButton btn : invOperations) {
        		btn.inverse();
        	}
        });
        p.add(reverse, new RCPosition(5, 7));
        cp.add(p);
        cp.setPreferredSize(p.getPreferredSize());
        
	}

	/**
	 * This method is used to evaluate the input.
	 */
	private void jednako() {
        if (calc.getPendingBinaryOperation() != null) calc.setActiveOperand(calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
        calc.setValue(calc.getActiveOperand());
        calc.freezeValue(Double.toString(calc.getActiveOperand()));
	}

	/**
	 * This method is used to calculate the input.
	 */
	private void calculate() {
		if(calc.isActiveOperandSet()) {
			if(calc.hasFrozenValue()) throw new CalculatorInputException("Result is frozen.");
			double result = calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue());
			calc.freezeValue(Double.toString(result));
			calc.setActiveOperand(result);
		} else {
			calc.setActiveOperand(calc.getValue());
		}
		calc.clear();
	}
	
	/**
	 * Main method.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
	       SwingUtilities.invokeLater(() -> {
	           Calculator prozor = new Calculator();
	           prozor.setVisible(true);
	       });
	}
	    
}
