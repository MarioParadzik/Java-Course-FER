package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * This interface represents a listener that observes and notifies about changes in a single document.
 */
public interface SingleDocumentListener {
	
	/**
     * Notifies the interested about status update.
     * @param model the updated instance.
     */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
     * Notifies the interested about file path update. 
     *
     * @param model the updated instance.
     */
	void documentFilePathUpdated(SingleDocumentModel model);
}
