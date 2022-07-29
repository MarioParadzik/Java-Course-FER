package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * This interface is a representation of an opened single document file.
 */
public interface SingleDocumentModel {
	/**
	 * This method is a getter for the text component of document.
	 * @return JTextArea instance.
	 */
	JTextArea getTextComponent();
	
	/**
	 * THis methods is a getter for the document path.
	 * @return Document path.
	 */
	Path getFilePath();
	
	/**
	 * This method is a setter for document path.
	 * @param path Path to set.
	 */
	void setFilePath(Path path);
	
	/**
	 * This method is a flag for changes on the current opened document.
	 * @return True if modified, false otherwise.
	 */
	boolean isModified();
	
	/**
	 * This method is a setter for the document modification.
	 * @param modified Document modified or not.
	 */
	void setModified(boolean modified);
	
	/**
	 * This method is adder for listeners on the current document.
	 * @param l Listener to be added.
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	
	/**
	 * This method is remover for listeners on the current document.
	 * @param l Listener to be removed.
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

	JTextArea getTextArea();
}
