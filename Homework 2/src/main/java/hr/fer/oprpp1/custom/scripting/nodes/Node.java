package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 *This is a base class for all graph nodes.
 */
public abstract class Node {
	private ArrayIndexedCollection collcetion;
	
	/**
	 *This method adds child's to this node.
	 *@param child The child we add.
	 */
	public void addChildNode(Node child) {
		if(collcetion == null) collcetion = new ArrayIndexedCollection();
		collcetion.add(child);
	}
	
	/**
	 *This method returns the number of children of this node.
	 *@return Number of the children,
	 */
	public int numberOfChildren() {
		return collcetion.size();
	} 
	
	/**
	 *This method is getting the child of the Node at the given index.
	 *@param index index of the child.
	 *@return Child at given index.
	 */
	public Node getChild(int index) {
		if(index < 0 || index > collcetion.size()) throw new IndexOutOfBoundsException();
		return (Node) collcetion.get(index);
	}
}
