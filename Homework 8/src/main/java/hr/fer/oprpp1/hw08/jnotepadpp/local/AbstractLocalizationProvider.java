package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class represents an implementation of localization provider.
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider{

	List<ILocalizationListener> listeners;
	
	/**
	 * Basic Constructor.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * This method is used to notify all active listeners.
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}
}
