package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/*
 *This class is a node representing a command which generates some textual output dynamically.
 */
public class EchoNode extends Node {
	private Element[] elements;

	/**
	 *This constructor initializes the Echo Node elements.
	 *@param elements Elements of the Echo Node.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 *This method returns the elements of the Echo Node.
	 *@return Elements of the Echo Node.
	 */
	public Element[] getElements() {
		return elements;
	}
	
	
}
