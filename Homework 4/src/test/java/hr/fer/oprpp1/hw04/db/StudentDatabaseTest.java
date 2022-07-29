package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;


public class StudentDatabaseTest {
	
	@Test
	public void testForJmbag() throws IOException {
		List<String> allLines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(allLines);
		StudentRecord s = new StudentRecord("0000000001", "Akšamović", "Marin", "2");
		assertEquals(db.forJMBAG("0000000001"), s);
	}

	@Test
	public void testTrueFilter() throws IOException {
		List<String> allLines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(allLines);
		List<StudentRecord> students = db.filter(student -> true);
		assertFalse(students.isEmpty());
		assertEquals(63, students.size());
	}
	
	@Test
	public void testFalseFilter() throws IOException {
		List<String> allLines = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(allLines);
		List<StudentRecord> students = db.filter(student -> false);
		assertTrue(students.isEmpty());
	}

}

