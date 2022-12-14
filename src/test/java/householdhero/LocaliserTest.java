package householdhero;

import group4.householdhero.model.Localiser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class LocaliserTest {
	private Localiser localiser;
	
	@BeforeAll
	public void createLocaliser() {
		localiser = new Localiser();
	}
	
	@Test
	public void setLocaleTest() {
		Locale locale = localiser.setLocale("en_IE");
		assertEquals("en_ie", locale.toString(), "The locale was incorrect");
	}
	
	@Test
	public void getLocalisedStringTest() {
		String string = localiser.getLocalisedString("english.choice.text");
		assertEquals("English", string, "The string was localised incorrectly");
	}
	
	@Test
	public void getLocaleTest() {
		localiser.setLocale("ga_IE");
		assertEquals("ga_ie", localiser.getLocale().toString(), "The incorrect locale was returned");
	}
}
