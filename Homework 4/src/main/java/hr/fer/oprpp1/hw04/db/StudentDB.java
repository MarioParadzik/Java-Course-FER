package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *This class is used to make query inputs and printing the data by the query.
 */
public class StudentDB {

	/**
	 *Main method
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		List<String> input = null;
		List<StudentRecord> students = new ArrayList<>();
		StudentDatabase db = null;
		try {
			input = Files.readAllLines(Paths.get("src/main/resources/database.txt"), StandardCharsets.UTF_8);
		} catch(IOException e) {
			System.out.println("Unable to read file.");
			System.exit(-1);
		}
		
		try {
			 db = new StudentDatabase(input);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		
		String line = null;
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("> ");
			line = sc.nextLine();
			
			if(line.trim().equals("exit")) {
				System.out.println("Goodbye!");
				break;
			} else if(!line.trim().startsWith("query")) {
				System.out.println("Invalid keyword");
				continue;
			}
			
			
			QueryParser parser;
			
			try {
				parser = new QueryParser(line.trim().replace("query", ""));
			} catch (RuntimeException e) {
				System.out.println("Wrong input.");
				continue;
			}
			
			if(parser.isDirectQuery()) {
				StudentRecord direct = db.forJMBAG(parser.getQueriedJMBAG());
				if(direct != null) students.add(direct);
			} else {
				students = db.filter(new QueryFilter(parser.getQueries()));
			}
			
			if(students.size() > 0) ispisi(students);
			System.out.println("Records selected: " + students.size());
			System.out.println();
		}
		sc.close();
	}

	/**
	 *This method is used to print the database by the given query.
	 * @param students List of student records.
	 */
	private static void ispisi(List<StudentRecord> students) {
		int jmbagLenght = -1;
		int lastNameLenght = -1;
		int firstNameLenght = -1;
		StringBuilder sb = new StringBuilder();
		
		for(StudentRecord s : students) {
			if(s.getJmbag().length() > jmbagLenght) jmbagLenght = s.getJmbag().length();
			if(s.getLastName().length() > lastNameLenght) lastNameLenght = s.getLastName().length();
			if(s.getFirstName().length() > firstNameLenght) firstNameLenght = s.getFirstName().length();
		}
		
		okvir(jmbagLenght, lastNameLenght, firstNameLenght, sb);
		sb.append(System.getProperty("line.separator"));
		
		for(int i = 0; i < students.size(); i++) {
			sb.append("| ");
			sb.append(students.get(i).getJmbag());
			sb.append(" ".repeat(jmbagLenght - students.get(i).getJmbag().length()));
			sb.append(" | ");
			sb.append(students.get(i).getLastName());
			sb.append(" ".repeat(lastNameLenght - students.get(i).getLastName().length()));
			sb.append(" | ");
			sb.append(students.get(i).getFirstName());
			sb.append(" ".repeat(firstNameLenght - students.get(i).getFirstName().length()));
			sb.append(" | ");
			sb.append(students.get(i).getFinalGrade());
			sb.append(" |");
			if(i < students.size()) sb.append(System.getProperty("line.separator"));
		}
		
		okvir(jmbagLenght, lastNameLenght, firstNameLenght, sb);
		
		System.out.println(sb.toString());
	}

	/**
	 *This method is used for printing the frame of the table.
	 * @param jmbagLenght Longest jmbag lenght.
	 * @param lastNameLenght Longest first name lenght.
	 * @param firstNameLenght Longest last name lenght.
	 * @param sb String builder.
	 */
	private static void okvir(int jmbagLenght, int lastNameLenght, int firstNameLenght, StringBuilder sb) {
		sb.append("+");
		sb.append("=".repeat(jmbagLenght+2));
		sb.append("+");
		sb.append("=".repeat(lastNameLenght+2));
		sb.append("+");
		sb.append("=".repeat(firstNameLenght+2));
		sb.append("+");
		sb.append("=".repeat(3));
		sb.append("+");
	}

}
