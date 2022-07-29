package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * This interface represents the model of a localization provider.
 */
public interface ILocalizationProvider {
	/**
	 * This method is used to add the current provider.
	 * @param l Listener that is going to be registered to the current provider.
	 */
    void addLocalizationListener(ILocalizationListener l);
    
    /**
     * This method is used to remove the given provider.
     * @param l Listener that is going to be unregistered to the current provider
     */
    void removeLocalizationListener(ILocalizationListener l);
    
    /**
     * This method gets the value which the given key maps to.
     * @param key Key.
     * @return Translated key.
     */
    String getString(String key);
    
    /**
     * This method is a getter for the current set language.
     * @return Current language.
     */
	String getCurrentLanguage();
}
