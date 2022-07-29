package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * This class represents a localizable implementation of Abstract Action.
 */
public class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private ILocalizationProvider provider;
	private String key;
	
	private ILocalizationListener listener = new ILocalizationListener() {
        @Override
        public void localizationChanged() {
            LocalizableAction.this.putValue(
                    Action.NAME,
                    provider.getString(key)
            );
        }
    };
    /**
     * Basic Constructor.
     * @param key Key.
     * @param lp Provider.
     */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.provider = lp;
		this.key = key;
		lp.addLocalizationListener(listener);
		this.putValue(Action.NAME, provider.getString(key));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.putValue(
                Action.NAME,
                provider.getString(key)
        );
	}

}
