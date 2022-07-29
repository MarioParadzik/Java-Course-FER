package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DictionaryTest<K, V> {

	@Test
	public void testIsEmpty() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		assertTrue(dictionary.isEmpty());
		dictionary.put(6, 6);
		assertFalse(dictionary.isEmpty());
	}
	
	@Test
	public void testSize() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		dictionary.put(6, 6);
		assertEquals(1, dictionary.size());
		dictionary.put(10, 11);
		assertEquals(2, dictionary.size());
	}
	
	@Test
	public void testClear() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		dictionary.put(6, 6);
		dictionary.put(10, 11);
		dictionary.clear();
		assertEquals(0, dictionary.size());
	}
	
	@Test
	public void testPut() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		dictionary.put(6, 6);
		assertEquals(1, dictionary.size());
	}
	
	@Test
	public void testPutWhenKeyIsNull() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> {dictionary.put(null, 6);});
	}
	
	@Test
	public void testPutExistingElements() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		dictionary.put(6, 6);
		dictionary.put(6, 11);
		assertEquals(1, dictionary.size());
	}
	
	@Test
	public void testGet() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		dictionary.put(10, 100);
		assertEquals(100, dictionary.get(10));
	}
	
	@Test
	public void testGetWhenKeyIsNull() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> {dictionary.get(null);});
	}
	
	@Test
	   public void testGetOverridenElements() {
		Dictionary<String, String> dictionary = new Dictionary<>();
        dictionary.put("Mario", "Marić");
        dictionary.put("Mario", "Maurović");
        assertEquals("Maurović", dictionary.get("Mario"));
	}
	
	@Test
	   public void testRemove() {
		Dictionary<String, Integer> dictionary = new Dictionary<>();
		dictionary.put("Mario", 10);
        dictionary.put("Luka", 100);
        assertEquals(2, dictionary.size());
        dictionary.remove("Mario");
        assertEquals(100, dictionary.get("Luka"));
        assertEquals(1, dictionary.size());
        dictionary.remove("Luka");
        assertEquals(0, dictionary.size());
        assertTrue(dictionary.isEmpty());
	}
	
	@Test
	public void testRemoveWhenKeyIsNull() {
		Dictionary<Integer, Integer> dictionary = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> {dictionary.remove(null);});
	}
	
}
