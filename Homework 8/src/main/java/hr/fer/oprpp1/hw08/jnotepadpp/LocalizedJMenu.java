package hr.fer.oprpp1.hw08.jnotepadpp;

import javax.swing.JMenu;

import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationListener;
import hr.fer.oprpp1.hw08.jnotepadpp.local.ILocalizationProvider;

/**
 * This class represents a Localized JMENU
 */
public class LocalizedJMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	String key;
	private ILocalizationProvider provider;
	private ILocalizationListener listener = new ILocalizationListener() {
        @Override
        public void localizationChanged() {
        	LocalizedJMenu.this.setText(provider.getString(key));
        }
    };
    
    /**
     * Basic Constructor.
     * @param key Key.
     * @param lp Provider.
     */
    public LocalizedJMenu(String key, ILocalizationProvider lp) {
    	this.key = key;
        this.provider = lp;
        lp.addLocalizationListener(listener);
        this.setText(provider.getString(key));
    }
}
