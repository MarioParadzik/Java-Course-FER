package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JLabel;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * This class represents a Localized JLabel
 */
public class LJLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	String key;
	private ILocalizationProvider provider;
	private ILocalizationListener listener = new ILocalizationListener() {
        @Override
        public void localizationChanged() {
        	LJLabel.this.setText(provider.getString(key));
        }
    };
    
    /**
     * Basic Constructor.
     * @param key Key.
     * @param lp Provider.
     */
    public LJLabel(String key, ILocalizationProvider lp) {
    	this.key = key;
        this.provider = lp;
        lp.addLocalizationListener(listener);
        this.setText(provider.getString(key));
    }

    /**
     * This method is a getter for key.
     * @return
     */
	public String getKey() {
		return provider.getString(key);
	}
    
}
