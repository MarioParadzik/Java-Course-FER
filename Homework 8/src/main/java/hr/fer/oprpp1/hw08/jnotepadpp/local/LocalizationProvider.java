package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is an implementation of  AbstractLocalizationProvider.
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	
	private static final LocalizationProvider instance = new LocalizationProvider();
	private String language;
	private ResourceBundle bundle;
	
	/**
	 * Basic private construcotor.
	 */
	private LocalizationProvider() {
        this.language = "en";
        this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", Locale.forLanguageTag(language));
    }
	
	/**
	 * This returns the singleton instance of the provider.
	 * @return
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * This method is a setter for language.
	 * @param language Language to set.
	 */
	public void setLanguage(String language) {
		this.language = language;
		this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", Locale.forLanguageTag(language));
        fire();
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return this.language;
	}
	
}
