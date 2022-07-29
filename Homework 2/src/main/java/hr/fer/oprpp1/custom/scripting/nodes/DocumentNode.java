package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 *This class is a node representing an entire document.
 */
public class DocumentNode extends Node {
	
    @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         return this.toString().equals(o.toString());
     }
    
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.numberOfChildren(); i++) {
			if(this.getChild(i) instanceof ForLoopNode) sb.append(strForLoopNode((ForLoopNode) this.getChild(i)));
			else if(this.getChild(i) instanceof EchoNode) sb.append(strEchoNode((EchoNode) this.getChild(i)));
			else if(this.getChild(i) instanceof TextNode) sb.append(((TextNode) this.getChild(i)).getText());
			else System.out.println("Wrong syntax tree");
		}
		return sb.toString();

	}

	/**
	 *This private method builds echo elements with StringBuilder.
	 *@param child Echo node which elements we use to build the string.
	 *@return The string value of EchoNode elements.
	 */
	private String strEchoNode(EchoNode child) {
		StringBuilder sb = new StringBuilder();
		Element[] elements = child.getElements();
		sb.append("{$=");
		for (int i = 0; i < elements.length; i++) {
			sb.append(elements[i].asText());
			sb.append(" ");
		}
		sb.append("$}");
		return sb.toString();
	}

	/**
	 *This private method builds the original input for the ForLoopNode and his children.
	 *@param child For Node which elements and child's we use to build the string.
	 *@return The string value of ForLoopNode elements and children.
	 */
	private String strForLoopNode(ForLoopNode child) {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR ");
		sb.append(child.getVariable().asText());
		sb.append(" ");
		sb.append(child.getStartExpression().asText());
		sb.append(" ");
		sb.append(child.getEndExpression().asText());
		sb.append(" ");
		
		if(child.getStepExpression() != null) sb.append(child.getStepExpression().asText());
		sb.append(" $}");
		
		for (int i = 0; i < child.numberOfChildren(); i++) {
			if(child.getChild(i) instanceof ForLoopNode) sb.append(strForLoopNode((ForLoopNode) child.getChild(i)));
			else if(child.getChild(i) instanceof EchoNode) sb.append(strEchoNode((EchoNode) child.getChild(i)));
			else if(child.getChild(i) instanceof TextNode) sb.append(((TextNode) child.getChild(i)).getText());
			else System.out.println("Wrong syntax tree");
		}
		
		sb.append("{$END$}");
		return sb.toString();
	}

}
