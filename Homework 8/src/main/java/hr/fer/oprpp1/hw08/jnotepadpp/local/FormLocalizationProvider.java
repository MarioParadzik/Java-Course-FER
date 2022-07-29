package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * This class represents a bridge between the main JFrame object and the localization provider.
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * 
	 * @param provider Provider.
	 * @param jFrame Component that seeks for localization information.
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame jFrame) {
		super(provider);
		jFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
				disconnect();
			}
			
		});
	}

}
