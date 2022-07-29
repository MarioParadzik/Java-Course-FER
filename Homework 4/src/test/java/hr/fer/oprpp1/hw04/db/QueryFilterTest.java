package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QueryFilterTest {
	 @Test
	    public void testTrueQuery() {
		 	StudentRecord student = new StudentRecord("123456789", "Bosec", "Damir", "4");
	        QueryParser qp2 = new QueryParser("jmbag=\"123456789\" and lastName LIKE \"B*c\"");
	        QueryFilter filter = new QueryFilter(qp2.getQueries());
	        assertTrue(filter.accepts(student));

	    }

	    @Test
	    public void testFalseQuery() {
	        QueryParser qp2 = new QueryParser("jmbag=\"123456789\" and firstName > \"R\"");
	        StudentRecord student = new StudentRecord("123456789", "Bosec", "Damir", "4");
	        QueryFilter filter = new QueryFilter(qp2.getQueries());

	        assertFalse(filter.accepts(student));
	    }
}
