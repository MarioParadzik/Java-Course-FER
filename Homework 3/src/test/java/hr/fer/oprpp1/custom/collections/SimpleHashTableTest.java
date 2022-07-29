package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashTableTest<K, V> {

	@Test
	public void testCapacityNegativeNumber() {
		assertThrows(IllegalArgumentException.class, () -> {@SuppressWarnings("unused")
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(-1);});
	}
	
	@Test
	public void testIsEmpty() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		assertTrue(examMarks.isEmpty());
	}
	
	@Test
	public void testIsNotEmpty() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Mario", 10);
		assertFalse(examMarks.isEmpty());
	}
	
	@Test
	public void testPut() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Mario", 10);
		assertEquals(examMarks.get("Mario"), 10);
		assertEquals(examMarks.size(), 1);
	}
	
	@Test
	public void testPutKeyIsNull() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> {examMarks.put(null, 10);});
	}
	
	@Test
	public void testPutOverwriteValue() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Mario", 10);
		examMarks.put("Mario", 11);
		assertEquals(examMarks.get("Mario"), 11);
		assertEquals(examMarks.size(), 1);
	}
	
	@Test
	public void testGet() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Mario", 100);
		assertEquals(100, examMarks.get("Mario"));
	}
	
	@Test
	public void testSize() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Mario", 6);
		assertEquals(1, examMarks.size());
		examMarks.put("Dario", 11);
		assertEquals(2, examMarks.size());
	}
	
	@Test
	public void testConatinsKey() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Mario", 6);
		assertTrue(examMarks.containsKey("Mario"));
		assertFalse(examMarks.containsKey("a"));
		assertEquals(examMarks.containsKey(null), false);
	}
	
	@Test
	public void testConatinsValue() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Mario", 6);
		assertTrue(examMarks.containsValue(6));
		assertFalse(examMarks.containsValue("a"));
		assertEquals(examMarks.containsValue(null), false);
	}
	
	@Test
	public void testRemove() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Mario", 10);
		examMarks.put("Luka", 100);
	    assertEquals(2, examMarks.size());
	    examMarks.remove("Mario");
	    assertEquals(100, examMarks.get("Luka"));
	    assertEquals(1, examMarks.size());
	    examMarks.remove("Luka");
	    assertEquals(0, examMarks.size());
	    assertTrue(examMarks.isEmpty());
	}
	
	@Test
	public void testRemoveWhenParamNull() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		assertEquals(examMarks.remove(null), null);
	}
	
	@Test
	public void testToStringEmptyTable() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		assertEquals("[null]", examMarks.toString());
	}
	
	@Test
	public void testClear() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Mario", 10);
		examMarks.put("Luka", 100);
		examMarks.clear();
		assertEquals(examMarks.containsKey("Mario"), false);
		assertEquals(examMarks.containsValue(10), false);
		assertEquals(examMarks.size(), 0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testToArray() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Mario", 10);
		examMarks.put("Luka", 100);
		TableEntry<K, V>[] v = (TableEntry<K, V>[]) examMarks.toArray();
		assertEquals(examMarks.get("Mario"), v[0].getValue());
		assertEquals(examMarks.containsKey("Mario"), true);
		assertEquals(examMarks.get("Luka"), v[1].getValue());
		assertEquals(examMarks.containsKey("Luka"), true);
	}
	
	@Test
	public void testRemoveInIterator() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove(); 
			}
		}
		
		assertEquals(examMarks.size(), 3);
	}
	
	@Test
	public void testDoubleRemoveInIterator() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				iter.remove();
				assertThrows(IllegalStateException.class, () -> {iter.remove();}); 
			}
		}
	}
	
	@Test
	public void testModificationWhileIterating() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		assertThrows(ConcurrentModificationException.class, () -> {	
	
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}});
	}
}
