package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;


import javax.swing.JComponent;
/**
 * This interface is a representation of an opened zero, single or multiple document file.
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * This method is responsible for displaying the entire MultipleDocumentModel‘s user interface.
	 * @return Graphical component.
	 */
	JComponent getVisualComponent();
	
	/**
	 * This method is used to create a new Document.
	 * @return New created document.
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * This method is used to as a getter for the current document.
	 * @return Current document.
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * This method is used to load the document from the given path.
	 * @param path Path of document to load.
	 * @return Loaded document.
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * This method is used to save the current document.
	 * @param model The document we want to save.
	 * @param newPath the path where we save the document.
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * This method is used to close the current document.
	 * @param model Document we want to close.
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * This method is used to add listeners on Multiple Document model.
	 * @param l Listener we add.
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * This method is used to remove listeners on Multiple Document model.
	 * @param l Listener we remove.
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * This method is used to count the number of opened documents.
	 * @return Number of opened documents.
	 */
	int getNumberOfDocuments();
	
	/**
	 * This method is used to get the document at the given index.
	 * @param index Index of document.
	 * @return Document at given index.
	 */
	SingleDocumentModel getDocument(int index);
	
	/**
	 * This method is used to fined a document for the given path.
	 * @param path Path.
	 * @return Document at given path if it exists, null otherwise.
	 */
	SingleDocumentModel findForPath(Path path); //null, if no such model exists
	
	/**
	 * This method is used to get the index of the given document.
	 * @param doc Document.
	 * @return Index of the given document.
	 */
	int getIndexOfDocument(SingleDocumentModel doc); //-1 if not present

}
