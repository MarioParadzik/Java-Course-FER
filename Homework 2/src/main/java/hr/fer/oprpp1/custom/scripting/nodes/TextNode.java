package hr.fer.oprpp1.custom.scripting.nodes;

/**
 *This class is a node representing a piece of textual data
 */
public class TextNode extends Node {
	private String text;

	/**
	 *This constructor initializes the text of the node.
	 *@param text Text.
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 *This method returns the text of the Text Node.
	 *@return Text of the Text Node.
	 */
	public String getText() {
		return text;
	}

	//can't have children.
	@Override
	public int numberOfChildren() {
		return 0;
	}
	
	
}
