package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * This interface represents a localization listener which has only one method to get notified if chanes are made.
 */
public interface ILocalizationListener {
	/**
	 * Notifies about the change in localization.
	 */
	void localizationChanged();
}
