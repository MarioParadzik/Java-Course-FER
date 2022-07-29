package hr.fer.oprpp1.hw04.db;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;



public class FieldValueGettersTest {

	@Test
	public void testFirstName() {
		StudentRecord student = new StudentRecord("123456789", "Damiro", "Damir", "4");
		assertEquals("Damir", FieldValueGetters.FIRST_NAME.get(student));
	}
	
	@Test
	public void testLastName() {
		StudentRecord student = new StudentRecord("123456789", "Damiro", "Damir", "4");
		assertEquals("Damiro", FieldValueGetters.LAST_NAME.get(student));
	}
	
	@Test
	public void testJMBAG() {
		StudentRecord student = new StudentRecord("123456789", "Damiro", "Damir", "4");
		assertEquals("123456789", FieldValueGetters.JMBAG.get(student));
	}
}
