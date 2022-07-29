package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 *This class represents students.
 */
public class StudentRecord {
	
	private String jmbag;
	private String lastName;
	private String firstName;
	private String finalGrade;
	
	/**
	 *Basic constructor.
	 * @param jmbag Jmbag of the student.
	 * @param lastName Students last name.
	 * @param firstName Students first name.
	 * @param finalGrade Students gradee.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}

	/**
	 *This method is a getter for students jmbag.
	 * @return Students jmbag.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 *This method is a getter for students last name.
	 * @return Students last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 *This method is a getter for students first name.
	 * @return Students  first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 *This method is a getter for students grade.
	 * @return Students grade.
	 */
	public String getFinalGrade() {
		return finalGrade;
	}
	
	
}
