package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *This class is represents a student database where we store and check students data.
 */
public class StudentDatabase {

	private List<StudentRecord> students;
	private HashMap<String, StudentRecord> studentsRetrivalMap;
	
	/**
	 *Basic constructor.
	 * @param content List of student info.
	 */
	public StudentDatabase(List<String> content) {
		this.students = new ArrayList<>(content.size());
		this.studentsRetrivalMap = new HashMap<>(content.size());
		
		for(String stud: content) {
			//split by tab
			String[] attribute = stud.split("\t");
			if(attribute.length != 4) throw new RuntimeException("Invalid data in database");
			StudentRecord student = new StudentRecord(attribute[0], attribute[1], attribute[2], attribute[3]);
			students.add(student);
			studentsRetrivalMap.put(student.getJmbag(), student);
		}
		checkUniqueJmbag();
		checkGrade();
	}

	/**
	 *This method checks if the Jmbag are unique.
	 */
	private void checkUniqueJmbag() {
		for(int i = 0; i < students.size(); i++) {
			for(int j = i + 1; j < students.size(); j++) {
				if(students.get(i).equals(students.get(j))) throw new IllegalArgumentException("Database can't contain students with the same jmbag.");
			}
		}
	}

	/**
	 *This method checks if studetns grades are the allowed ones.
	 */
	private void checkGrade() {
		for(StudentRecord student : students) {
			int grade = Integer.parseInt(student.getFinalGrade());
			if(grade < 1 || grade > 5)
				throw new IllegalArgumentException("Grade is not allowed.");
		}
		
	}

	
	/**
	 *This method is a getter for students by the jmbag.
	 * @param jmbag Jmbag of the student we want.
	 * @return Student record.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentsRetrivalMap.get(jmbag);
	}
	
	/**
	 *This method is used to filter the student data base by given filter.
	 * @param filter Filter.
	 * @return List of the filtered database.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> temp = new ArrayList<>();
		for(StudentRecord student : students) {
			if(filter.accepts(student)) temp.add(student);
		}
		return temp;
	}

}
