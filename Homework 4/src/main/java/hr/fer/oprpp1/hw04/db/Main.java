package hr.fer.oprpp1.hw04.db;

/**
 *This main class is used to test the written implementation of  interfaces and classes.
 */
public class Main {

	/**
	 *This main method is used for testing.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		//IComparisonOperator oper = ComparisonOperators.LESS;
		//System.out.println(oper.satisfied("Ana", "Jasna")); // true, since Ana < Jasna

		IComparisonOperator oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
		System.out.println(oper.satisfied("AAA", "AA*AA")); // false
		System.out.println(oper.satisfied("AAAA", "AA*AA")); // true
		
		//pr
		System.out.println(oper.satisfied("xAAA", "*AAA")); // true
		System.out.println(oper.satisfied("AAAB", "AAA*")); // true
		
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", "2");
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));

		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 "Bos*",
				 ComparisonOperators.LIKE
				);
		
		StudentRecord record1 = new StudentRecord("0000000001", "Akšamović", "Marin", "2");
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
		 expr.getFieldGetter().get(record1), // returns lastName from given record
		 expr.getStringLiteral() // returns "Bos*"
		);
		System.out.println(recordSatisfies); // false

	}

}
