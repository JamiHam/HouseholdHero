package group4.householdhero.model;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localiser {
	private Locale locale;
	private ResourceBundle bundle;
	
	public Localiser() {
		setLocale("en_IE");
	}
	
	public Locale setLocale(String language) {
		locale = new Locale(language);
		bundle = ResourceBundle.getBundle("TextProperties", locale);
		return locale;
	}
	
	public String getLocalisedString(String string) {
		return bundle.getString(string);
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public ResourceBundle getBundle() {
		return bundle;
	}
}
