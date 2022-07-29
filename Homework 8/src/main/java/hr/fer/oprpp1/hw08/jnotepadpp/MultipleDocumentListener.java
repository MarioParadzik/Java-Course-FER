package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * This interface represents a listener that observes and notifies about changes in a zero or more documents.
 */
public interface MultipleDocumentListener {
	/**
	 * This method is used to notify about the current document changed.
	 * @param previousModel Model we switched.
	 * @param currentModel Model we switched to.
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * This method is used to notify about the added new document.
	 * @param model Document we added.
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * This method is used to notify about the document we removed.
	 * @param model Document we removed.
	 */
	void documentRemoved(SingleDocumentModel model);
}
