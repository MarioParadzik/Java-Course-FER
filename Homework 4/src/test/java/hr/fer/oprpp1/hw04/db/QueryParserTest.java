package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QueryParserTest {
	 @Test
	    public void testdirectQuery() {
	        QueryParser qp1 = new QueryParser(" jmbag =\"123456789\" ");
	        assertTrue(qp1.isDirectQuery());
	        assertEquals("123456789", qp1.getQueriedJMBAG());
	        assertEquals(1, qp1.getQueries().size());
	    }

	    @Test
	    public void testMultipleQuery() {
	        QueryParser qp2 = new QueryParser("jmbag=\"123456789\" and lastName>\"P\"");
	        assertFalse(qp2.isDirectQuery());
	        assertEquals(2, qp2.getQueries().size());
	        assertThrows(IllegalArgumentException.class, ()-> System.out.println(qp2.getQueriedJMBAG())); // would throw!
	    }
	    
	    @Test
	    public void testCallingDirectQueryOnAMultipleQuery() {
	        QueryParser qp2 = new QueryParser("jmbag=\"123456789\" and lastName>\"P\"");
	        assertThrows(IllegalArgumentException.class, ()-> System.out.println(qp2.getQueriedJMBAG())); // would throw!
	    }
	    
}
