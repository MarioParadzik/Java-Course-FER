package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/*
 *This class is a node representing a single for-loop construct.
 */
public class ForLoopNode extends Node {
	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;
	
	/**
	 *This constructor initializes the For loop variable and their 3 expressions.
	 *@param variable Variable
	 *@param startExpression Start Expression
	 *@param endExpression End expression
	 *@param stepExpression Step expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 *This method returns the variable of the For Loop Node.
	 *@return Variable of the For Loop Node.
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 *This method returns the start expression of the For Loop Node.
	 *@return Start expression of the For Loop Node.
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 *This method returns the end expression of the For Loop Node.
	 *@return End expression of the For Loop Node.
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 *This method returns the step expression of the For Loop Node.
	 *@return Step expression of the For Loop Node.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	
}
