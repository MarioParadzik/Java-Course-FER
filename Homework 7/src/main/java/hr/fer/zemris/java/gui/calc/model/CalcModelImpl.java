package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * This class is an implementation of Calc Model.
 */
public class CalcModelImpl implements CalcModel {

	private boolean editable;
	private boolean positive;
	private String input;
	private double inputValue;
	private String frozen;
	private double activeOperand;
	private boolean activeOperandflag;
	private DoubleBinaryOperator pendingOperation;
	private List<CalcValueListener> listeners =  new ArrayList<>();
	
	/**
	 * Basic Construcotr.
	 */
	public CalcModelImpl() {
        this.editable = true;
        this.positive = true;
        this.input = "";
        this.inputValue = 0;
        this.frozen = null;
        activeOperandflag = false;
    }	

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if(l == null) throw new NullPointerException("Listener can't be null.");
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if(l == null) throw new NullPointerException("Listener can't be null.");
		listeners.remove(l);
	}
	
	private void notifyListeners() {listeners.forEach(l -> l.valueChanged(this));}

	@Override
	public double getValue() {
		return positive ? inputValue : -inputValue;
	}

	@Override
	public void setValue(double value) {
		editable = false;
		positive = value >= 0;
		frozen = null;
		if(Double.isFinite(value)) {
			inputValue = Math.abs(value);
			input = String.valueOf(value);
		} else {
			if(Double.isInfinite(value)) {
				inputValue = positive ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
				input = String.valueOf(value);
			} else if(Double.isNaN(value)) {
				inputValue = Double.NaN;
				input = String.valueOf(value);
			}
		}
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return this.editable;
	}

	@Override
	public void clear() {
		editable = true;
		positive = true;
		input = "";
		inputValue = 0.;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		clearActiveOperand();
		frozen = null;
		pendingOperation = null;
		clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable()) throw new CalculatorInputException("Calculator currently not editable.");
		positive = !positive;
		frozen = null;	
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!editable) throw new CalculatorInputException("You can't enter a digit, calculator not editable.");
		if(input.contains(".") || input.isBlank()) throw new CalculatorInputException("Number has already a decimal point.");
		String added;
		if(input.isBlank()) added = "0.";
		else added = input + ".";
		
		try {
			double number = Double.parseDouble(added);
			if(Double.isFinite(number)) {
				input = added;
				inputValue = number;
				frozen = null;
				notifyListeners();
			} else {
				throw new CalculatorInputException("Number to large.");
			}
		}catch (NumberFormatException e) {
			throw new CalculatorInputException("Can't parse to double.");
		}
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!editable) throw new CalculatorInputException("You can't enter a digit, calculator not editable.");
		if(digit > 9 && digit < 0) throw new CalculatorInputException("Number you enetered is not valid.");
		if(input == null) {
			input = String.valueOf(digit);
			inputValue = digit;
		} else {
			String added = input + String.valueOf(digit);
			try {
				double number = Double.parseDouble(added);
				if(Double.isFinite(number)) {
					input = added;
					inputValue = number;
					notifyListeners();
				} else {
					throw new CalculatorInputException("To Large Number.");
				}
			} catch (NumberFormatException e) {
				throw new CalculatorInputException("Can't parse to double.");
			}
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperandflag;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(!isActiveOperandSet()) throw new IllegalStateException("Active operant not set.");
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		activeOperandflag = true;
		editable = false;
		notifyListeners();
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0.0;
		activeOperandflag = false;
		notifyListeners();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
		notifyListeners();
	}

	@Override
	public String toString() {
		String sign = positive ? "" : "-";
		if(hasFrozenValue()) {
			return frozen;
		} else {
			if(input.isBlank() || input.equals("0")) {
				return sign + "0";
			} else {
				if(Double.isFinite(inputValue)) {
					if(input.contains(".")) return sign + inputValue;
					else return sign + (int) inputValue;
				}
				else return  String.valueOf(inputValue);
			}
		}
		
	}

	@Override
	public void freezeValue(String value) {
		frozen = value;
		
	}

	@Override
	public boolean hasFrozenValue() {
		return frozen != null;
	}
}
