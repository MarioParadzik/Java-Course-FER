package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * This class is an implementation of the AbstractLocalizationProvider.
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	private boolean connected;
	private String currentLanguage;
	private ILocalizationProvider provider;
	private ILocalizationListener listener = new ILocalizationListener() {
		
		@Override
		public void localizationChanged() {
			fire();
			
		}
	};
	
	/**
	 * Basic Constructor.
	 * @param provider Provider.
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
		this.currentLanguage = provider.getCurrentLanguage();
	}
	
	/**
     * Creates a connection between the current bridge and the provider through a listener.
     */
	public void connect() {
		if(connected) return;
		connected = true;
		provider.addLocalizationListener(listener);
		if (currentLanguage != null && !currentLanguage.equals(provider.getCurrentLanguage())) {
            fire();
            currentLanguage = provider.getCurrentLanguage();
        }
	}
	
	/**
     * Disconnects the current bridge from the provider, if previously connected.
     */
	public void disconnect() {
		if(!connected) return;
		connected = false;
		provider.removeLocalizationListener(listener);
		currentLanguage = provider.getCurrentLanguage();
	}

	@Override
	public String getString(String key) {
		 return provider.getString(key);
	}
	
	@Override
    public String getCurrentLanguage() {
        return currentLanguage;
    }
}
